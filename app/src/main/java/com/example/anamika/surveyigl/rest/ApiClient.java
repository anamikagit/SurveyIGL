package com.example.anamika.surveyigl.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String baseUrl = "http://111.118.178.163/amrs_igl_api/webservice.asmx/";
        public static Retrofit getClient(String baseUrl){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();


            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)

                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit;
        }
    }
