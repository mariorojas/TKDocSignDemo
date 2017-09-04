package com.mobbeel.mobbsign.teknei.demo.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marojas on 10/05/2017.
 */

public class VoucherDetails {

    @SerializedName("Nombre")
    private String name;

    @SerializedName("TipoDocumento")
    private String documentType;

    @SerializedName("Fecha")
    private String date;

    @SerializedName("Direccion")
    private String address;

    @SerializedName("Referencia")
    private String reference;

    public VoucherDetails(String name, String documentType, String date,
                          String address, String reference) {
        this.name = name;
        this.documentType = documentType;
        this.date = date;
        this.address = address;
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "ComprobanteDetalles {name=" + name
                + ", documentType=" + documentType
                + ", date=" + date
                + ", address=" + address
                + ", reference" + reference
                + "}";
    }

    public String getName() {
        return name;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public String getReference() {
        return reference;
    }
}
