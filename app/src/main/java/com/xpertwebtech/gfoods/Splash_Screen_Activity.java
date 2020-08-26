package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import utils.SharedPrefManager;

public class Splash_Screen_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen_);
        String city = SharedPrefManager.getInstance(getApplicationContext()).getUser().getCity();
        if (city != null) {
            if (city.equals("1") && city != null) {
                new Handler().postDelayed(new Runnable() {


                    @Override
                    public void run() {

                        // This method will be executed once the timer is over
                        Intent i = new Intent(Splash_Screen_Activity.this, OrderActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 3000);
            } else {
                new Handler().postDelayed(new Runnable() {


                    @Override
                    public void run() {

                        // This method will be executed once the timer is over
                        Intent i = new Intent(Splash_Screen_Activity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 3000);
            }
        } else {
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {

                    // This method will be executed once the timer is over
                    Intent i = new Intent(Splash_Screen_Activity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }
    }
}
