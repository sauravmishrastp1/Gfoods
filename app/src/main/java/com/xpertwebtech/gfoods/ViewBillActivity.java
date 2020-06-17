package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import adapterclass.MonthlyBillAdapter;
import modelclass.MonthlyBillModelclass;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class ViewBillActivity extends AppCompatActivity {
    private RelativeLayout backpress;
    private ArrayList<MonthlyBillModelclass>vactionmodels = new ArrayList<>();
    private TextView text;
    private ImageView previous,next;
    private TextView datetxt;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private  String formattedDatee;
    public static TextView textviewstatment,totalbillmonth;
    private View layouttt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        text = findViewById(R.id.texttttt);
        layouttt = findViewById(R.id.totalbill);
        previous = findViewById(R.id.previousmonth);
        textviewstatment = findViewById(R.id.textmonth);
        totalbillmonth = findViewById(R.id.totalbillintxt);
        next = findViewById(R.id.nextmonth);
        datetxt = findViewById(R.id.date);
        progressBar = findViewById(R.id.progressbarr);
        imageView = findViewById(R.id.referimg);
        recyclerView = findViewById(R.id.viewbillrecyclerview);
        backpress = findViewById(R.id.backpresslayout);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewBillActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



        final Calendar c = Calendar.getInstance();

        //System.out.println("Current time => " + c.getTime());

        final SimpleDateFormat df = new SimpleDateFormat("MMMyyyy");
        final String[] formattedDate = {df.format(c.getTime())};
        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
        final Calendar actualDate = Calendar.getInstance();
        formattedDatee=sdf.format(actualDate.getTime());
        getvactiondata();
        datetxt.setText(formattedDate[0]);
        textviewstatment.setText("Total bill amount of"+" "+formattedDate[0]);
       // totalbillmonth.setText("\u20B9" +MonthlyBillAdapter.totalPrice);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //layouttt.setVisibility(View.VISIBLE);
                actualDate.add(Calendar.MONTH,1);
                formattedDatee=sdf.format(actualDate.getTime());
                getvactiondata();
                c.add(Calendar.MONTH, 1);
               formattedDate[0] = df.format(c.getTime());
                datetxt.setText(formattedDate[0]);
                textviewstatment.setText("Total bill amount of"+" "+formattedDate[0]);
              //  totalbillmonth.setText("\u20B9" +MonthlyBillAdapter.totalPrice);


            }
        });
      previous.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              actualDate.add(Calendar.MONTH,-1);
              formattedDatee=sdf.format(actualDate.getTime());
              getvactiondata();
              c.add(Calendar.MONTH, -1);
              formattedDate[0] = df.format(c.getTime());
              datetxt.setText(formattedDate[0]);
              textviewstatment.setText("Total bill amount of"+" "+formattedDate[0]);
              //totalbillmonth.setText("\u20B9" +MonthlyBillAdapter.totalPrice);


          }
      });

    }


    private void getvactiondata(){
        //totalbillmonth.setText("\u20B9" +MonthlyBillAdapter.totalPrice);
        //Toast.makeText(this, "date=>"+formattedDatee, Toast.LENGTH_SHORT).show();
        vactionmodels.clear();
        layouttt.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        String url2="http://lsne.in/gfood/api/order-by-date?user_id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()+"&date="+formattedDatee;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray state = obj.getJSONArray("order_details");

                            if (status.equals("200")) {


                                for(int j=0;j<state.length();j++) {

                                    JSONObject stateJSONObject = state.getJSONObject(j);
                                    String dat1 = stateJSONObject.getString("date");
                                    String productid = stateJSONObject.getString("product_id");
                                    String orderstatus = stateJSONObject.getString("mark_dileverd");
                                    String quant = stateJSONObject.getString("qunatity");
                                    String id = stateJSONObject.getString("id");
                                    String pricee = stateJSONObject.getString("product_price");
                                    String invoice = stateJSONObject.getString("invoice_no");
                                    String volume = stateJSONObject.getString("product_volume");
                                    String productname = stateJSONObject.getString("product_name");





                                    vactionmodels.add(new MonthlyBillModelclass(dat1,productname,pricee,quant,id,productid,volume));

                                    LinearLayoutManager gridLayoutManager1 = new LinearLayoutManager(ViewBillActivity.this);

                                    recyclerView.setLayoutManager(gridLayoutManager1);
                                    MonthlyBillAdapter gridProductAdapter = new MonthlyBillAdapter(vactionmodels, ViewBillActivity.this);
                                    recyclerView.setAdapter(gridProductAdapter);
                                    gridProductAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    layouttt.setVisibility(View.VISIBLE);
                                    imageView.setVisibility(View.GONE);
                                    text.setVisibility(View.GONE);
                                   // totalbillmonth.setText("\u20B9" +MonthlyBillAdapter.totalPrice);


                                }
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                layouttt.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ViewBillActivity.this, "No bill for this month", Toast.LENGTH_SHORT).show();
                            imageView.setVisibility(View.VISIBLE);
                            text.setVisibility(View.VISIBLE);
                            //recyclerView.setVisibility(View.GONE);
                            layouttt.setVisibility(View.GONE);

                            progressBar.setVisibility(View.GONE);

                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewBillActivity.this, "Server Not Responding"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        layouttt.setVisibility(View.GONE);

                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
}
