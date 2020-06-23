package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapterclass.OrderAdapter;
import modelclass.OrderModel;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class OrderActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private ArrayList<OrderModel>orderModels = new ArrayList<>();
  private ProgressBar progressBar;
  private TextView deliveryboyname;
  private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyclerView = findViewById(R.id.orderrecyclerview);
        progressBar = findViewById(R.id.progressbarr);
        deliveryboyname = findViewById(R.id.name);
        imageView = findViewById(R.id.logout);
        String name = SharedPrefManager.getInstance(getApplicationContext()).getUser().getName();
        deliveryboyname.setText(name);
        Boolean islogin=SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn();
        if (!islogin)
        {
            startActivity(new Intent(OrderActivity.this,LoginActivity.class));
            finish();
        }
       getcategory();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });


    }

    private void getcategory(){
        orderModels.clear();
        progressBar.setVisibility(View.VISIBLE);
        String url = "http://xpertwebtech.in/gfood/api/order-dilevery-details";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray category = obj.getJSONArray("Order_dilevery_boy");
                            if (status.equals("200")) {
                                for(int i=0;i<category.length();i++) {
                                    JSONObject categoryJSONObject = category.getJSONObject(i);
                                    String pruductnbame = categoryJSONObject.getString("product_name");
                                    String productimg = categoryJSONObject.getString("image");
                                    String quant  = categoryJSONObject.getString("qunatity");
                                    String price = categoryJSONObject.getString("product_price");
                                    String id = categoryJSONObject.getString("id");
                                    String pid = categoryJSONObject.getString("product_id");
                                    String userid = categoryJSONObject.getString("user_id");
                                    String add = categoryJSONObject.getString("city");
                                    String date = categoryJSONObject.getString("date");
                                    String unit_price = categoryJSONObject.getString("unit_price");

                                    orderModels.add(new OrderModel(id,pid,date,quant,pruductnbame,"http://xpertwebtech.in/gfood/upload/"+productimg,add,price,unit_price));

                                    LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                                    recyclerView.setLayoutManager(layoutManager);
                                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                                    OrderAdapter gridProductAdapter = new OrderAdapter(orderModels,    OrderActivity.this);
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
                            Toast.makeText(OrderActivity.this, "somrthing went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this, "Server Not Responding" + error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}
