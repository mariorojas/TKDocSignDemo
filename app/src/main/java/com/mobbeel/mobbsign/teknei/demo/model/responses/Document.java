package com.mobbeel.mobbsign.teknei.demo.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marojas on 07/05/2017.
 */

public class Document {

    @SerializedName("name")
    private String name;

    @SerializedName("surname")
    private String surname;

    @SerializedName("curp")
    private String curp;

    @SerializedName("address")
    private String address;

    @SerializedName("mrz")
    private String ocr;

    public Document(String name, String surname, String curp,
                    String address, String ocr) {
        this.name = name;
        this.surname = surname;
        this.curp = curp;
        this.address = address;
        this.ocr = ocr;
    }

    @Override
    public String toString() {
        return "Documento {name=" + name
                + ", surname=" + surname
                + ", curp=" + curp
                + ", address=" + address
                + ", ocr=" + getOcr()
                + "}";
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getCurp() {
        return this.curp;
    }

    public String getAddress() {
        return this.address;
    }

    public String getOcr() {
        int index = 17;
        return ocr.substring(index, index+13);
    }
}
