package com.mobbeel.mobbsign.teknei.demo.service;


import android.preference.PreferenceManager;

import com.mobbeel.mobbsign.teknei.demo.service.interceptor.BasicAuthInterceptor;
import com.mobbeel.mobbsign.teknei.demo.service.interceptor.LoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Context;

/**
 * Created by jearias (Mobbeel) on 30/04/17
 */
public class RestClient {
    public static String BASE_URL = "http://185.80.5.18";
    //public static String BASE_URL = "https://192.168.1.100:28443";

    public static final int TIMEOUT = 60;

    private RestAPI restAPI;

    private String username;
    private String password;

    public RestClient(String username, String password, String baseUrl) {
        setUsername(username);
        setPassword(password);
        BASE_URL = baseUrl;
        if (!BASE_URL.endsWith("/"))
            BASE_URL += "/";
        restAPI = createRestAPI();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getBaseURL() {
        return BASE_URL;
    }

    private RestAPI createRestAPI() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        if (getUsername() != null && getPassword() != null) {
            builder.interceptors().add(new BasicAuthInterceptor(username, password));
        }
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .validateEagerly(true)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(RestAPI.class);
    }

    public RestAPI getRestAPI() {
        return restAPI;
    }

}
