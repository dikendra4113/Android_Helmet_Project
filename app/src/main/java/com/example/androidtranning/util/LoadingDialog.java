package com.example.androidtranning.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;


import com.example.androidtranning.R;

import java.util.Objects;

public class LoadingDialog {

    private Dialog loadingDialog;

    public LoadingDialog(Context context) {
        loadingDialog = new Dialog(context, R.style.progress_bar);
        @SuppressLint("InflateParams")
        View dialog = LayoutInflater.from(context).inflate(R.layout.loading_layout, null);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(R.color.colorHalfGrey);
        loadingDialog.setContentView(dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);

        ProgressBar mIvLoader = loadingDialog.findViewById(R.id.iv_dialog_loader);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mIvLoader.getIndeterminateDrawable().setColorFilter(context.getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        }

    }

    public void showDialog() {
        if (loadingDialog != null)
            loadingDialog.show();
    }

    public void hideDialog() {
        if (loadingDialog != null)
            loadingDialog.hide();
    }
}
