package com.mobbeel.mobbsign.teknei.demo.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jearias (Mobbeel) on 30/04/17.
 */
public class ErrorResponse {
    @SerializedName("message")
    private String errorMsg;

    public ErrorResponse(String errorMsg, int codError) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
