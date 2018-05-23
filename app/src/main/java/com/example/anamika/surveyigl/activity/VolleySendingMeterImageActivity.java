package com.example.anamika.surveyigl.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anamika.surveyigl.Constants;
import com.example.anamika.surveyigl.R;
import com.example.anamika.surveyigl.model.LogoutResponce;
import com.example.anamika.surveyigl.model.SurvayStatus;
import com.example.anamika.surveyigl.rest.ApiClient;
import com.example.anamika.surveyigl.rest.ApiInterface;
import com.example.anamika.surveyigl.util.AppSharedData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static java.util.Locale.getDefault;

public class VolleySendingMeterImageActivity extends AppCompatActivity {
    ImageView imageView;
    Button clickImg, btnSendImg;
    Bitmap bitmap;
    String imageCode;
    String formattedDate;
    TextView tv_done;
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    // file url to store image/video
    private Uri fileUri;
    ApiInterface apiService = ApiClient.getClient(ApiClient.baseUrl).create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_sending_meter_image);

        btnSendImg = findViewById(R.id.sendImage);
        imageView = findViewById(R.id.imgPreview);
        clickImg = findViewById(R.id.btnCapturePicture);
        tv_done = findViewById(R.id.tv_done);

        clickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });
        btnSendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendingImage();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuAddSurvey:
               // Toast.makeText(this, "You clicked settings", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(VolleySendingMeterImageActivity.this,MainActivity.class);
                startActivity(i);
                break;

            case R.id.menuLogout:
               // Toast.makeText(this, "You clicked logout", Toast.LENGTH_SHORT).show();
                sendLogoutCall();
                break;
        }
        return true;
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }
        else {
            return null;
        }

        return mediaFile;
    }

    private void captureImage(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    /**
     * Display image from a path to ImageView
     */
    public String previewCapturedImage() {
        try {
            //encode base64 string to image
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            options.inSampleSize = 8;

            bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            imageCode = Base64.encodeToString(imageBytes,Base64.DEFAULT);
            imageView.setVisibility(View.VISIBLE);

            imageView.setImageBitmap(bitmap);
        }   catch (NullPointerException e) {
            e.printStackTrace();
        }
        return imageCode;
    }

    public void SendingImage()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://111.118.178.163/amrs_igl_api/webservice.asmx/Ins_meter_image?",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);
                       // mProgressDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            for(int j=0;j<array.length();j++) {
                                JSONObject json = array.getJSONObject(j);
                                if (!(imageCode.isEmpty())) {
                                   // Toast.makeText(getApplicationContext(), "Submitting Image To Server", Toast.LENGTH_SHORT).show();
                                    btnSendImg.setText("Survey completed");
                                    btnSendImg.setEnabled(false);
                                    clickImg.setVisibility(Button.GONE);
                                    imageView.setImageResource(R.mipmap.tick_green);
                                    tv_done.setVisibility(TextView.VISIBLE);
                                } else {
                                    Toast.makeText(VolleySendingMeterImageActivity.this, "Login Again!! image is not clicked properly", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // mProgressDialog.dismiss();
                        Toast.makeText(VolleySendingMeterImageActivity.this, "Please try again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("img_data", imageCode);
                params.put("date_time", formattedDate);
                params.put("serialno", AppSharedData.get(VolleySendingMeterImageActivity.this, Constants.MTR_SRL_NUM_KEY,""));
                params.put("img_type", 1+"");

                System.out.println("Login SendData" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public  void sendLogoutCall(){
       String logoutUid =  AppSharedData.get(VolleySendingMeterImageActivity.this,Constants.LOGOUT_ID,"");
        Call<List<LogoutResponce>> call = apiService.sendLogotCall(logoutUid);

       // AppSharedData.save(VolleySendingMeterImageActivity.this, Constants.LOGOUT_ID, editText7.getText().toString());


        call.enqueue(new Callback<List<LogoutResponce>>() {
            @Override
            public void onResponse(Call<List<LogoutResponce>> call, retrofit2.Response<List<LogoutResponce>> response) {
                List<LogoutResponce> logoutResponces = response.body();
                if(logoutResponces != null && logoutResponces.size()>0){
                    LogoutResponce logoutResponce = logoutResponces.get(0);
                    Toast.makeText(VolleySendingMeterImageActivity.this,"Logged Out Successfully", Toast.LENGTH_SHORT).show();
               Intent i = new Intent(VolleySendingMeterImageActivity.this,LoginActivity.class);
               startActivity(i);
                }
                else{
                    Toast.makeText(VolleySendingMeterImageActivity.this,"Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LogoutResponce>> call, Throwable t) {

            }
        });
    }
    }
