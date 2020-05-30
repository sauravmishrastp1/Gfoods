package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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

import java.util.Calendar;

import adapterclass.ViewVactopnAdapter;
import modelclass.Vactionmodel;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class EndVavationActivity extends AppCompatActivity {
    private TextView textView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private TextView start,endp;
    private TextView startdate,enddate;
    private LinearLayout startlayout,endlayout;
    private String date1,date2,vacationid;
    private String endtime="",stardate="";
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Bundle bundle;
    private Button updatevacation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_vavation);
        progressBar = findViewById(R.id.progressbarr);
        updatevacation = findViewById(R.id.addvacationNNN);
        endlayout = findViewById(R.id.endlayout);
        startlayout = findViewById(R.id.linearlayout);
        textView = findViewById(R.id.endvacation);
        toolbar = findViewById(R.id.toolbar);
        startdate = findViewById(R.id.startdateEE);
        enddate = findViewById(R.id.enddatee);


        bundle = getIntent().getExtras();
        if(bundle!=null){
            vacationid = bundle.getString("id");
            date1 = bundle.getString("start");
            date2 = bundle.getString("end");
            startdate.setText(date1);
            enddate.setText(date2);
            if(date2.equals(null)){
                enddate.setText("Optinal");
            }
            endtime=date2;
            stardate =date2;
        }

        toolbar.setTitle("Edit Vacation");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndVavationActivity.this,ViewVacationActivtiy.class);
                startActivity(intent);
            }
        });

        startlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EndVavationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                stardate = dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
                                startdate.setText(dayOfMonth + "-" + (monthOfYear+1) + "-" + year);

                                // Toast.makeText(PaymentDeatilsFormActivity.this, "date="+date, LENGTH_SHORT).show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();






            }
        });


        updatevacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update();

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String url ="http://lsne.in/gfood/api/delete-vacation?id="+vacationid;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    JSONObject obj = new JSONObject(response);
                                    String status = obj.getString("status");

                                    if (status.equals("201")) {


                                        Toast.makeText(EndVavationActivity.this, "Vacation Update Succsfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(EndVavationActivity.this,ViewVacationActivtiy.class);
                                        startActivity(intent);
                                        progressBar.setVisibility(View.GONE);
                                    } else {

                                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(EndVavationActivity.this,ViewVacationActivtiy.class);
                                        startActivity(intent);
                                        progressBar.setVisibility(View.GONE);
                                       // layout1.setVisibility(View.GONE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(EndVavationActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EndVavationActivity.this,ViewVacationActivtiy.class);
                                    startActivity(intent);
                                    progressBar.setVisibility(View.GONE);

                                }
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(EndVavationActivity.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                //layout1.setVisibility(View.GONE);
                            }
                        }) {

                };
                VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            }
        });

        endlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EndVavationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                endtime = dayOfMonth + "-" +(monthOfYear+1) + "-" + year;
                                enddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                // Toast.makeText(PaymentDeatilsFormActivity.this, "date="+date, LENGTH_SHORT).show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();



            }
        });
    }
    private void update(){
        //Toast.makeText(this, ""+time+date, Toast.LENGTH_SHORT).show();
       //Toast.makeText(this, ""+vacationid, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        String url ="http://lsne.in/gfood/api/update-vacation?id="+vacationid+"&user_id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()+"&start_date="+stardate+"&end_date="+endtime;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("state");
                            JSONArray state = obj.getJSONArray("vacation");

                            if (status.equals("200")) {


                                Toast.makeText(EndVavationActivity.this, "Update succesfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EndVavationActivity.this,ViewVacationActivtiy.class);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                               // layout1.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EndVavationActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                           // layout1.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EndVavationActivity.this, "Server Not Responding"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                       // layout1.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
