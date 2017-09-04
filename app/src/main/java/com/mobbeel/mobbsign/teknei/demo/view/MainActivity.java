package com.mobbeel.mobbsign.teknei.demo.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.preference.PreferenceManager;
import android.widget.Button;

import com.mobbeel.mobblicense.IOUtils;
import com.mobbeel.mobbsign.teknei.demo.R;
import com.mobbeel.mobbsign.teknei.demo.controller.DocumentController;
import com.mobbeel.mobbsign.teknei.demo.service.RestAPI;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends BaseActivity {

    private static final String DOC_ID = "0000013ee31c54e348a98eaed7d221858a40";
    private static final int RC_MOBBSIGN = 1;

    DocumentController documentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();//Ocultar ActivityBar anterior
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        try {
            setSupportActionBar(myToolbar);
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        // Cargar valores por defecto
        PreferenceManager.setDefaultValues(this, R.xml.mypreferences, false);

        Button button = (Button) findViewById(R.id.ButtonContrato);
        button.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                //initProgress();
                documentController = new DocumentController(PreferenceManager.getDefaultSharedPreferences(v.getContext()).getString("url", ""));
                getDocument();
            }
        });

        /**
         * Sentencias para lanzar actividad GetUserDataActivity
         */
        Button btnGetUserData = (Button) findViewById(R.id.btnGetVoucher);
        btnGetUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VoucherActivity.class);
                startActivity(intent);
            }
        });
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onConfigurationChanged(Configuration config) {
//        if (progressDialog != null) {
//            progressDialog.dismiss();
//            initProgress();
//        }
//    }

    private void getDocument() {

        //documentController.getDocument(DOC_ID, getApplicationContext(), new RestAPI.GetDocumentCallback() {
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
                    Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
            }
        });
    }

    private void openMobbSign(InputStream inputStream) {
        Intent intent = new Intent(MainActivity.this, MobbSignActivity.class);
        try {
            intent.putExtra(MobbSignActivity.EXTRA_DOCUMENT, IOUtils.toByteArray(inputStream));
            intent.putExtra(MobbSignActivity.EXTRA_DOC_ID, DOC_ID);
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        startActivityForResult(intent, RC_MOBBSIGN);
    }
}