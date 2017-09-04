package com.mobbeel.mobbsign.teknei.demo.model.responses;

import com.google.gson.annotations.SerializedName;

import java.io.FileOutputStream;

/**
 * Created by marojas on 09/05/2017.
 */

public class Voucher {

    @SerializedName("INE")
    private String ine;

    @SerializedName("scanId")
    private String scanId;

    @SerializedName("comprobante")
    private VoucherDetails voucherDetails;

    @SerializedName("expedienteUUID")
    private String expedienteUUID;

    public Voucher(String ine, String scanId, VoucherDetails voucherDetails,
                   String expedienteUUID) {
        this.ine = ine;
        this.scanId = scanId;
        this.voucherDetails = voucherDetails;
        this.expedienteUUID = expedienteUUID;
    }

    @Override
    public String toString() {
        return "Voucher {ine=" + ine
                + ", scanId=" + scanId
                + ", expedienteUUID" + expedienteUUID
                + "}";
    }

    public String getIne() {
        return this.ine;
    }

    public String getScanId() {
        return this.scanId;
    }

    public VoucherDetails getVoucherDetails() {
        return this.voucherDetails;
    }

    public String getExpedienteUUID() {
        return this.expedienteUUID;
    }

}
