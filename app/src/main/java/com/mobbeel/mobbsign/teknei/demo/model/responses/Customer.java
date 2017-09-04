package com.mobbeel.mobbsign.teknei.demo.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marojas on 06/05/2017.
 */

public class Customer {

    @SerializedName("scanId")
    private String scanId;

    @SerializedName("documentType")
    private String documentType;

    @SerializedName("document")
    private Document document;

    public Customer(String scanId, String documentType, Document document) {
        this.scanId = scanId;
        this.documentType = documentType;
        this.document = document;
    }

    @Override
    public String toString() {
        return "Cliente {scanId=" + scanId
                + ", documentType=" + documentType
                + "} " + document.toString();
    }

    public String getScanId() {
        return this.scanId;
    }

    public String getDocumentType() {
        return this.documentType;
    }

    public Document getDocument() {
        return this.document;
    }
}
