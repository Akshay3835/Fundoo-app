package com.bridgelabz.fundoo.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.bridgelabz.fundoo.Dashboard.DashboardActivity;
import com.bridgelabz.fundoo.LoginSignUp.View.LoginActivity;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.common.share_preference.SharePreferencesManager;

public class SplashActivity extends AppCompatActivity {


    public static final int SPLASH_DISPLAY_TIME = 2000;
    private SharePreferencesManager preferenceManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


            preferenceManager = new SharePreferencesManager(this);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openActivity();
                }
            }, SPLASH_DISPLAY_TIME);
        }

        private void openActivity() {
            Intent intent;
            if (preferenceManager.isLoggedOut()){

                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();
            }
            else {
                intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }

