package com.mobbeel.mobbsign.teknei.demo.model.responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jearias (Mobbeel) on 30/04/17
 */
public class UploadDocumentResponse extends BaseResponse implements Serializable {
    @SerializedName("file_added")
    private String fileAdded;
    @SerializedName("id")
    private String id;

    public UploadDocumentResponse(String fileAdded, String id) {
        this.fileAdded = fileAdded;
        this.id = id;
    }

    public UploadDocumentResponse() {
    }

    public String getFileAdded() {
        return fileAdded;
    }

    public void setFileAdded(String fileAdded) {
        this.fileAdded = fileAdded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
