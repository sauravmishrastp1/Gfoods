package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MyPlanActivity extends AppCompatActivity {
    private RelativeLayout backpress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);
        backpress = findViewById(R.id.backpresslayout);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPlanActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
