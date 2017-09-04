package com.mobbeel.mobbsign.teknei.demo.view;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Toast;

import com.mobbeel.mobbsign.teknei.demo.R;

public class BaseActivity extends AppCompatActivity {

    public static final String TAG = "Teknei demo";

    ProgressDialog progressDialog;

    protected void initProgress() {
        progressDialog = new ProgressDialog(this);
        //MCG inicio
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Realizando petición...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        //MCG fin
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void showProgress(String title, String message) {
        showProgress(title, message, true);
    }

    public void showProgress(String title, String message, boolean cancelOnTouchOutside) {
        progressDialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
        if (!isFinishing()) {
            if (title != null) {
                progressDialog.setTitle(title);
            }
            if (message != null) {
                progressDialog.setMessage(message);
            }
            progressDialog.show();
        }
    }

    public void hideProgress() {
        if (!isFinishing()) {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    protected void createToast(int message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void createDialog(int message, final boolean finish) {
        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog))
                .setMessage(message)
                .setPositiveButton(getString(R.string.dialog_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (finish) {
                            finish();
                        }
                    }
                })
                .setCancelable(false)
                .show();
    }


}
