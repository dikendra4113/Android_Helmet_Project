package com.example.androidtranning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidtranning.application.IP;
import com.example.androidtranning.models.UserDetails;
import com.example.androidtranning.util.Prevelent;
import com.example.androidtranning.webservices.user.ApiEmailLogin;

import java.util.HashMap;

import io.paperdb.Paper;
import io.reactivex.ObservableSource;
import utils.ApiHandler;
import utils.ApiTaskDelegate;
import utils.Net;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserEmailEt,mUserPassEt;
    private Button mLoginbtn;
    private UserDetails details;
    public ProgressDialog loadingprocess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserEmailEt = findViewById(R.id.usernameEditText);

        mLoginbtn = findViewById(R.id.loginButton);
        Paper.init(this);
        loadingprocess =new  ProgressDialog(this);

        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text =  mUserEmailEt.getText().toString().trim();
                if(TextUtils.isEmpty(text)){
                    Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                else{
                        showLoadinBar();
                        checkUserCredential(text);
                }
            }
        });

    }

    private void showLoadinBar() {
        loadingprocess.setTitle("Please Wait");
        loadingprocess.setMessage("Checking credentials");
        loadingprocess.setCanceledOnTouchOutside(false);
        loadingprocess.show();
    }

    private void checkUserCredential(String text) {
        ApiHandler.setBaseURL(IP.ADDRESS);
        ApiEmailLogin caller = new ApiEmailLogin().setEmail(text).setFcm_token("sdf123aslkdslfksjfklsfsljfsdfh12j31l231l3lkasdlkajdalkjljasds");
        ObservableSource<String> observableSource = caller.getAPI().getReponse(caller.getURL(),
                new HashMap<String, String>(),
                caller.getParams());


        HashMap<String, ObservableSource<String>> req = new HashMap<>();
        req.put("UserEmailLogin", observableSource);
        Net.getInstance(this).setRequestZip(req).doMakeApiCall(new ApiTaskDelegate() {
            @Override
            public void onTaskCompleted(HashMap<String, String> observables) {
                String response = observables.get("UserEmailLogin");
                if (response != null) {
                    Log.i("response", response);
                    ApiEmailLogin login = new ApiEmailLogin();
                    details = login.getUserDetails(response);
                    Paper.book().write(Prevelent.USER_TOKEN,details.userToken);
                    Paper.book().write(Prevelent.PREF_FCM,details.fcmToken);
                    Paper.book().write(Prevelent.APP_USER,details.userFirstName);
                    Log.i("Log atlll",details.userLongitude + "+++"+ details.userLatitude);
//                    Paper.book().write(Prevelent.PREF_LATITUDE,details.userLatitude);
//                    Paper.book().write(Prevelent.PREF_LONGITUTE,details.userLongitude);

                    moveToHomePage();

                }
            }

            @Override
            public void onErrorOccured(String error) {
                Log.e("Error", "onErrorOccured: " + error);
            }
        });
    }

    private void moveToHomePage() {
        loadingprocess.dismiss();
        Intent intent =new Intent(getApplicationContext(),HomeScreenActivity.class);

        startActivity(intent);
    }


}