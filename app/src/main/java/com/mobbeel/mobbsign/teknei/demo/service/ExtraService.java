package com.mobbeel.mobbsign.teknei.demo.service;

import com.mobbeel.mobbsign.teknei.demo.model.responses.Summary;
import com.mobbeel.mobbsign.teknei.demo.model.responses.Voucher;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * Created by marojas on 09/05/2017.
 */

public interface ExtraService {

    @Streaming
    @GET("rest/v1/comprobantes")
    Call<ResponseBody> getVoucherImage();

    @GET("rest/v1/comprobantes_info")
    Call<Voucher> getVoucherData();

    @GET("api/customer/getResume")
    Call<Summary> getSummary();

}
