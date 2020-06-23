package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapterclass.OrderHistoryAdapter;
import modelclass.ViewBillModel;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class OrderHitoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView previous,next;
    private TextView datetxt;
    String date;
    private RelativeLayout backpress;
    private ArrayList<ViewBillModel> vactionmodelss = new ArrayList<>();
    private TextView text;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private View mynullplaylaypout;
    private Button addplanybutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_hitory);
        final Calendar c = Calendar.getInstance();
        previous = findViewById(R.id.previousmonth);
        addplanybutton = findViewById(R.id.buttonaddplane);
        toolbar = findViewById(R.id.toolbar);
        next = findViewById(R.id.nextmonth);
        mynullplaylaypout = findViewById(R.id.nullmyplanelayout);
        progressBar = findViewById(R.id.progressbarr);
        //imageView = findViewById(R.id.referimg);
        recyclerView = findViewById(R.id.recycelerview);
        toolbar.setTitle("Order History");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        vactionmodelss.clear();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderHitoryActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        addplanybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderHitoryActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        datetxt = findViewById(R.id.date);
        //System.out.println("Current time => " + c.getTime());
        Bundle bundle = new Bundle();
        date = bundle.getString("date");
        //Toast.makeText(this, "date=>"+date, Toast.LENGTH_SHORT).show();

        final SimpleDateFormat df = new SimpleDateFormat("ddMMMyyyy");
        final String[] formattedDate = {df.format(c.getTime())};

        datetxt.setText(formattedDate[0]);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.DATE, 1);
                formattedDate[0] = df.format(c.getTime());
                datetxt.setText(formattedDate[0]);

                getvactiondata();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.DATE, -1);
                formattedDate[0] = df.format(c.getTime());
                datetxt.setText(formattedDate[0]);
                getvactiondata();
            }
        });
        vactionmodelss.clear();
        getvactiondata();
    }

    private void getvactiondata(){
        vactionmodelss.clear();
        progressBar.setVisibility(View.VISIBLE);
        String url ="http://xpertwebtech.in/gfood/api/user-order-details?user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray state = obj.getJSONArray("user_order_details");

                            if (status.equals("200")) {


                                for(int j=0;j<state.length();j++) {

                                    JSONObject stateJSONObject = state.getJSONObject(j);
                                    String dat1 = stateJSONObject.getString("start_date");
                                    String productid = stateJSONObject.getString("product_id");
                                    String id = stateJSONObject.getString("id");
                                    String date2 = stateJSONObject.getString("end_date");
                                    String orderstatus = stateJSONObject.getString("order_status");
                                    String img = stateJSONObject.getString("image");
                                    String quant = stateJSONObject.getString("qunatity");
                                    String pricee = stateJSONObject.getString("" + "price");
                                    String invoice = stateJSONObject.getString("invoice_no");
                                    String dailyy = stateJSONObject.getString("plan_type");
                                    String productname = stateJSONObject.getString("product_name");

                                    if(!quant.equals("0")&&!pricee.equals("0")){
                                        vactionmodelss.add(new ViewBillModel(invoice,dat1,date2,orderstatus,"http://xpertwebtech.in/gfood/upload/"+img,pricee,dailyy,productid,quant,productname,id));
                                        LinearLayoutManager gridLayoutManager1 = new LinearLayoutManager(OrderHitoryActivity.this);

                                        recyclerView.setLayoutManager(gridLayoutManager1);
                                        OrderHistoryAdapter gridProductAdapter = new OrderHistoryAdapter(vactionmodelss, OrderHitoryActivity.this);
                                        recyclerView.setAdapter(gridProductAdapter);
                                        gridProductAdapter.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
                                    }





                                }
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                                mynullplaylaypout.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OrderHitoryActivity.this, "No order Yet!", Toast.LENGTH_SHORT).show();
                            // imageView.setVisibility(View.VISIBLE);
                            //text.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            mynullplaylaypout.setVisibility(View.VISIBLE);

                            progressBar.setVisibility(View.GONE);

                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderHitoryActivity.this, "Server Not Responding"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        mynullplaylaypout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}