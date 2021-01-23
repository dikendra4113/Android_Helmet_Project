package com.example.androidtranning.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.example.androidtranning.R;
import com.example.androidtranning.util.LoadingDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import io.realm.Realm;
import utils.SharedPreferenceManager;


abstract public class BaseActivity extends AppCompatActivity {


    //    protected Api api;
//    protected LoadingDialog loadingDialog;
    protected Realm realm;
    protected HashMap<String, String> headers;
    protected SharedPreferenceManager pref;
    private Snackbar snackbar;
    private RelativeLayout mRlProgressContainer;
    private ProgressBar mProgressBar;
    private boolean isProgressVisible;

    protected LoadingDialog dialog;


    abstract protected int getLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        realm = Realm.getDefaultInstance();

//        pref = new SharedPreferenceManager(this, Const.APP_PREF_NAME);
//
//        headers = new HashMap<>();
//        headers.put("user-token", pref.getString(Const.PREF_TOKEN));

        if (getLayout() > 0) {
            setContentView(getLayout());
        }


        mRlProgressContainer = findViewById(R.id.rl_progress_root);
        mProgressBar = findViewById(R.id.progressbar);
        if (mRlProgressContainer != null) {
            mRlProgressContainer.setVisibility(View.GONE);
        }

        dialog = new LoadingDialog(this);

    }


    public void moveToNextScreen(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_enter_in, R.anim.slide_enter_out);
    }

    public void moveToNextScreenForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_enter_in, R.anim.slide_enter_out);
    }

    public void closeNmoveToNextScreen(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_enter_in, R.anim.slide_enter_out);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_exit_in);
    }


    protected void setUpSnackbar(View view) {
        if (snackbar == null)
            snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT);


    }

    protected void showMessage(String message) {
        if (snackbar != null) {
            snackbar.setText(message);
            snackbar.show();
        }
    }

    public void showProgressIndicators() {
        if (!isProgressVisible && mRlProgressContainer != null && mProgressBar != null) {
            mRlProgressContainer.bringToFront();
            mRlProgressContainer.setVisibility(View.VISIBLE);
            isProgressVisible = true;
        }
    }

    public void hideProgressIndicator() {
        if (isProgressVisible && mRlProgressContainer != null && mProgressBar != null) {
            mRlProgressContainer.setVisibility(View.GONE);
            isProgressVisible = false;
        }
    }
}
