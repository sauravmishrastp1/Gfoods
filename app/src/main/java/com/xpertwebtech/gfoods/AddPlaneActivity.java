package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import adapterclass.WeekDaysAdapter;
import utils.SharedPrefManager;
import utils.VolleySingleton;
public class AddPlaneActivity extends AppCompatActivity {
    private RelativeLayout backpress;
    private TextView minuss, pluss;
    private TextView counttxt;
    static String sdate = "null", edate = "null";
    private int mYear, mMonth, mDay, mHour, mMinute;
    private RelativeLayout startdate, emddate;
    private TextView startdatetxt, enddatetxt;
    private Button orderplace;
    public static int count = 0;
    private TextView productname, price, quant;
    private ImageView productimg;
    private Bundle bundle;
    private String pricee;
    private int pricceeeel;
    public static int totalprice;
    private TextView totalpricetxt;
    private RecyclerView weeklyrecyclerView;
    private ArrayList<modelclass.Daysmodelcalss> daysmodelcalssArrayList = new ArrayList<>();
    private View multipledayslayout;
    public static String pid, id;
    private View daly, selectdat, alternateda;
    public static String days = "null";
    public static String quanttt = "0", plantype = "null", type, volume;
    private ImageView dalychcekbox, altercheckbox, selectcheckbox;
    private int addmoney;
    private int money;

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
                volume = bundle.getString("volume");
                String name = bundle.getString("name");
                pricee = bundle.getString("price");
                pid = bundle.getString("pid");
                id = bundle.getString("id");
                String img = bundle.getString("img");
                quanttt = bundle.getString("qunat");
                productname.setText(name);
                pricceeeel = Integer.parseInt(pricee);
                price.setText(String.valueOf("\u20B9" + pricceeeel));
                quant.setText(volume);
                Picasso.get().load(img).into(productimg);
              //  Toast.makeText(this, "pid=>" + pid, Toast.LENGTH_SHORT).show();
                if (type.equals("myplan")) {
                    startdatetxt.setText(sdate);
                    if (edate.equals("null")){
                        enddatetxt.setText("optinal");
                }else {
                    enddatetxt.setText(edate);
                }
                }
                if (quanttt.equals("0")) {
                    totalpricetxt.setText(String.valueOf("\u20B9" + "00"));
                  //  Toast.makeText(this, "str=>" + sdate, Toast.LENGTH_SHORT).show();
                    totalprice = 00;
                    count = 0;
                    counttxt.setText(String.valueOf(count));
                    orderplace.setText("Order Place");
                    days = plantype;
                    if(sdate.equals("null")){
                        startdatetxt.setText("select date");
                        enddatetxt.setText("optinal");
                    }else {
                        startdatetxt.setText(sdate);
                        enddatetxt.setText("optinal");
                    }




                } else {
                    totalpricetxt.setText(String.valueOf("\u20B9" + pricee));
                    totalprice = Integer.parseInt(pricee);
                    count = Integer.parseInt(quanttt);
                    counttxt.setText(String.valueOf(count));
                    orderplace.setText("Update");
                    quant.setText(quanttt + "lt");

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
        } catch (Exception e) {
            Toast.makeText(this, "Some data is missing", Toast.LENGTH_SHORT).show();
        }
        // Toast.makeText(this, "id=>"+pid+id, Toast.LENGTH_SHORT).show();
        minuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    minuss.setLinksClickable(false);
                    count = 0;
                    counttxt.setText(String.valueOf(count));

                } else if (count > 0) {
                    minuss.setLinksClickable(true);
                    count = count - 1;
                    counttxt.setText(String.valueOf(count));
                    totalprice = pricceeeel * count;
                    totalpricetxt.setText(String.valueOf("\u20B9" + totalprice));
                    // Toast.makeText(AddPlaneActivity.this, "price="+totalprice, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(AddPlaneActivity.this, "add item=" + count, Toast.LENGTH_SHORT).show();
                }
            }
        });
        pluss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count + 1;
                counttxt.setText(String.valueOf(count));
                totalprice = pricceeeel * count;
                totalpricetxt.setText(String.valueOf("\u20B9" + totalprice));
                //  Toast.makeText(AddPlaneActivity.this, "price="+totalprice, Toast.LENGTH_SHORT).show();
                // Toast.makeText(AddPlaneActivity.this, "add item="+count, Toast.LENGTH_SHORT).show();


            }
        });





        backpress = findViewById(R.id.backpresslayout);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlaneActivity.this, MainActivity.class);
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


                                sdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                startdatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                                startdatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                // Toast.makeText(PaymentDeatilsFormActivity.this, "date="+date, LENGTH_SHORT).show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });
        //  Toast.makeText(this, "startdate=>" + sdate, Toast.LENGTH_SHORT).show();
        emddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quanttt.equals("0")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(AddPlaneActivity.this);
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


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddPlaneActivity.this,
                                            new DatePickerDialog.OnDateSetListener() {

                                                @Override
                                                public void onDateSet(DatePicker view, int year,
                                                                      int monthOfYear, int dayOfMonth) {

                                                    String enddate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                                    SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
                                                    try {
                                                        if (dfDate.parse(sdate).before(dfDate.parse(enddate))) {
                                                            edate = enddate;
                                                            enddatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                                        } else if (dfDate.parse(sdate).equals(dfDate.parse(enddate))) {
                                                            Toast.makeText(AddPlaneActivity.this, "start date and end date must not be equal", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(AddPlaneActivity.this, "end date is not small than start date", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }


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
                                    edate = "null";
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(AddPlaneActivity.this);
                    builder1.setMessage("Update Date?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    final Calendar c = Calendar.getInstance();
                                    mYear = c.get(Calendar.YEAR);
                                    mMonth = c.get(Calendar.MONTH);
                                    mDay = c.get(Calendar.DAY_OF_MONTH);


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddPlaneActivity.this,
                                            new DatePickerDialog.OnDateSetListener() {

                                                @Override
                                                public void onDateSet(DatePicker view, int year,
                                                                      int monthOfYear, int dayOfMonth) {


                                                    String enddate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                                    SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");

                                                    try {
                                                        if (dfDate.parse(sdate).before(dfDate.parse(enddate))) {
                                                            edate = enddate;
                                                            enddatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                                        } else if (dfDate.parse(sdate).equals(dfDate.parse(enddate))) {
                                                            Toast.makeText(AddPlaneActivity.this, "start date and end date must not be equal", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(AddPlaneActivity.this, "end date is not small than start date", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }


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
                                    edate = "null";
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

                //    Toast.makeText(AddPlaneActivity.this, "enddate=>"+edate , Toast.LENGTH_SHORT).show();

            }
        });
        alternateda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dalychcekbox.setVisibility(View.GONE);
                altercheckbox.setVisibility(View.VISIBLE);
                selectcheckbox.setVisibility(View.GONE);
                days = "alternate";
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
                days = "daly";
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
        for (int i = 0; i < WeekDaysAdapter.newlistday.size(); i++) {

            if (i == 0) {
                days = WeekDaysAdapter.newlistday.get(i).getDays();
            } else {

                days = days + "," + WeekDaysAdapter.newlistday.get(i).getDays();
            }
            //  Toast.makeText(AddPlaneActivity.this, "days=>"+days, Toast.LENGTH_SHORT).show();
        }


            orderplace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     if(MainActivity.money.equals("0")){
                         if(count==0){
                             missaddquant();
                             // orderplace.setBackgroundResource(R.drawable.disablegradient);
                         }
                         if (days.equals("null")) {
                             plannull();
                             //orderplace.setBackgroundResource(R.drawable.disablegradient);
                         } else if (sdate.equals("null")) {
                             startdatenull();
                             //orderplace.setBackgroundResource(R.drawable.disablegradient);
                         } else {
                             Intent intent = new Intent(AddPlaneActivity.this, MyWalletActivity.class);
                             intent.putExtra("money",String.valueOf(totalprice));
                             startActivity(intent);
                         }
                     }else {
                         if (quanttt.equals("0")) {
                             orderplace();
                             orderplace.setText("Order Place");
                         } else {
                             updatenow();
                             orderplace.setText("Update");

                         }
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
         money = Integer.valueOf(MainActivity.money)-totalprice;
    }
    private void orderplace() {

       if(count==0){
          missaddquant();
         // orderplace.setBackgroundResource(R.drawable.disablegradient);
       }
        if (days.equals("null")) {
           plannull();
            //orderplace.setBackgroundResource(R.drawable.disablegradient);
        } else if (sdate.equals("null")) {
          startdatenull();
            //orderplace.setBackgroundResource(R.drawable.disablegradient);
        } else {

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
                                    Intent intent = new Intent(AddPlaneActivity.this, OrderHitoryActivity .class);
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
                    params.put("product_id", pid);
                    params.put("invoice_no", "41543");
                    params.put("order_status", "1");
                    params.put("start_date", sdate);
                    params.put("end_date", edate);
                    params.put("unit_price",String.valueOf(pricceeeel));
                   // params.put("credit_use","");
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
            String ug = "http://lsne.in/gfood/api/update-user-order-details";
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
                                    Intent intent = new Intent(AddPlaneActivity.this, MonthlyWiseBillActivity.class);
                                    startActivity(intent);
                                    progressDialog.dismiss();
                                } else {

                                    Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(AddPlaneActivity.this, "somrthing went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    params.put("product_id", pid);
                    params.put("id", id);
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
    private void missaddquant(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddPlaneActivity.this);
        builder1.setMessage("Please Select Quantity!");
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void plannull(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddPlaneActivity.this);
        builder1.setMessage("Please Select Delivery Day!");
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    private void startdatenull(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddPlaneActivity.this);
        builder1.setMessage("Please Select Duration date!");
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    private void onpaymnetcomplete()


    {
        addmoney = Integer.valueOf(MainActivity.money)-totalprice;
        String url ="http://lsne.in/gfood/api/user-wallet-update?user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()+"&money="+String.valueOf(addmoney);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray wallet = obj.getJSONArray("wallet");
                            if (status.equals("200")) {
                                Toast.makeText(AddPlaneActivity.this, "hii", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddPlaneActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            // progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddPlaneActivity.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}

