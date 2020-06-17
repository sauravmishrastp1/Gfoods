package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddMoneyLyoutActivity extends AppCompatActivity {
  private Button maypaymnet;
    private RelativeLayout backpress;
  private TextView amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_lyout);
        amount = findViewById(R.id.amounttxt);
        maypaymnet = findViewById(R.id.makepaymnet);
        backpress = findViewById(R.id.backpresslayout);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMoneyLyoutActivity.this,MyWalletActivity.class);
                startActivity(intent);
            }
        });
        amount.setText("Amount"+" "+"\u20B9"+"200");
        maypaymnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMoneyLyoutActivity.this,SelectPayemnt.class);
                startActivity(intent);
            }
        });
    }
}
