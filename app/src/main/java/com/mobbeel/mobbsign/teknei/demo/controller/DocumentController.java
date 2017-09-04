package com.mobbeel.mobbsign.teknei.demo.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mobbeel.mobblicense.IOUtils;
import com.mobbeel.mobbsign.teknei.demo.model.responses.BaseResponse;
import com.mobbeel.mobbsign.teknei.demo.model.responses.UploadDocumentResponse;
import com.mobbeel.mobbsign.teknei.demo.service.RestAPI;
import com.mobbeel.mobbsign.teknei.demo.service.RestClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jearias (Mobbeel) on 01/05/2017.
 */

public class DocumentController {
    public static final String TAG = "Teknei demo";

    private static final String USERNAME = "user1"; //Solo vale para las pruebas sobre tas
    private static final String PASSWORD = "user1"; //Solo vale para las pruebas sobre tas
    private final RestClient restClient;

    public DocumentController(String baseUrl) {
        this.restClient = new RestClient(USERNAME, PASSWORD, baseUrl);
    }

    public void getDocument(/*String docId INE, */final Context context, final RestAPI.GetDocumentCallback callback) {
                Call<ResponseBody> call = restClient.getRestAPI().getDocument(/*docId INE*/); //TODO: enviar INE
                call.enqueue(new Callback<ResponseBody>() {//call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "DocumentController:getDocument-->onSuccess()");
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    callback.onGetDocumentSuccess(response.body().byteStream());
                                    return null;
                                }
                            }.execute();
                        } else {
                            Log.d(TAG, "DocumentController:getDocument-->onResponseNOSuccessful()");
                            callback.onGetDocumentFailure(response.code(), response.message());
                        }
                    }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "DocumentController:getDocument-->onFailure()");
                Log.e(TAG, t.getLocalizedMessage());
                callback.onGetDocumentFailure(BaseResponse.CODE_GENERIC_ERROR, "");
            }
        });
    }

    public void uploadDocument(/*String docId INE, */String filename, byte[] documentInBytes, final RestAPI.UploadDocumentCallback callback) {
        String contentType = "application/pdf";
        RequestBody requestBody = RequestBody.create(MediaType.parse(contentType), documentInBytes);
        MultipartBody.Part partFile = MultipartBody.Part.createFormData("file", filename, requestBody);

        //Call<UploadDocumentResponse> call = restClient.getRestAPI().uploadDocument(/*docId INE,*/ filename, contentType, partFile);
        Call<Void> call = restClient.getRestAPI().uploadDocument(/*docId INE,*/ filename, contentType, partFile);
        //call.enqueue(new Callback<UploadDocumentResponse>() {
        call.enqueue(new Callback<Void>() {
            @Override
            //public void onResponse(Call<UploadDocumentResponse> call, Response<UploadDocumentResponse> response) {
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "DocumentController:uploadDocument-->onSuccess()");
                    callback.onUploadDocumentSuccess(BaseResponse.CODE_OK, "");
                } else {
                    Log.d(TAG, "DocumentController:uploadDocument-->onResponseNOSuccessful()");
                    callback.onUploadDocumentFailure(BaseResponse.CODE_GENERIC_ERROR, "");
                }
            }

            @Override
            //public void onFailure(Call<UploadDocumentResponse> call, Throwable t) {
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "DocumentController:uploadDocument-->onFailure()");
                Log.e(TAG, t.getLocalizedMessage());
                callback.onUploadDocumentFailure(BaseResponse.CODE_GENERIC_ERROR, "");
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, Context context) {
        // todo change the file location/name according to your needs
        File file = new File(context.getExternalFilesDir(null), "filename");
        Log.d(TAG, "PATH: " + file.getAbsolutePath());
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = body.byteStream();
            outputStream = new FileOutputStream(file);

            IOUtils.copy(inputStream, outputStream);
            return true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return false;
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage());
                return false;
            }
        }
    }
}
