package com.mobbeel.mobbsign.teknei.demo.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobbeel.mobblicense.IOUtils;
import com.mobbeel.mobbsign.teknei.demo.R;
import com.mobbeel.mobbsign.teknei.demo.controller.DocumentController;
import com.mobbeel.mobbsign.teknei.demo.model.responses.Summary;
import com.mobbeel.mobbsign.teknei.demo.model.responses.Voucher;
import com.mobbeel.mobbsign.teknei.demo.service.ClientWS;
import com.mobbeel.mobbsign.teknei.demo.service.RestAPI;
import com.mobbeel.mobbsign.teknei.demo.service.VoucherAsynRenderImage;
import com.mobbeel.mobbsign.teknei.demo.service.ExtraService;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherActivity extends BaseActivity {

    private static final String DOC_ID = "0000013ee31c54e348a98eaed7d221858a40";
    private static final int RC_MOBBSIGN = 1;

    private ClientWS clientWS;
    private ExtraService extraService;

    private Button btnContrato;
    private Button btnSummary;

    private ImageView voucherImage;

    private TextView lblVoucherName;
    private TextView lblVoucherDocumentType;
    private TextView lblVoucherDate;
    private TextView lblVoucherAddress;
    private TextView lblVoucherReference;

    private DocumentController documentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        Log.d(this.getClass().getName(), "Voucher activity launched");

        btnContrato = (Button) findViewById(R.id.ButtonContrato);
        btnContrato.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(this.getClass().getName(), "Inicia proceso para obtener el contrato");
                initProgress();
                documentController = new DocumentController(PreferenceManager.getDefaultSharedPreferences(v.getContext()).getString("url", ""));
                getDocument();
            }
        });

        btnSummary = (Button) findViewById(R.id.btnSummary);
        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_get_sumary);
                dialog.setTitle("Resumen");

                Button btnCloseDialog = (Button) dialog.findViewById(R.id.btnCloseDialog);
                btnCloseDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                final TextView lblValCapturaCred = (TextView) dialog.findViewById(R.id.lblValCapturaCred);
                final TextView lblValValidacionINE = (TextView) dialog.findViewById(R.id.lblValValidacionINE);
                final TextView lblValValidacionRostro = (TextView) dialog.findViewById(R.id.lblValValidacionRostro);
                final TextView lblValEnrolamientoBio = (TextView) dialog.findViewById(R.id.lblValEnrolamientoBio);

                ClientWS.BASE_URL = "http://201.99.106.95:18080/morpho-test/";
                clientWS = ClientWS.getInstance();
                extraService = clientWS.buildRetrofit().create(ExtraService.class);
                Call<Summary> summaryCall = extraService.getSummary();
                summaryCall.enqueue(new Callback<Summary>() {
                    @Override
                    public void onResponse(Call<Summary> call, Response<Summary> response) {
                        Log.i(this.getClass().getName(), "Petición exitosa");
                        Summary summary = response.body();
                        lblValCapturaCred.setText(summary.getCapturaCredencial());
                        lblValValidacionINE.setText(summary.getValidacionIne());
                        lblValValidacionRostro.setText(summary.getValidacionRostro());
                        lblValEnrolamientoBio.setText(summary.getEnrolamientoBiometrico());
                    }

                    @Override
                    public void onFailure(Call<Summary> call, Throwable t) {
                        Log.i(this.getClass().getName(), "Petición fallida");
                    }
                });

                dialog.show();
            }
        });

        voucherImage = (ImageView) findViewById(R.id.imgVoucher);

        lblVoucherName = (TextView) findViewById(R.id.lblVoucherName);
        lblVoucherDocumentType = (TextView) findViewById(R.id.lblVoucherDocumentType);
        lblVoucherDate = (TextView) findViewById(R.id.lblVoucherDate);
        lblVoucherAddress = (TextView) findViewById(R.id.lblVoucherAddress);
        lblVoucherReference = (TextView) findViewById(R.id.lblVoucherReference);

        initProgress();

        showVoucher();
    }

    private void showVoucher() {
        ClientWS.BASE_URL = PreferenceManager.getDefaultSharedPreferences(this).getString("url", "");
        clientWS = ClientWS.getInstance();
        extraService = clientWS.buildRetrofit().create(ExtraService.class);
        Call<ResponseBody> voucherCall = extraService.getVoucherImage();
        voucherCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                Log.d(this.getClass().getName(), "OK");
                Log.d(this.getClass().getName(), "codeResponse: " + response.code());

                if (response.code() == 200) {
                    final InputStream stream = response.body().byteStream();
                    new VoucherAsynRenderImage(VoucherActivity.this, voucherImage, stream).execute();
                    setVoucherInfo();
                } else {
                    performClick();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                Log.d(this.getClass().getName(), "Error al obtener el comprobante");
                performClick();
            }
        });
    }

    public void setVoucherInfo() {
        clientWS = ClientWS.getInstance();
        extraService = clientWS.buildRetrofit().create(ExtraService.class);
        Call<Voucher> voucherCall = extraService.getVoucherData();
        voucherCall.enqueue(new Callback<Voucher>() {
            @Override
            public void onResponse(Call<Voucher> call, Response<Voucher> response) {
                Voucher voucher = response.body();
                lblVoucherName.setText("Nombre: " + voucher.getVoucherDetails().getName());
                lblVoucherDocumentType.setText("Tipo de Documento: " + voucher.getVoucherDetails().getDocumentType());
                lblVoucherDate.setText("Fecha: " + voucher.getVoucherDetails().getDate());
                lblVoucherAddress.setText("Dirección: " + voucher.getVoucherDetails().getAddress());
                lblVoucherReference.setText("Referencia: " + voucher.getVoucherDetails().getReference());
            }

            @Override
            public void onFailure(Call<Voucher> call, Throwable t) {

            }
        });
    }

    /**
     * Document methods
     */

    private void performClick() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnContrato.performClick();
            }
        });
    }

    private void getDocument() {

        documentController.getDocument(getApplicationContext(), new RestAPI.GetDocumentCallback() {
            @Override
            public void onGetDocumentSuccess(InputStream inputStream) {
                hideProgress();
                Log.d(TAG, "onGetDocumentSuccess");
                openMobbSign(inputStream);
            }

            @Override
            public void onGetDocumentFailure(int code, String message) {
                hideProgress();
                Log.e(TAG, "onGetDocumentFailure: " + code + " " + message);
                Toast toast = Toast.makeText(VoucherActivity.this, "No se pudo obtener el contrato", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private void openMobbSign(InputStream inputStream) {
        Intent intent = new Intent(VoucherActivity.this, MobbSignActivity.class);
        try {
            intent.putExtra(MobbSignActivity.EXTRA_DOCUMENT, IOUtils.toByteArray(inputStream));
            intent.putExtra(MobbSignActivity.EXTRA_DOC_ID, DOC_ID);
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        startActivityForResult(intent, RC_MOBBSIGN);
    }

}
