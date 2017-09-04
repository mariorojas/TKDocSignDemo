package com.mobbeel.mobbsign.teknei.demo.service;

import com.mobbeel.mobbsign.teknei.demo.model.responses.Customer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by marojas on 06/05/2017.
 */

public interface CustomerService {

    @FormUrlEncoded
    @Headers("Authorization: Basic dGVrbmVpOnRla24zMQ==")
    @POST("private/get.json")
    Call<Customer> getUserData(
            @Field("licenseId") String licenseId,
            @Field("scanId") String id
    );

}
