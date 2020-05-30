package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectPayemnt extends AppCompatActivity {
    private TextView amount;
    private RelativeLayout backpress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payemnt);
        amount = findViewById(R.id.amountttt);
        amount.setText("\u20B9"+"200");
        backpress = findViewById(R.id.backpresslayout);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectPayemnt.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
