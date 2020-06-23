package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapterclass.OfferAdapter;
import modelclass.OfferModel;
import utils.VolleySingleton;

public class OffersActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private ArrayList<OfferModel>offerModels = new ArrayList<>();
  private RelativeLayout backpress;
  private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        recyclerView = findViewById(R.id.offerrecyclerview);
        backpress = findViewById(R.id.backpresslayout);
        progressBar = findViewById(R.id.progress_circular);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OffersActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


      getoffer();
    }

    private void getoffer(){

     progressBar.setVisibility(View.VISIBLE);
        String url ="http://xpertwebtech.in/gfood/api/offer-product";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray profiledata = obj.getJSONArray("offer_product");
                            if (status.equals("200")) {
                                for(int i=0;i<profiledata.length();i++) {
                                    JSONObject profiledataJSONObject = profiledata.getJSONObject(i);
                                    String productimg = profiledataJSONObject.getString("image");
                                    String offer = profiledataJSONObject.getString("off_percent");
                                    String productname = profiledataJSONObject.getString("product_name");
                                    String offerdiscount = profiledataJSONObject.getString("promo_code");

                                    offerModels.add(new OfferModel("http://xpertwebtech.in/gfood/upload/"+productimg,offer,productname));

                                    LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                                    recyclerView.setLayoutManager(layoutManager);
                                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                                    OfferAdapter gridProductAdapter = new OfferAdapter(offerModels,    OffersActivity.this);
                                    recyclerView.setAdapter(gridProductAdapter);
                                    gridProductAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                }
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                 progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OffersActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                             progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OffersActivity.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                         progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    }

