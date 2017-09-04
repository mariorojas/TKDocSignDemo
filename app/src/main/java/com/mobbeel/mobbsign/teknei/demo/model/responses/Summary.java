package com.mobbeel.mobbsign.teknei.demo.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marojas on 13/06/2017.
 */

public class Summary {

    @SerializedName("Captura de credencial")
    private String capturaCredencial;

    @SerializedName("Validacion INE")
    private String validacionIne;

    @SerializedName("Validacion Rostro")
    private String validacionRostro;

    @SerializedName("Enrolamiento biometrico")
    private String enrolamientoBiometrico;

    public Summary(String capturaCredencial, String validacionIne, String validacionRostro, String enrolamientoBiometrico) {
        this.capturaCredencial = capturaCredencial;
        this.validacionIne = validacionIne;
        this.validacionRostro = validacionRostro;
        this.enrolamientoBiometrico = enrolamientoBiometrico;
    }

    @Override
    public String toString() {
        return "Summary{capturaCredencial=" + capturaCredencial
                + ", validacionIne=" + validacionIne
                + ", validacionRostro=" + validacionRostro
                + ", enrolamientoBiometrico=" + enrolamientoBiometrico
                + "}";
    }

    public String getCapturaCredencial() {
        return capturaCredencial;
    }

    public void setCapturaCredencial(String capturaCredencial) {
        this.capturaCredencial = capturaCredencial;
    }

    public String getValidacionIne() {
        return validacionIne;
    }

    public void setValidacionIne(String validacionIne) {
        this.validacionIne = validacionIne;
    }

    public String getValidacionRostro() {
        return validacionRostro;
    }

    public void setValidacionRostro(String validacionRostro) {
        this.validacionRostro = validacionRostro;
    }

    public String getEnrolamientoBiometrico() {
        return enrolamientoBiometrico;
    }

    public void setEnrolamientoBiometrico(String enrolamientoBiometrico) {
        this.enrolamientoBiometrico = enrolamientoBiometrico;
    }

}
