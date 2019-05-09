package com.techease.mensfashion.communication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by k.zahid on 3/28/18.
 */

public class ApiFactory {
    public static WebServices create() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://116.203.128.245/index.php/restapi/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WebServices.class);
    }
}
