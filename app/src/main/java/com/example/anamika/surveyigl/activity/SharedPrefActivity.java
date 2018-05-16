package com.example.anamika.surveyigl.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.anamika.surveyigl.R;


public class SharedPrefActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyApp_Settings";

    //SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("encodedImage", "imageCode");
        editor.commit();

        // Reading from SharedPreferences
        String value = sharedPreferences.getString("key", "");
        //Log.d(TAG, value);
    }
    }

