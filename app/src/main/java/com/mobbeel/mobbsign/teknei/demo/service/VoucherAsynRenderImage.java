package com.mobbeel.mobbsign.teknei.demo.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.mobbeel.mobbsign.teknei.demo.R;
import com.mobbeel.mobbsign.teknei.demo.view.VoucherActivity;

import java.io.InputStream;

/**
 * Created by marojas on 10/05/2017.
 */

public class VoucherAsynRenderImage extends AsyncTask<Void, Void, Void> {

    private VoucherActivity voucherActivity;
    private ImageView voucherImage;
    private InputStream stream;
    private Bitmap bitmap;

    public VoucherAsynRenderImage(VoucherActivity voucherActivity, ImageView voucherImage, InputStream stream) {
        this.voucherActivity = voucherActivity;
        this.voucherImage = voucherImage;
        this.stream = stream;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(this.getClass().getName(), "Desplegando imagen ");
        bitmap = BitmapFactory.decodeStream(stream);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        voucherImage.setImageBitmap(bitmap);
        Log.d(this.getClass().getName(), "Imagen desplegada");
    }
}
