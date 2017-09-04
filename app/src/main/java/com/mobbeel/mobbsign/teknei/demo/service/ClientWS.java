package com.mobbeel.mobbsign.teknei.demo.service;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marojas on 06/05/2017.
 */

public class ClientWS {

    public static String BASE_URL = "http://192.168.1.124:8080/mobbscan/";
    private static int TIMEOUT = 60;

    private static ClientWS INSTANCE = new ClientWS();

    private ClientWS() { }

    public static ClientWS getInstance() {
        return INSTANCE;
    }

    public static Retrofit buildRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        Log.d("ClientWS class", "BASE_URL: " + BASE_URL);
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .validateEagerly(true)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

}
