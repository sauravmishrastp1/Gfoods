package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.instamojo.android.Instamojo;
import com.instamojo.android.helpers.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapterclass.MyWalletLayout;
import modelclass.MyWalletModel;
import okhttp3.ResponseBody;
import retrofit.GatewayOrderStatus;
import retrofit.GetOrderIDRequest;
import retrofit.GetOrderIDResponse;
import retrofit.MyBackendService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class MyWalletActivity extends AppCompatActivity implements Instamojo.InstamojoPaymentCallback {
    private RecyclerView recyclerViewl;
    private ArrayList<MyWalletModel>myWalletModels=new ArrayList<>();
    private EditText entramount;
    private TextView blanceavalb;
    private Button addmonry;
    private RelativeLayout backpress;
    private TextView rssymbole;
    private MyBackendService myBackendService;
    private static final HashMap<Instamojo.Environment, String> env_options = new HashMap<>();

    static {
       //env_options.put(Instamojo.Environment.TEST, "https://test.instamojo.com/api/1.1/");
      env_options.put(Instamojo.Environment.PRODUCTION, "https://www.instamojo.com/@granofoods06");
    }
    private Instamojo.Environment mCurrentEnv = Instamojo.Environment.PRODUCTION;
    private boolean mCustomUIFlow = false;
    private String addmoney;

    private static final String TAG = MainActivity.class.getSimpleName();
    private AlertDialog dialog;
    private String money;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        recyclerViewl = findViewById(R.id.recyclerviewwallet);
        entramount = findViewById(R.id.enteramount);
        backpress = findViewById(R.id.backpresslayout);
        rssymbole = findViewById(R.id.rssymbole);
        rssymbole.setText("\u20B9");
        addmonry = findViewById(R.id.addmoneybtn);
        addmonry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmoney = entramount.getText().toString();
                createOrderOnServer();
            }
        });
        Bundle bundle = getIntent().getExtras();


        if(bundle != null)
        {
            String money = bundle.getString("money");
            entramount.setText(money);



        }else {
            entramount.setHint("Enter Amount");
        }
            showwallet();
        blanceavalb = findViewById(R.id.blance);

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWalletActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        statedata();
        Instamojo.getInstance().initialize(MyWalletActivity.this, mCurrentEnv);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sample-sdk-server.instamojo.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myBackendService = retrofit.create(MyBackendService.class);

    }



    private void statedata()
    {
        myWalletModels.add(new MyWalletModel("Pay"+" "+"\u20B9"+"50","Rs.25 Cashback"));
        myWalletModels.add(new MyWalletModel("Pay"+" "+"\u20B9"+"50","Rs.25 Cashback"));
        myWalletModels.add(new MyWalletModel("Pay"+" "+"\u20B9"+"50","Rs.25 Cashback"));
        myWalletModels.add(new MyWalletModel("Pay"+" "+"\u20B9"+"50","Rs.25 Cashback"));


        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(MyWalletActivity.this, 2);

        recyclerViewl.setLayoutManager(gridLayoutManager1);
        MyWalletLayout gridProductAdapter = new MyWalletLayout(myWalletModels, MyWalletActivity.this);
        recyclerViewl.setAdapter(gridProductAdapter);
        gridProductAdapter.notifyDataSetChanged();
    }

    private void createOrderOnServer() {
         progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait......");
        progressDialog.show();
        GetOrderIDRequest request = new GetOrderIDRequest();
        request.setEnv(mCurrentEnv.name());
        request.setBuyerName(SharedPrefManager.getInstance(getApplicationContext()).getUser().getName());
        request.setBuyerEmail(SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail());
        request.setBuyerPhone(SharedPrefManager.getInstance(getApplicationContext()).getUser().getPhone());
        request.setDescription("no dis");
        request.setAmount(addmoney);

        Call<GetOrderIDResponse> getOrderIDCall = myBackendService.createOrder(request);
        getOrderIDCall.enqueue(new retrofit2.Callback<GetOrderIDResponse>() {
            @Override
            public void onResponse(Call<GetOrderIDResponse> call, Response<GetOrderIDResponse> response) {
                if (response.isSuccessful()) {
                    String orderId = response.body().getOrderID();

                    if (!mCustomUIFlow) {
                        // Initiate the default SDK-provided payment activity
                        initiateSDKPayment(orderId);

                    } else {
                        // OR initiate a custom UI activity
                        //initiateCustomPayment(orderId);
                    }

                } else {
                    // Handle api errors
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d(TAG, "Error in response" + jObjError.toString());
                        progressDialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetOrderIDResponse> call, Throwable t) {
                // Handle call failure
                Log.d(TAG, "Failure");
                progressDialog.dismiss();

            }
        });
    }

    private void initiateSDKPayment(String orderID) {
        Instamojo.getInstance().initiatePayment(this, orderID, this);
    }

//    private void initiateCustomPayment(String orderID) {
//        Intent intent = new Intent(getBaseContext(), CustomUIActivity.class);
//        intent.putExtra(Constants.ORDER_ID, orderID);
//        startActivityForResult(intent, Constants.REQUEST_CODE);
//    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Will check for the transaction status of a particular Transaction
     *
     * @param transactionID Unique identifier of a transaction ID
     */
    private void checkPaymentStatus(final String transactionID, final String orderID) {
        if (transactionID == null && orderID == null) {
            return;
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

        showToast("Checking transaction status");
        Call<GatewayOrderStatus> getOrderStatusCall = myBackendService.orderStatus(mCurrentEnv.name().toLowerCase(),
                orderID, transactionID);
        getOrderStatusCall.enqueue(new retrofit2.Callback<GatewayOrderStatus>() {
            @Override
            public void onResponse(Call<GatewayOrderStatus> call, final Response<GatewayOrderStatus> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    GatewayOrderStatus orderStatus = response.body();
                    if (orderStatus.getStatus().equalsIgnoreCase("successful")) {
                        showToast("Transaction still pending");
                        return;
                    }

                    showToast("Transaction successful for id - " + orderStatus.getPaymentID());
                    refundTheAmount(transactionID, orderStatus.getAmount());
                    progressDialog.dismiss();


                } else {
                    showToast("Error occurred while fetching transaction status");
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<GatewayOrderStatus> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                            progressDialog.dismiss();

                        }
                        showToast("Failed to fetch the transaction status");

                        progressDialog.dismiss();

                    }
                });
            }
        });
    }

    /**
     * Will initiate a refund for a given transaction with given amount
     *
     * @param transactionID Unique identifier for the transaction
     * @param amount        amount to be refunded
     */
    private void refundTheAmount(String transactionID, String amount) {
        if (transactionID == null || amount == null) {
            return;
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

        showToast("Initiating a refund for - " + amount);
        Call<ResponseBody> refundCall = myBackendService.refundAmount(
                mCurrentEnv.name().toLowerCase(),
                transactionID, amount);
        refundCall.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    showToast("Refund initiated successfully");

                } else {
                    showToast("Failed to initiate a refund");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    progressDialog.dismiss();

                }

                showToast("Failed to Initiate a refund");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE && data != null) {
            String orderID = data.getStringExtra(Constants.ORDER_ID);
            String transactionID = data.getStringExtra(Constants.TRANSACTION_ID);
            String paymentID = data.getStringExtra(Constants.PAYMENT_ID);

            // Check transactionID, orderID, and orderID for null before using them to check the Payment status.
            if (transactionID != null || paymentID != null) {
                checkPaymentStatus(transactionID, orderID);
            } else {
                showToast("Oops!! Payment was cancelled");
                progressDialog.dismiss();
            }
        }
    }


    public void onInstamojoPaymentComplete(String orderID, String transactionID, String paymentID, String paymentStatus) {
        Log.d(TAG, "Payment complete");

        onpaymnetcomplete();
        progressDialog.dismiss();
        orderplace();

    }

    public void onPaymentCancelled() {
        Log.d(TAG, "Payment cancelled");
        showToast("Payment cancelled by user");
    }


    public void onInitiatePaymentFailure(String errorMessage) {
        Log.d(TAG, "Initiate payment failed");
        showToast("Initiating payment failed. Error: " + errorMessage);
    }

    private void onpaymnetcomplete()
    {

        String url ="http://lsne.in/gfood/api/user-wallet-update?user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()+"&money="+addmoney;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray wallet = obj.getJSONArray("wallet");
                            if (status.equals("200")) {
                                 entramount.getText().clear();
                                showwallet();
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyWalletActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            // progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyWalletActivity.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private  void showwallet(){
        String url ="http://lsne.in/gfood/api/view-wallet?user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray wallet = obj.getJSONArray("$data");
                            if (status.equals("200")) {
                                for(int i =0;i<wallet.length();i++){
                                    JSONObject jsonObject = wallet.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    money = jsonObject.getString("money");
                                    blanceavalb.setText("\u20B9"+money);

                                }

                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyWalletActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            // progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyWalletActivity.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void orderplace() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Order Place.....");
            progressDialog.setMessage("Please wait......");
            progressDialog.show();
            String ug = "http://lsne.in/gfood/api/bill-submit";
            //  String url ="http://lsne.in/gfood/api/bill-submit?product_id="+id+"&invoice_no=123456&time="+time+"&date="+date+"&price="+String.valueOf(totalprice)+"&order_status=1&user_id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ug,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject obj = new JSONObject(response);
                                String status = obj.getString("state");
                                JSONArray state = obj.getJSONArray("vacation");

                                if (status.equals("200")) {
                                    Toast.makeText(MyWalletActivity.this, "Order Place Succesfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MyWalletActivity.this, OrderHitoryActivity .class);
                                    startActivity(intent);
                                    progressDialog.dismiss();

                                } else {

                                    Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Intent intent = new Intent(MyWalletActivity.this, MonthlyWiseBillActivity.class);
                                startActivity(intent);
                                progressDialog.dismiss();

                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MyWalletActivity.this, "Server Not Responding" + error, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("product_id", AddPlaneActivity.pid);
                    params.put("invoice_no", "41543");
                    params.put("order_status", "1");
                    params.put("start_date", AddPlaneActivity.sdate);
                    params.put("end_date", AddPlaneActivity.edate);
                    params.put("plan_type", AddPlaneActivity.days);
                    params.put("qunatity", String.valueOf(AddPlaneActivity.count));
                    params.put("price", String.valueOf(AddPlaneActivity.totalprice));
                    params.put("user_id", SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
                    return params;
                }


            };
            VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }

}

