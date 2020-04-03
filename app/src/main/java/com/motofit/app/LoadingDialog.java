package com.motofit.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {
    private Activity Fragment;
    private AlertDialog alert;

    public LoadingDialog(Activity context) {
        Fragment = context;
    }

    @SuppressLint("InflateParams")
    public void startsLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Fragment);
        LayoutInflater inflater = Fragment.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loadingdialog, null));
        builder.setCancelable(false);
        alert = builder.create();
        alert.show();
    }

    public void dismissLoading() {
        alert.dismiss();
    }
}
