package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import utils.SharedPrefManager;

public class SplaashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splaash_screen);
        String city = SharedPrefManager.getInstance(getApplicationContext()).getUser().getCity();
        if(city !=null) {
            if (city.equals("1") && city != null) {
                new Handler().postDelayed(new Runnable() {


                    @Override
                    public void run() {

                        // This method will be executed once the timer is over
                        Intent i = new Intent(SplaashScreen.this, OrderActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 3000);
            } else {
                new Handler().postDelayed(new Runnable() {


                    @Override
                    public void run() {

                        // This method will be executed once the timer is over
                        Intent i = new Intent(SplaashScreen.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 3000);
            }
        }else {
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {

                    // This method will be executed once the timer is over
                    Intent i = new Intent(SplaashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }
    }
}
