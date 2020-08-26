package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapterclass.ViewVactopnAdapter;
import modelclass.Vactionmodel;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class   ViewVacationActivtiy extends AppCompatActivity {
    private RelativeLayout backpress;
    private LinearLayout startlayout,endlayout;
    private TextView start,endp;
    static String time="",date="";
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView textView;
    private Button addvaction,addvactionre;
    private RecyclerView recyclerView;
    private View layout1,laou2;
    private ProgressBar progressBar;
    private ArrayList<Vactionmodel>vactionmodels = new ArrayList<>();
    private View nullvacationlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vacation_activtiy);
        backpress = findViewById(R.id.backpresslayout);
        progressBar = findViewById(R.id.progress_circular);
        nullvacationlayout = findViewById(R.id.nullvavationlayout);
        addvaction = findViewById(R.id.addvacationNNN);
        layout1 = findViewById(R.id.vacationlayout);
        addvactionre = findViewById(R.id.addvactionrecyclerview);
        laou2 = findViewById(R.id.recyclerview);
        endlayout = findViewById(R.id.endlayout);
        startlayout = findViewById(R.id.linearlayout);
        textView = findViewById(R.id.textview);
        start = findViewById(R.id.startdatee);
        recyclerView = findViewById(R.id.viewvacationrecyclerview);
        endp = findViewById(R.id.enddatee);
       vactionmodels.clear();
        getvactiondata();
        startlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                final Calendar c = Calendar.getInstance();
                                mYear = c.get(Calendar.YEAR);
                                mMonth = c.get(Calendar.MONTH);
                                mDay = c.get(Calendar.DAY_OF_MONTH);


                                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewVacationActivtiy.this,
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {


                                                date = dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
                                                start.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                                // Toast.makeText(PaymentDeatilsFormActivity.this, "date="+date, LENGTH_SHORT).show();
                                            }
                                        }, mYear, mMonth, mDay);
                                datePickerDialog.show();




            }
        });
        addvactionre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
                if(userid.equals("null")){
                    notlogin();
                }else {
                    layout1.setVisibility(View.VISIBLE);
                    addvactionre.setVisibility(View.GONE);
                    laou2.setVisibility(View.GONE);
                    nullvacationlayout.setVisibility(View.GONE);
                }
            }
        });

        addvaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getvacation();
                getvactiondata();
                layout1.setVisibility(View.GONE);
                addvactionre.setVisibility(View.VISIBLE);
                laou2.setVisibility(View.VISIBLE);
                nullvacationlayout.setVisibility(View.GONE);
            }
        });

        endlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewVacationActivtiy.this);
                builder1.setMessage("Choose Date?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final Calendar c = Calendar.getInstance();
                                mYear = c.get(Calendar.YEAR);
                                mMonth = c.get(Calendar.MONTH);
                                mDay = c.get(Calendar.DAY_OF_MONTH);


                                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewVacationActivtiy.this,
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {
                                                String enddate = dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
                                                SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
                                                try {
                                                    if(dfDate.parse(date).before(dfDate.parse(enddate)))
                                                    {
                                                        time = enddate;
                                                        endp.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                                    }
                                                    else if(dfDate.parse(date).equals(dfDate.parse(enddate)))
                                                    {
                                                        Toast.makeText(ViewVacationActivtiy.this, "start date and end date must not be equal", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(ViewVacationActivtiy.this, "end date is not small than start date", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }


                                                //time = dayOfMonth + "-" + (monthOfYear+1) + "-" + year;

                                                // Toast.makeText(PaymentDeatilsFormActivity.this, "date="+date, LENGTH_SHORT).show();
                                            }
                                        }, mYear, mMonth, mDay);
                                datePickerDialog.show();


                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                time="null";
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();





            }
        });
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewVacationActivtiy.this,MainActivity.class);
                startActivity(intent);
            }
        });




    }
    private void notlogin() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewVacationActivtiy.this);
        builder1.setMessage("You need to login !!");
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    private void getvacation(){
        //Toast.makeText(this, ""+time+date, Toast.LENGTH_SHORT).show();
       layout1.setVisibility(View.GONE);
       laou2.setVisibility(View.VISIBLE);
       progressBar.setVisibility(View.VISIBLE);
        String url ="http://xpertwebtech.in/gfood/api/vacation?user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()+
        "&start_date="+date+"&end_date="+time;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("state");
                            JSONArray state = obj.getJSONArray("vacation");

                            if (status.equals("200")) {


                                Toast.makeText(ViewVacationActivtiy.this, "Add succesfully", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                layout1.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ViewVacationActivtiy.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            layout1.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewVacationActivtiy.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        layout1.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void getvactiondata(){
        //Toast.makeText(this, ""+time+date, Toast.LENGTH_SHORT).show();
        vactionmodels.clear();
        layout1.setVisibility(View.GONE);
        laou2.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        String url ="http://xpertwebtech.in/gfood/api/vacation-data?user_id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray state = obj.getJSONArray("vacation_data");

                            if (status.equals("200")) {


                                for(int j=0;j<state.length();j++) {

                                    JSONObject stateJSONObject = state.getJSONObject(j);
                                    String dat1 = stateJSONObject.getString("start_date");
                                    String date2 = stateJSONObject.getString("end_date");
                                    String vacationid = stateJSONObject.getString("id");


                                    vactionmodels.add(new Vactionmodel(dat1,date2,vacationid));

                                    LinearLayoutManager gridLayoutManager1 = new LinearLayoutManager(ViewVacationActivtiy.this);

                                    recyclerView.setLayoutManager(gridLayoutManager1);
                                    ViewVactopnAdapter gridProductAdapter = new ViewVactopnAdapter(vactionmodels, ViewVacationActivtiy.this);
                                    recyclerView.setAdapter(gridProductAdapter);
                                    gridProductAdapter.notifyDataSetChanged();
                                    layout1.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                    laou2.setVisibility(View.VISIBLE);

                                }
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                layout1.setVisibility(View.GONE);
                                nullvacationlayout.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                          //  Toast.makeText(ViewVacationActivtiy.this, "No Vacation Yet!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            layout1.setVisibility(View.GONE);
                            nullvacationlayout.setVisibility(View.VISIBLE);
                            laou2.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewVacationActivtiy.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        layout1.setVisibility(View.GONE);
                        laou2.setVisibility(View.VISIBLE);
                        nullvacationlayout.setVisibility(View.VISIBLE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    }
