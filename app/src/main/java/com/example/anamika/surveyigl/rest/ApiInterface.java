package com.example.anamika.surveyigl.rest;

import com.example.anamika.surveyigl.activity.ImageServerResponce;
import com.example.anamika.surveyigl.model.SurvayStatus;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("Ins_Install_Survay")
    Call<List<SurvayStatus>> sendSurveyResponce(
             @Query("Business_part_no") String Business_part_no,
             @Query("Name") String Name,
             @Query("Address") String Address,
             @Query("Mob_no") String Mob_no,
             @Query("Email_id") String Email_id,
             @Query("meter_manufacture_by") String meter_manufacture_by,
             @Query("Meter_type") String Meter_type,
             @Query("Meter_Serial_no") String Meter_Serial_no,
             //@Query("Sim_provider") String Sim_provider,
             //@Query("Sim_no") String Sim_no,
             //@Query("Device_imei") String Device_imei,
             @Query("Current_Reading") String Current_Reading,
             @Query("lat") String lat,
             @Query("lon") String lon,
             @Query("currenttime") String formattedDate);

    @POST("/Ins_meter_image")
    @FormUrlEncoded
    Call<List<ImageServerResponce>> sendingImage(
            @Field("img_data") String temp,
            @Field("date_time") String imei,
            @Field("serialno") String serialNum,
            @Field("img_type")String imgType);
}

