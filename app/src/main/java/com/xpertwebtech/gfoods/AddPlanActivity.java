package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddPlanActivity extends AppCompatActivity {
   private Toolbar toolbar;
   private Button addplane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
         addplane = findViewById(R.id.addplane);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Plane");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlanActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
         addplane.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(AddPlanActivity.this,MainActivity.class);
                 startActivity(intent);
             }
         });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddPlanActivity.this,MainActivity.class);
        startActivity(intent);
    }
}