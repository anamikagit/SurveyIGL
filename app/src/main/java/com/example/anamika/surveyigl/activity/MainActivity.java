package com.example.anamika.surveyigl.activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anamika.surveyigl.Constants;
import com.example.anamika.surveyigl.GPSTracker;
import com.example.anamika.surveyigl.R;
import com.example.anamika.surveyigl.model.SurvayStatus;
import com.example.anamika.surveyigl.rest.ApiClient;
import com.example.anamika.surveyigl.rest.ApiInterface;
import com.example.anamika.surveyigl.util.AppSharedData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
   // private List<String> surveyFields = new ArrayList<String>();
    GPSTracker gps;
    String lat,lng,formattedDate;
    Double latitude,longitude;
    int flag = 0;
    private static final String TAG = "MAIN_ACTIVITY_ASYNC";

    Button button_submit,button_add_new;

    @BindView(R.id.edt1)EditText editText1;
    @BindView(R.id.edt2)EditText editText2;
    @BindView(R.id.edt12)EditText editText12;
    @BindView(R.id.edt3)EditText editText3;
    @BindView(R.id.edt4)EditText editText4;
    @BindView(R.id.edt5)EditText editText5;
    @BindView(R.id.edt6)EditText editText6;
    @BindView(R.id.edt7)EditText editText7;
    //@BindView(R.id.edt8)EditText editText8;
    //@BindView(R.id.edt9)EditText editText9;
    //@BindView(R.id.edt10)EditText editText10;
    @BindView(R.id.edt11)EditText editText11;
    @BindView(R.id.fetch_btn)Button buttonFetch;
    @BindView(R.id.lat)TextView textView_lat;
    @BindView(R.id.lng)TextView textView_lng;

    ApiInterface apiService = ApiClient.getClient(ApiClient.baseUrl).create(ApiInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

       /* SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        String decodedImage = sharedPreferences.getString("encodedImage", "imageCode");*/

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
    // Now formattedDate have current date/time
       // Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();

        checkAndRequestPermissions();
    //to restart the activity
        Intent starterIntent = new Intent();
        starterIntent = getIntent();

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
        final boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Toast.makeText(MainActivity.this,"GPS Status:"+statusOfGPS, Toast.LENGTH_LONG);

        gps = new GPSTracker(MainActivity.this);

        if(statusOfGPS == false)
        {
            gps.showSettingsAlert();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // toolbar.setTitle("IGL SURVEY");

        button_submit = findViewById(R.id.submit_button);
        //button_add_new = findViewById(R.id.add_new_button);

        final Intent finalStarterIntent = starterIntent;
        final Intent finalStarterIntent1 = starterIntent;
        buttonFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(statusOfGPS == true && gps.canGetLocation())
                {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    textView_lat.setText(""+latitude);
                    textView_lng.setText(""+longitude);
                }

                else
                {
                    finish();
                    startActivity(finalStarterIntent1);

                    if(statusOfGPS == true && gps.canGetLocation())
                    {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                        textView_lat.setText(""+latitude);
                        textView_lng.setText(""+longitude);
                    }
                }

                lat = textView_lat.getText().toString();
                lng = textView_lng.getText().toString();
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Submitting data to server", Toast.LENGTH_SHORT).show();
                submitSurveyData();
                /*Intent i = new Intent(MainActivity.this,VolleySendingMeterImageActivity.class);
                startActivity(i);*/
            }
        });
            }
    public void clearForm(){

                editText1.getText().clear();
                editText2.getText().clear();
                editText3.getText().clear();
                editText4.getText().clear();
                editText5.getText().clear();
                editText6.getText().clear();
                editText7.getText().clear();
                editText11.getText().clear();
                editText12.getText().clear();
                textView_lat.setText("");
                textView_lng.setText("");
    }

    private void checkAndRequestPermissions() {
        if (flag == 0) {
            String[] permissions = new String[] {
                    "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.VIBRATE", "android.permission.INTERNET",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.ACCESS_FINE_LOCATION", "android.permission.WAKE_LOCK",
                    "android.permission.ACCESS_NETWORK_STATE", "android.permission.READ_PHONE_STATE"
            };
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(permission);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat
                        .requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                                1);
            }
            flag = 1;
        }
    }
    public void submitSurveyData(){

        Call<List<SurvayStatus>> call = apiService.sendSurveyResponce(editText1.getText().toString(),
                editText12.getText().toString(),editText2.getText().toString(),editText3.getText().toString(),editText4.getText().toString(),
                editText5.getText().toString(),editText6.getText().toString(),editText7.getText().toString(),editText11.getText().toString(),
                lat,lng,formattedDate);

        AppSharedData.save(MainActivity.this, Constants.MTR_SRL_NUM_KEY, editText7.getText().toString());


        call.enqueue(new Callback<List<SurvayStatus>>() {
            @Override
            public void onResponse(Call<List<SurvayStatus>> call, Response<List<SurvayStatus>> response) {
                List<SurvayStatus> survayStatuses = response.body();
                if(survayStatuses != null && survayStatuses.size()>0){
                    SurvayStatus survayStatus = survayStatuses.get(0);
                   // Toast.makeText(MainActivity.this,"Form Submitted Successfully", Toast.LENGTH_SHORT).show();
                    if(survayStatus.getResponse().equals("success")){
                    Intent i = new Intent(MainActivity.this,VolleySendingMeterImageActivity.class);
                    startActivity(i);
                    clearForm();}

                else{
                    Toast.makeText(MainActivity.this,"Fill All Fields Carefully", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SurvayStatus>> call, Throwable t) {

            }
        });
    }

    /*@Override
    public void onBackPressed(){
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }*/
}