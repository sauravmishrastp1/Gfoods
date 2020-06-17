package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import utils.SharedPrefManager;

public class EarnAndRefferActivity extends AppCompatActivity {
    private RelativeLayout backpress;
    private Button shareview;
    private String randomNumber;
    private Button refercode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_and_reffer);
        backpress = findViewById(R.id.backpresslayout);
        shareview = findViewById(R.id.sharenowww);
        refercode = findViewById(R.id.refercode);
        String refer_code= SharedPrefManager.getInstance(getApplicationContext()).getUser().getRefercode();
        refercode.setText(refer_code);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarnAndRefferActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        shareview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Referal code:="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getRefercode());
                final String appPackageName = getApplicationContext().getPackageName();
                 String app_url = "https://play.google.com/store/apps/details?id="+appPackageName;
                 shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });



//        char[] chars ="ABCDEFGHIJKLMNOPQRWXYZ123456789".toCharArray();
//        StringBuilder stringBuilder = new StringBuilder();
//        Random random = new Random();
//        for(int i =0;i<6;i++){
//            char c =chars[random.nextInt(chars.length)];
//            stringBuilder.append(c);
//        }
     //   randomNumber = stringBuilder.toString();
       // Toast.makeText(this, "hii"+randomNumber, Toast.LENGTH_SHORT).show();
    }
}
