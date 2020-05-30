package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapterclass.MyProductcategory;
import modelclass.MyPlanProductCat;
import modelclass.User;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class HelpActivity extends AppCompatActivity {
    private RelativeLayout backpress;
    private TextView paragraph1;
    private TextView paragraph2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        paragraph1 = findViewById(R.id.discription);
        paragraph2 =  findViewById(R.id.text2);
        backpress = findViewById(R.id.backpresslayout);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
      private void gethelpdata()
      {
        String url = "http://lsne.in/gfood/api/help-us";
          StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {


                          try {
                              JSONObject obj = new JSONObject(response);
                              String status = obj.getString("status");
                              JSONArray HELP = obj.getJSONArray("sub_cat");
                              if (status.equals("200")) {
                                  for(int i=0;i<HELP.length();i++) {
                                      JSONObject help = HELP.getJSONObject(i);
                                      String title1 = help.getString("");



                                  }
                              } else {

                                  Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                  // progressBar.setVisibility(View.GONE);
                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                              Toast.makeText(HelpActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                              // progressBar.setVisibility(View.GONE);
                          }
                      }
                  },
                  new Response.ErrorListener() {

                      @Override
                      public void onErrorResponse(VolleyError error) {
                          Toast.makeText(HelpActivity.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                          // progressBar.setVisibility(View.GONE);
                      }
                  }) {

          };
          VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
          VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
      }

}
