package com.mobbeel.mobbsign.teknei.demo.service;


import com.mobbeel.mobbsign.teknei.demo.model.responses.BaseResponse;
import com.mobbeel.mobbsign.teknei.demo.model.responses.GetDocumentResponse;
import com.mobbeel.mobbsign.teknei.demo.model.responses.UploadDocumentResponse;

import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface RestAPI {

    public interface GetDocumentCallback {
        void onGetDocumentSuccess(InputStream inputStream);

        void onGetDocumentFailure(int code, String message);
    }

    public interface UploadDocumentCallback {
        void onUploadDocumentSuccess(int code, String message);

        void onUploadDocumentFailure(int code, String message);
    }

    @Streaming
    //@GET("/tas/rest/api/v1/files/{docId}")
    //Call<ResponseBody> getDocument(@Path("docId") String docId);
    @GET("/DemoServer/rest/v1/contratos")
    Call<ResponseBody> getDocument(/*@Path("docId") String docId INE*/);

    @Multipart
    //@POST("/tas/rest/api/v1/files/{docId}")
    //Call<UploadDocumentResponse> uploadDocument(@Path("docId") String docId, @Query("fileName") String filename, @Query("contentType") String contentType, @Part MultipartBody.Part file);
    @POST("/DemoServer/rest/v1/contratos")
    Call<Void> uploadDocument(/*@Path("docId") String docId INE,*/ @Query("fileName") String filename, @Query("contentType") String contentType, @Part MultipartBody.Part file);

}
