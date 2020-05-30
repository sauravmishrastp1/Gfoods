package com.xpertwebtech.gfoods;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.applandeo.materialcalendarview.EventDay;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import adapterclass.MyAdapter;
import adapterclass.MyPlanAdapter;
import adapterclass.MyProductcategory;
import fragment.SideMenuFragment;
import modelclass.EventCalender;
import modelclass.EventModel;
import modelclass.MyPlanModelClass;
import modelclass.MyPlanProductCat;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView walletamount, mycleandertextview, myplanetextview, heading;
    private View mycleanderview, myplaneview, cleanderview;
    private View walletlayout;
    private ArrayList<EventModel> eventModels = new ArrayList<>();
    private RecyclerView productcategory;
    private ArrayList<MyPlanModelClass> myPlanModelClasses = new ArrayList<>();
    private ArrayList<MyPlanProductCat> myPlanProductCats = new ArrayList<>();
    private SlidingMenu menu;
    private CalendarView calendarView;
    private ImageView sidemenu;
    private ImageView btnnext, btnprevious;
    private TextView cruntmonth;
    private String catId;
    private int color;
    private ProgressBar progressBar;
    private static final String TAG = "MainActivity";
    private CompactCalendarView compactCalendarView;
    private List<CalendarDay> calevents = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private HashMap<Integer, List<Event>> map = new HashMap<>();
    private ListView listView;
    private MyAdapter adapter;
    private ArrayList<EventCalender> eventsmodell = new ArrayList<>();
    private Calendar cal;
    List<String> mutableBookings = new ArrayList<>();
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private boolean shouldShow = false;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private ImageView previousmonth, nextmonth;
    private int day[];
    private boolean cxx;


    @SuppressLint({"NewApi", "ResourceAsColor"})
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sidemenu = findViewById(R.id.menu);
        walletlayout = findViewById(R.id.walletlayout);
        walletamount = findViewById(R.id.walletamounttxt);
        recyclerView = findViewById(R.id.recyclerviewproducts);
        cleanderview = findViewById(R.id.cleanderlayoutt);
        calendarView = findViewById(R.id.calendarView);
        heading = findViewById(R.id.heading);
        progressBar = findViewById(R.id.progressbarr);
        btnnext = findViewById(R.id.monthforward);
        btnprevious = findViewById(R.id.previousmonth);
        previousmonth = findViewById(R.id.previouddate);
        nextmonth = findViewById(R.id.nextdate);
        cruntmonth = findViewById(R.id.month);
        productcategory = findViewById(R.id.recycleprductscategory);
        //ListView bookingsListView = findViewById(R.id.listview);

        compactCalendarView = findViewById(R.id.compactcalendar_view);
//        final ArrayAdapter adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, mutableBookings);
//        bookingsListView.setAdapter(adapter);


        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.displayOtherMonthDays(false);
        //compactCalendarView.setIsRtl(true);

        compactCalendarView.invalidate();

        logEventsByMonth(compactCalendarView);


        previousmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollLeft();
            }
        });

        nextmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollRight();
            }
        });

        // calendarView.setOnMonthChangedListener(this);

        //makeJsonObjectRequest();

        Boolean islogin = SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn();
        if (!islogin) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
//       String userid = SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
//        Toast.makeText(this, "userid=>"+userid, Toast.LENGTH_SHORT).show();

        loadevent();
        loaddelevedevent();
        vacationevent();
        previousmonth.setVisibility(View.VISIBLE);
        nextmonth.setVisibility(View.VISIBLE);
        heading.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        logEventsByMonth(compactCalendarView);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                heading.setText(dateFormatForMonth.format(dateClicked));
                //Toast.makeText(MainActivity.this, "=>"+dateFormatForMonth, Toast.LENGTH_SHORT).show();
               List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                   for(int i =0;i<bookingsFromMap.size();i++){
                       Object objects = bookingsFromMap.get(i);
                       int data = (int) ((Event) objects).getColor();
                       String date =dateFormat.format(dateClicked);
                      // Toast.makeText(MainActivity.this, ""+date, Toast.LENGTH_SHORT).show();
                       if(data==Color.parseColor("#4fC3F7")){
                        Intent intent = new Intent(MainActivity.this,CalenderWiseActivity.class);
                        intent.putExtra("date",date);
                        startActivity(intent);
                       }if(data==Color.parseColor("#AEEA00")){
                           Intent intent = new Intent(MainActivity.this,ViewBillActivity.class);
                           intent.putExtra("date",date);
                           startActivity(intent);
                       }if(data==Color.parseColor("#F57F17")){
                           Intent intent = new Intent(MainActivity.this,ViewVacationActivtiy.class);
                           startActivity(intent);
                       }
                   }
                heading.setText(dateFormatForMonth.format(dateClicked));

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                heading.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });



        walletamount.setText("\u20B9" + "50");
        mycleandertextview = findViewById(R.id.mycleandertext);
        myplanetextview = findViewById(R.id.myplanetext);
        mycleanderview = findViewById(R.id.mycleander);
        myplaneview = findViewById(R.id.myolane);
        mycleanderview.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                loadevent();
                loaddelevedevent();
                vacationevent();
                mycleanderview.setBackgroundResource(R.drawable.bakgroud);
                myplaneview.setBackgroundResource(R.drawable.border1);
                mycleandertextview.setTextColor(R.color.colorPrimaryDark);
                myplanetextview.setTextColor(Color.WHITE);
                cleanderview.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                productcategory.setVisibility(View.GONE);
                previousmonth.setVisibility(View.VISIBLE);
                nextmonth.setVisibility(View.VISIBLE);
                heading.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

            }
        });


        walletlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyWalletActivity.class);
                startActivity(intent);
            }
        });
        myplaneview.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                heading.setText("Looking For?");
                previousmonth.setVisibility(View.GONE);
                nextmonth.setVisibility(View.GONE);
                myplaneview.setBackgroundResource(R.drawable.bakgroud);
                mycleanderview.setBackgroundResource(R.drawable.border1);
                myplanetextview.setTextColor(R.color.colorPrimaryDark);
                mycleandertextview.setTextColor(Color.WHITE);
                cleanderview.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                productcategory.setVisibility(View.GONE);
                myPlanProductCats.clear();
                myPlanModelClasses.clear();
                previousmonth.setVisibility(View.GONE);
                nextmonth.setVisibility(View.GONE);
                getmyprlanproduct();


            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String date = String.valueOf(dayOfMonth);
                String monthh = String.valueOf(month);
                String yearr = String.valueOf(year);
                Intent intent = new Intent(MainActivity.this, MonthlyWiseBillActivity.class);
                intent.putExtra("date", date + "" + monthh + "" + yearr);
                startActivity(intent);

            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String catname = bundle.getString("productname");
            String type = bundle.getString("type");
            color = bundle.getInt("color");
            catId = bundle.getString("id");
            if (type.equals("1")) {
                myplaneview.setBackgroundResource(R.drawable.bakgroud);
                mycleanderview.setBackgroundResource(R.drawable.border1);
                myplanetextview.setTextColor(R.color.colorPrimaryDark);
                mycleandertextview.setTextColor(Color.WHITE);
                productcategory.setVisibility(View.VISIBLE);
                previousmonth.setVisibility(View.GONE);
                nextmonth.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                myPlanProductCats.clear();
                myPlanModelClasses.clear();
                cleanderview.setVisibility(View.GONE);
                getcategory();

                heading.setText(catname);
            }

        } else {
            loadevent();
            loaddelevedevent();
            vacationevent();
            previousmonth.setVisibility(View.VISIBLE);
            nextmonth.setVisibility(View.VISIBLE);
            heading.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
            logEventsByMonth(compactCalendarView);
        }
        setSideBar();

        SideMenuFragment sideMenuFragment = new SideMenuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.side_menu_container, sideMenuFragment, "SideMenuFragment")
                .commit();

        sidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onSideMenu();
            }
        });

    }


    private void getmyprlanproduct() {
        if (myPlanModelClasses.size() > 0) {
            myPlanModelClasses.clear();
        }

        myPlanProductCats.clear();
        String url = "http://lsne.in/gfood/api/category";
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray category = obj.getJSONArray("category");
                            if (status.equals("200")) {
                                for (int i = 0; i < category.length(); i++) {
                                    JSONObject categoryJSONObject = category.getJSONObject(i);
                                    String Name = categoryJSONObject.getString("category");
                                    String id = categoryJSONObject.getString("id");


                                    myPlanModelClasses.add(new MyPlanModelClass(R.drawable.daryproduct, Name, R.color.dary, id));


                                }
                                GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                recyclerView.setLayoutManager(layoutManager);
                                layoutManager.setOrientation(RecyclerView.VERTICAL);
                                MyPlanAdapter gridProductAdapter = new MyPlanAdapter(myPlanModelClasses, MainActivity.this);
                                recyclerView.setAdapter(gridProductAdapter);
                                gridProductAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);


                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "somrthing went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Server Not Responding" + error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void getcategory() {
        myPlanProductCats.clear();
        progressBar.setVisibility(View.VISIBLE);
        String url = "http://lsne.in/gfood/api/product?category_id=" + catId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray category = obj.getJSONArray("Product");
                            if (status.equals("200")) {
                                for (int i = 0; i < category.length(); i++) {
                                    JSONObject categoryJSONObject = category.getJSONObject(i);
                                    String pruductnbame = categoryJSONObject.getString("product_name");
                                    String productimg = categoryJSONObject.getString("image");
                                    String quant = categoryJSONObject.getString("product_volume");
                                    String price = categoryJSONObject.getString("price");
                                    String id = categoryJSONObject.getString("id");

                                    myPlanProductCats.add(new MyPlanProductCat("http://lsne.in/gfood/upload/" + productimg, pruductnbame, quant, price, color, id));

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                    productcategory.setLayoutManager(layoutManager);
                                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                                    MyProductcategory gridProductAdapter = new MyProductcategory(myPlanProductCats, MainActivity.this);
                                    productcategory.setAdapter(gridProductAdapter);
                                    gridProductAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);

                                }

                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "somrthing went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Server Not Responding" + error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    public void onSideMenu() {
        menu.toggle();
    }

    private void setSideBar() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setFadeDegree(0.75f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.ly_frame_layout);
    }


    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        currentCalender.set(Calendar.MONTH, Calendar.AUGUST);
        List<String> dates = new ArrayList<>();
        for (Event e : compactCalendarView.getEventsForMonth(new Date())) {
            dates.add(dateFormatForDisplaying.format(e.getTimeInMillis()));
        }
        Log.d(TAG, "Events for Aug with simple date formatter: " + dates);
        Log.d(TAG, "Events for Aug month using default local and timezone: " + compactCalendarView.getEventsForMonth(currentCalender.getTime()));
    }


    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void loadevent() {
       //  Toast.makeText(this, "enter", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        String url = "http://lsne.in/gfood/api/upcoming-product-for-dileverd?user_id=" + SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray upcoming = obj.getJSONArray("up_coming");
                            if (status.equals("200")) {
                                for (int i = 0; i < upcoming.length(); i++) {
                                    JSONObject categoryJSONObject = upcoming.getJSONObject(i);
                                    String date = categoryJSONObject.getString("date");
                                    String input_date = date;
                                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
                                    Date dt1 = null;
                                    try {
                                        dt1 = format1.parse(input_date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    DateFormat format2 = new SimpleDateFormat("mm");
                                    DateFormat format = new SimpleDateFormat("yyyy");
                                    DateFormat format3 = new SimpleDateFormat("dd");

                                    int month = Integer.parseInt(format2.format(dt1));
                                    int year = Integer.parseInt(format.format(dt1));
                                    int day = Integer.parseInt(format3.format(dt1));
                                    //int a [] ={day};


                                    addEvents(month - 1, year, day - 1);

                                   // Toast.makeText(MainActivity.this, "day" + day + "month=>" + month + "yer=>" + year, Toast.LENGTH_SHORT).show();

                                    progressBar.setVisibility(View.GONE);
                                }


                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "somrthing went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Server Not Responding" + error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void loaddelevedevent() {
       // Toast.makeText(this, "enter", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        String url = "http://lsne.in/gfood/api/dileverd-product?user_id=" + SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray upcoming = obj.getJSONArray("up_coming");
                            if (status.equals("200")) {
                                for (int i = 0; i < upcoming.length(); i++) {
                                    JSONObject categoryJSONObject = upcoming.getJSONObject(i);
                                    String date = categoryJSONObject.getString("date");
                                    String input_date = date;
                                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
                                    Date dt1 = null;
                                    try {
                                        dt1 = format1.parse(input_date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    DateFormat format2 = new SimpleDateFormat("mm");
                                    DateFormat format = new SimpleDateFormat("yyyy");
                                    DateFormat format3 = new SimpleDateFormat("dd");

                                    int month = Integer.parseInt(format2.format(dt1));
                                    int year = Integer.parseInt(format.format(dt1));
                                    int day = Integer.parseInt(format3.format(dt1));
                                    //int a [] ={day};


                                    adddeleverd(month - 1, year, day - 1);

                                   // Toast.makeText(MainActivity.this, "day" + day + "month=>" + month + "yer=>" + year, Toast.LENGTH_SHORT).show();

                                    progressBar.setVisibility(View.GONE);
                                }


                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "somrthing went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Server Not Responding" + error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void vacationevent() {
        // Toast.makeText(this, "enter", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        String url = "http://lsne.in/gfood/api/vacation-data?user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray upcoming = obj.getJSONArray("vacation_data");
                            if (status.equals("200")) {
                                for (int i = 0; i < upcoming.length(); i++) {
                                    JSONObject categoryJSONObject = upcoming.getJSONObject(i);
                                    String date = categoryJSONObject.getString("start_date");
                                    String input_date = date;
                                    SimpleDateFormat format1 = new SimpleDateFormat("dd-mm-yyyy");
                                    Date dt1 = null;
                                    try {
                                        dt1 = format1.parse(input_date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    DateFormat format2 = new SimpleDateFormat("mm");
                                    DateFormat format = new SimpleDateFormat("yyyy");
                                    DateFormat format3 = new SimpleDateFormat("dd");

                                    int month = Integer.parseInt(format2.format(dt1));
                                    int year = Integer.parseInt(format.format(dt1));
                                    int day = Integer.parseInt(format3.format(dt1));
                                    //int a [] ={day};


                                    advacation(month - 1, year, day - 1);

                                    // Toast.makeText(MainActivity.this, "day" + day + "month=>" + month + "yer=>" + year, Toast.LENGTH_SHORT).show();

                                    progressBar.setVisibility(View.GONE);
                                }


                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "somrthing went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Server Not Responding" + error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void addEvents(int month, int year, int day) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        currentCalender.setTime(firstDayOfMonth);
        if (month > -1) {
            currentCalender.set(Calendar.MONTH, month);
        }
        if (year > -1) {
            currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
            currentCalender.set(Calendar.YEAR, year);
        }
        currentCalender.add(Calendar.DATE, day);
        setToMidnight(currentCalender);
        long timeInMillis = currentCalender.getTimeInMillis();

        List<Event> events = getEvents(timeInMillis, day);
        //Toast.makeText(this, "event=>" + events.get(0), Toast.LENGTH_SHORT).show();
        compactCalendarView.addEvents(events);
      // events.add(new Event(R.drawable.ic_call_to_action_black_24dp,timeInMillis));

    }
    private List<Event> getEvents(Long timeInMillis, int day) {
        //Toast.makeText(this, "enter", Toast.LENGTH_SHORT).show();

        return Arrays.asList(new Event(Color.parseColor("#4fC3F7"), timeInMillis, "upcoming" + new Date(timeInMillis)));

    }

    private void adddeleverd(int month, int year, int day) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        currentCalender.setTime(firstDayOfMonth);
        if (month > -1) {
            currentCalender.set(Calendar.MONTH, month);
        }
        if (year > -1) {
            currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
            currentCalender.set(Calendar.YEAR, year);
        }
        currentCalender.add(Calendar.DATE, day);
        setToMidnight(currentCalender);
        long timeInMillis = currentCalender.getTimeInMillis();

        List<Event> events = getdeliverd(timeInMillis, day);
        //Toast.makeText(this, "event=>" + events.get(0), Toast.LENGTH_SHORT).show();
        compactCalendarView.addEvents(events);
    }


    private List<Event> getdeliverd(Long timeInMillis, int day) {
        //Toast.makeText(this, "enter", Toast.LENGTH_SHORT).show();

        return Arrays.asList(new Event(Color.parseColor("#AEEA00"), timeInMillis, "dilivery" + new Date(timeInMillis)));

    }
    private void advacation(int month, int year, int day) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        currentCalender.setTime(firstDayOfMonth);
        if (month > -1) {
            currentCalender.set(Calendar.MONTH, month);
        }
        if (year > -1) {
            currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
            currentCalender.set(Calendar.YEAR, year);
        }
        currentCalender.add(Calendar.DATE, day);
        setToMidnight(currentCalender);
        long timeInMillis = currentCalender.getTimeInMillis();

        List<Event> events = getvacation(timeInMillis, day);
      //  Toast.makeText(this, "event=>" + events.get(0), Toast.LENGTH_SHORT).show();
        compactCalendarView.addEvents(events);
    }


    private List<Event> getvacation(Long timeInMillis, int day) {
        //Toast.makeText(this, "enter", Toast.LENGTH_SHORT).show();

        return Arrays.asList(new Event(Color.parseColor("#F57F17"), timeInMillis, "vacation" + new Date(timeInMillis)));

    }
}
