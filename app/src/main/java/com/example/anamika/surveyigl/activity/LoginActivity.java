package com.example.anamika.surveyigl.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.anamika.surveyigl.Constants;
import com.example.anamika.surveyigl.R;
import com.example.anamika.surveyigl.model.LoginResponce;
import com.example.anamika.surveyigl.model.SurvayStatus;
import com.example.anamika.surveyigl.rest.ApiClient;
import com.example.anamika.surveyigl.rest.ApiInterface;
import com.example.anamika.surveyigl.util.AppSharedData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText inputUid,inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btnLogIn;
    String saveUid;
    String savePwd;
    int flag = 0;
    ApiInterface apiService = ApiClient.getClient(ApiClient.baseUrl).create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
       // inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        inputUid = (EditText) findViewById(R.id.input_uid);
       // inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnLogIn = (Button) findViewById(R.id.btn_logIn);

        inputUid.addTextChangedListener(new MyTextWatcher(inputUid));
       // inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveUid = inputUid.getText().toString();
                savePwd = inputPassword.getText().toString();
               // submitForm();
                sendLoginCredential();

            }
        });

        checkAndRequestPermissions();
    }


    /**
     * Validating form
     */
   /* private void submitForm() {
        if (!validateName()) {
            return;
        }

        *//*if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }*//*

        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
    }*/

   /* private boolean validateName() {
        if (inputUid.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputUid);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }
*/
    /*private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }
*/
   /* private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }*/

   /* private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
*/
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_uid:
                   // validateName();
                    break;
                /*case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;*/
            }
        }
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
    public void clearIdPwdEditTextData(){
        inputUid.setText(null);
        inputPassword.setText(null);
    }

    public void sendLoginCredential(){

        Call<List<LoginResponce>> call = apiService.sendLoginCredential(saveUid,savePwd);

        call.enqueue(new Callback<List<LoginResponce>>() {
            @Override
            public void onResponse(Call<List<LoginResponce>> call, Response<List<LoginResponce>> response) {
                List<LoginResponce> loginResponces = response.body();
                if(loginResponces != null && loginResponces.size()>0){
                    LoginResponce loginResponce = loginResponces.get(0);

                    if(loginResponce.getResponse().equals("success"))
                    {
                        Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);

                        AppSharedData.save(LoginActivity.this, Constants.LOGOUT_ID, inputUid.getText().toString());
                        clearIdPwdEditTextData();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Login Id or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LoginResponce>> call, Throwable t) {

            }
        });
    }
    /*@Override
            public void onBackPressed(){
                    Intent launchNextActivity = new Intent(B.class, A.class);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(launchNextActivity);
                                        }*/
}