package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
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

import adapterclass.UpcomingAdapter;
import modelclass.UpcomingModel;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class CalenderWiseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView previous,next;
    private TextView datetxt;
    String date;
    private RelativeLayout backpress;
    private ArrayList<UpcomingModel> vactionmodelss = new ArrayList<>();
    private TextView text;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private String userid;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_wise);
        final Calendar c = Calendar.getInstance();
        previous = findViewById(R.id.previousmonth);
        toolbar = findViewById(R.id.toolbar);
        next = findViewById(R.id.nextmonth);
        progressBar = findViewById(R.id.progressbarr);
        //imageView = findViewById(R.id.referimg);
        recyclerView = findViewById(R.id.recycelerview);
        toolbar.setTitle("Up Coming");
        vactionmodelss.clear();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        userid = SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalenderWiseActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        datetxt = findViewById(R.id.date);
        //System.out.println("Current time => " + c.getTime());
        bundle = getIntent().getExtras();
        date = bundle.getString("date");
        Toast.makeText(this, "date=>"+date, Toast.LENGTH_SHORT).show();
        final SimpleDateFormat df = new SimpleDateFormat("ddMMMyyyy");
        final String[] formattedDate = {df.format(c.getTime())};
        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final Calendar actualDate = Calendar.getInstance();
        datetxt.setText(formattedDate[0]);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualDate.add(Calendar.DATE,1);
                date=sdf.format(actualDate.getTime());
                getvactiondata();
                c.add(Calendar.DATE, 1);
                formattedDate[0] = df.format(c.getTime());
                datetxt.setText(formattedDate[0]);

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualDate.add(Calendar.DATE,-1);
                date=sdf.format(actualDate.getTime());
                getvactiondata();
                c.add(Calendar.DATE, -1);
                formattedDate[0] = df.format(c.getTime());
                datetxt.setText(formattedDate[0]);

            }
        });
        vactionmodelss.clear();
        getvactiondata();
    }

    private void getvactiondata(){
        Toast.makeText(this, "date=>"+date, Toast.LENGTH_SHORT).show();
        vactionmodelss.clear();
        progressBar.setVisibility(View.VISIBLE);
        String url ="http://lsne.in/gfood/api/upcoming-delivery-by-date?user_id="+userid+"&date="+date;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray state = obj.getJSONArray("delivery_details_by_date");

                            if (status.equals("200")) {


                                for(int j=0;j<state.length();j++) {

                                    JSONObject stateJSONObject = state.getJSONObject(j);
                                    String img = stateJSONObject.getString("image");
                                    String quant = stateJSONObject.getString("qunatity");
                                    String productname = stateJSONObject.getString("product_name");
                                   String deliverystatus = stateJSONObject.getString("mark_dileverd");
                                   if(deliverystatus.equals("0")){
                                       vactionmodelss.add(new UpcomingModel("http://lsne.in/gfood/upload/"+img,productname,quant));

                                       LinearLayoutManager gridLayoutManager1 = new LinearLayoutManager(CalenderWiseActivity.this);

                                       recyclerView.setLayoutManager(gridLayoutManager1);
                                       UpcomingAdapter gridProductAdapter = new UpcomingAdapter(vactionmodelss, CalenderWiseActivity.this);
                                       recyclerView.setAdapter(gridProductAdapter);
                                       gridProductAdapter.notifyDataSetChanged();
                                       progressBar.setVisibility(View.GONE);
                                   }else {
                                       Toast.makeText(CalenderWiseActivity.this, "No Upcoming Order", Toast.LENGTH_SHORT).show();
                                   }





                                }
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CalenderWiseActivity.this, "Empty"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            // imageView.setVisibility(View.VISIBLE);
                            //text.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);


                            progressBar.setVisibility(View.GONE);

                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CalenderWiseActivity.this, "Server Not Responding"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CalenderWiseActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
