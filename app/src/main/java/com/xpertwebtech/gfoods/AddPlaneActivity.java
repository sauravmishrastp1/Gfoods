package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import adapterclass.CityAdapter;
import adapterclass.ViewBillAdapter;
import adapterclass.WeekDaysAdapter;
import modelclass.ViewBillModel;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class AddPlaneActivity extends AppCompatActivity {
private RelativeLayout backpress;
private TextView minuss,pluss;
private TextView counttxt ;
static String sdate="na",edate="na";
private int mYear, mMonth, mDay, mHour, mMinute;
private RelativeLayout startdate,emddate;
private TextView startdatetxt,enddatetxt;
private Button orderplace;
private int count;
private TextView productname,price,quant;
private ImageView productimg;
private Bundle bundle;
private String pricee;
private int pricceeeel;
private int totalprice;
private TextView totalpricetxt;
private RecyclerView weeklyrecyclerView;
private ArrayList<modelclass.Daysmodelcalss>daysmodelcalssArrayList = new ArrayList<>();
private View multipledayslayout;
private String pid ,id;
private View daly,selectdat,alternateda;
private String days="null";
private String quanttt,plantype,type;
private ImageView dalychcekbox,altercheckbox,selectcheckbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plane);
        minuss = findViewById(R.id.minus);
        pluss = findViewById(R.id.pluss);
        dalychcekbox = findViewById(R.id.dalycheck);
        altercheckbox = findViewById(R.id.altercheck);
        selectcheckbox = findViewById(R.id.selectcheck);
        counttxt = findViewById(R.id.count);
        totalpricetxt = findViewById(R.id.TOTALmrp);
        startdate = findViewById(R.id.startdate);
        startdatetxt = findViewById(R.id.startdatetxt);
        daly = findViewById(R.id.dalyklayout);
        selectdat = findViewById(R.id.selectdateview);
        alternateda = findViewById(R.id.alternatedays);
        enddatetxt = findViewById(R.id.enddatetxt);
        emddate = findViewById(R.id.enddate);
        multipledayslayout = findViewById(R.id.multipledays);
        orderplace = findViewById(R.id.oredrbtn);
        productname = findViewById(R.id.productname);
        productimg = findViewById(R.id.productimg);
        price = findViewById(R.id.mrp);
        weeklyrecyclerView = findViewById(R.id.wekdaysrecyclerview);
        quant = findViewById(R.id.productquantity);

          bundle = getIntent().getExtras();
         try {


             if (bundle != null) {
                 type = bundle.getString("type");
                 sdate = bundle.getString("startdate");
                 edate = bundle.getString("enddate");
                 String name = bundle.getString("name");
                 pricee = bundle.getString("price");
                 pid =bundle.getString("pid");
                 id = bundle.getString("id");
                 String img = bundle.getString("img");
                 quanttt = bundle.getString("qunat");
                 productname.setText(name);
                 pricceeeel = Integer.parseInt(pricee);
                 price.setText(String.valueOf("\u20B9" + pricceeeel));
                 quant.setText("1ltr");

                 if(type.equals("myplan")){
                     startdatetxt.setText(sdate);
                     enddatetxt.setText(edate);
                 }
                 if (quanttt.equals("0")) {
                     totalpricetxt.setText(String.valueOf("\u20B9" + "00"));
                     totalprice = 00;
                     count = 0;
                     counttxt.setText(String.valueOf(count));
                     orderplace.setText("Order Place");
                     days = plantype;


                 } else {
                     totalpricetxt.setText(String.valueOf("\u20B9" + pricee));
                     totalprice = Integer.parseInt(pricee);
                     count = Integer.parseInt(quanttt);
                     counttxt.setText(String.valueOf(count));
                     orderplace.setText("Update");

                 }

                 plantype = bundle.getString("plantye");
                 if (plantype.equals("daly")) {
                    // Toast.makeText(this, "planttype=>" + plantype, Toast.LENGTH_SHORT).show();
                     dalychcekbox.setVisibility(View.VISIBLE);
                     altercheckbox.setVisibility(View.GONE);
                     selectcheckbox.setVisibility(View.GONE);

                 } else if (plantype.equals("alternate")) {
                     dalychcekbox.setVisibility(View.GONE);
                     altercheckbox.setVisibility(View.VISIBLE);
                     selectcheckbox.setVisibility(View.GONE);

                 } else if (plantype.equals("null")) {
                     dalychcekbox.setVisibility(View.GONE);
                     altercheckbox.setVisibility(View.GONE);
                     selectcheckbox.setVisibility(View.GONE);
                     multipledayslayout.setVisibility(View.GONE);

                 } else {
                     dalychcekbox.setVisibility(View.GONE);
                     altercheckbox.setVisibility(View.GONE);
                     selectcheckbox.setVisibility(View.VISIBLE);
                     multipledayslayout.setVisibility(View.VISIBLE);
                     getdays();

                 }
             }
         }catch (Exception e){
             Toast.makeText(this, "Some data is missing", Toast.LENGTH_SHORT).show();
         }
        Toast.makeText(this, "id=>"+pid+id, Toast.LENGTH_SHORT).show();
        minuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0){
                    minuss.setLinksClickable(false);
                    count = 0;
                    counttxt.setText(String.valueOf(count));

                }else if (count>0){
                    minuss.setLinksClickable(true);
                    count =count-1;
                    counttxt.setText(String.valueOf(count));
                    totalprice = pricceeeel*count;
                    totalpricetxt.setText(String.valueOf("\u20B9"+totalprice));
                   // Toast.makeText(AddPlaneActivity.this, "price="+totalprice, Toast.LENGTH_SHORT).show();
                   // Toast.makeText(AddPlaneActivity.this, "add item=" + count, Toast.LENGTH_SHORT).show();
                }
            }
        });
        pluss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count+1;
                counttxt.setText(String.valueOf(count));
                totalprice = pricceeeel*count;
                totalpricetxt.setText(String.valueOf("\u20B9"+totalprice));
              //  Toast.makeText(AddPlaneActivity.this, "price="+totalprice, Toast.LENGTH_SHORT).show();
               // Toast.makeText(AddPlaneActivity.this, "add item="+count, Toast.LENGTH_SHORT).show();


            }
        });

        backpress = findViewById(R.id.backpresslayout);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlaneActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPlaneActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                sdate = dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
                                startdatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                // Toast.makeText(PaymentDeatilsFormActivity.this, "date="+date, LENGTH_SHORT).show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();



            }
        });

        emddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPlaneActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                edate = dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
                                enddatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                // Toast.makeText(PaymentDeatilsFormActivity.this, "date="+date, LENGTH_SHORT).show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        alternateda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dalychcekbox.setVisibility(View.GONE);
                altercheckbox.setVisibility(View.VISIBLE);
                selectcheckbox.setVisibility(View.GONE);
                days="alternate";
                multipledayslayout.setVisibility(View.GONE);

               // Toast.makeText(AddPlaneActivity.this, "Days=>"+"Alternate", Toast.LENGTH_SHORT).show();


            }
        });
        daly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dalychcekbox.setVisibility(View.VISIBLE);
                altercheckbox.setVisibility(View.GONE);
                selectcheckbox.setVisibility(View.GONE);
                days="daly";
                multipledayslayout.setVisibility(View.GONE);
               // Toast.makeText(AddPlaneActivity.this, "Days=>"+"Daly", Toast.LENGTH_SHORT).show();

            }
        });
        selectdat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dalychcekbox.setVisibility(View.GONE);
                altercheckbox.setVisibility(View.GONE);
                selectcheckbox.setVisibility(View.VISIBLE);
                multipledayslayout.setVisibility(View.VISIBLE);
                getdays();

            }
        });

        orderplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        for(int i=0;i<WeekDaysAdapter.newlistday.size();i++)
                        {

                            if (i==0)
                            {
                                days=WeekDaysAdapter.newlistday.get(i).getDays();
                            }
                            else
                            {
                                days=days+","+WeekDaysAdapter.newlistday.get(i).getDays();
                            }
                            Toast.makeText(AddPlaneActivity.this, "days=>"+days, Toast.LENGTH_SHORT).show();
                        }
                        if(quanttt.equals("0")){
                            orderplace();
                            orderplace.setText("Order Place");
                        }else {
                            updatenow();
                            orderplace.setText("Update");

                        }

            }
        });

    }
    private void getdays(){
        daysmodelcalssArrayList.add(new modelclass.Daysmodelcalss("Mon",false));
        daysmodelcalssArrayList.add(new modelclass.Daysmodelcalss("Tue",false));
        daysmodelcalssArrayList.add(new modelclass.Daysmodelcalss("Wed",false));
        daysmodelcalssArrayList.add(new modelclass.Daysmodelcalss("Thr",false));
        daysmodelcalssArrayList.add(new modelclass.Daysmodelcalss("Fri",false));
        daysmodelcalssArrayList.add(new modelclass.Daysmodelcalss("Sat",false));
        daysmodelcalssArrayList.add(new modelclass.Daysmodelcalss("Sun",false));
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        weeklyrecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        WeekDaysAdapter gridProductAdapter = new WeekDaysAdapter(daysmodelcalssArrayList,AddPlaneActivity.this);
        weeklyrecyclerView.setAdapter(gridProductAdapter);
        gridProductAdapter.notifyDataSetChanged();



    }
    private void orderplace()

    {
       // Toast.makeText(this, "days==>"+days, Toast.LENGTH_SHORT).show();
        if(days.equals("null")){
            Toast.makeText(this, "Please select delivery day", Toast.LENGTH_SHORT).show();
        }else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Order Place.....");
            progressDialog.setMessage("Please wait......");
            progressDialog.show();
            String ug = "http://lsne.in/gfood/api/bill-submit";
            //  String url ="http://lsne.in/gfood/api/bill-submit?product_id="+id+"&invoice_no=123456&time="+time+"&date="+date+"&price="+String.valueOf(totalprice)+"&order_status=1&user_id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ug,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject obj = new JSONObject(response);
                                String status = obj.getString("state");
                                JSONArray state = obj.getJSONArray("vacation");

                                if (status.equals("200")) {
                                    Toast.makeText(AddPlaneActivity.this, "Order Place Succesfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddPlaneActivity.this, MonthlyWiseBillActivity.class);
                                    startActivity(intent);
                                    progressDialog.dismiss();
                                } else {

                                    Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Intent intent = new Intent(AddPlaneActivity.this, MonthlyWiseBillActivity.class);
                                startActivity(intent);
                                progressDialog.dismiss();

                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddPlaneActivity.this, "Server Not Responding" + error, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("product_id", id);
                    params.put("invoice_no", "41543");
                    params.put("order_status", "1");
                    params.put("start_date", sdate);
                    params.put("end_date", edate);
                    params.put("plan_type", days);
                    params.put("qunatity", String.valueOf(count));
                    params.put("price", String.valueOf(totalprice));
                    params.put("user_id", SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
                    return params;
                }


            };
            VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
    private void updatenow()

    {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update.....");
        progressDialog.setMessage("Please wait......");
        progressDialog.show();
        String ug ="http://lsne.in/gfood/api/update-user-order-details";
        //  String url ="http://lsne.in/gfood/api/bill-submit?product_id="+id+"&invoice_no=123456&time="+time+"&date="+date+"&price="+String.valueOf(totalprice)+"&order_status=1&user_id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ug,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("state");
                            JSONArray state = obj.getJSONArray("vacation");

                            if (status.equals("200")) {
                                Toast.makeText(AddPlaneActivity.this, "Update Succesfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddPlaneActivity.this,MonthlyWiseBillActivity.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddPlaneActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddPlaneActivity.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("product_id",pid);
                params.put("id",id);
                params.put("invoice_no", "41543");
                params.put("order_status", "1");
                params.put("start_date", sdate);
                params.put("end_date",edate);
                params.put("plan_type", days);
                params.put("qunatity", String.valueOf(count));
                params.put("price", String.valueOf(totalprice));
                params.put("user_id", SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
                return params;
            }


        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    }

