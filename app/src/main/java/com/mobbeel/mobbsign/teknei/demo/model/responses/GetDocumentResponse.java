package com.mobbeel.mobbsign.teknei.demo.model.responses;

import com.google.gson.annotations.SerializedName;

import java.io.FileOutputStream;
import java.io.Serializable;

/**
 * Created by jearias (Mobbeel) on 30/04/17
 */
public class GetDocumentResponse extends BaseResponse implements Serializable {
    private FileOutputStream outputStream;

    public FileOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(FileOutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
