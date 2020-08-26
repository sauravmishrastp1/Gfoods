package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.paykun.sdk.PaykunApiCall;
import com.paykun.sdk.eventbus.Events;
import com.paykun.sdk.eventbus.GlobalBus;
import com.paykun.sdk.helper.PaykunHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapterclass.MyWalletLayout;
import constants.PaymentGateWayData;
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


public class MyWalletActivity extends AppCompatActivity {
    private RecyclerView recyclerViewl;
    private ArrayList<MyWalletModel>myWalletModels=new ArrayList<>();
    private EditText entramount;
    private TextView blanceavalb;
    private Button addmonry;
    private RelativeLayout backpress;
    private TextView rssymbole;
    private MyBackendService myBackendService;
    private String Testacceskey="35147FA826E0A0ABFB9A9D844CEF3E49";
    private String testmerchentid="255265780610472";
    private String TestacceskeyLIVE="6A75D3707CC25C07001C004D34286CD3";
    private String testmerchentidLIVE="837773351399967";

    private static final HashMap<Instamojo.Environment, String> env_options = new HashMap<>();

    static {
       env_options.put(Instamojo.Environment.TEST, "https://imjo.in/wA7nKr");
      env_options.put(Instamojo.Environment.PRODUCTION, "https://imjo.in/wA7nKr");
    }
    private Instamojo.Environment mCurrentEnv = (Instamojo.Environment.PRODUCTION);
    private boolean mCustomUIFlow = false;
    private String addmoney="0";

    private static final String TAG = MainActivity.class.getSimpleName();
    private AlertDialog dialog;
    private String money=null;
    private String productname="Add wallet";


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
       // onpaymnetcomplete();

        //Toast.makeText(this, "u->"+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), Toast.LENGTH_SHORT).show();

        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            money = bundle.getString("money");
            productname = bundle.getString("productnmae");
            entramount.setText(money);



        }else {
            entramount.setHint("Enter Amount");
            productname="Add Wallet";
        }
        addmonry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Userid = SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
                if (Userid.equals("null")) {
                         notlogin();
                }else {


                    addmoney = entramount.getText().toString();

                    if (Integer.valueOf(addmoney) >= 10 || Integer.valueOf(MainActivity.money) >= 10) {
                        // mCurrentEnv = Instamojo.Environment.PRODUCTION;
                        //Toast.makeText(MyWalletActivity.this, "money->" + addmoney, Toast.LENGTH_SHORT).show();
                        JSONObject object = new JSONObject();
                        try {
                            object.put("merchant_id", testmerchentid);
                            object.put("access_token", Testacceskey);
                            object.put("customer_name", SharedPrefManager.getInstance(getApplicationContext()).getUser().getName());
                            object.put("customer_email", SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail());
                            object.put("customer_phone", SharedPrefManager.getInstance(getApplicationContext()).getUser().getPhone());
//                    //    object.put("customer_name","saurav");
//                     //   object.put("customer_email","sauravmishrastp1@gmail.com");
//                        object.put("customer_phone","9560618681");
                            object.put("product_name", productname);
                            object.put("order_no", System.currentTimeMillis()); // order no. should have 10 to 30 character in numeric format
                            object.put("amount", addmoney);  // minimum amount should be 10
                            object.put("isLive", false); // need to send false if you are in sandbox mode

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyWalletActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        new PaykunApiCall.Builder(MyWalletActivity.this).sendJsonObject(object); // Paykun api to initialize your payment and send info.


                        // mCurrentEnv = Instamojo.Environment.PRODUCTION;
                    } else {
                        startdatenull();


                    }
                }
            }
        });

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
    @Override
    protected void onStart() {
        super.onStart();
        // Register this activity to listen to event.
        GlobalBus.getBus().register(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Unregister from activity
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getResults(Events.PaymentMessage message) {
        if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SUCCESS)){
            onpaymnetcomplete();
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            if(MainActivity.money.equals("0")){
                orderplace();
            }

            // do your stuff here
            // message.getTransactionId() will return your failed or succeed transaction id
            /* if you want to get your transaction detail call message.getTransactionDetail()
             *  getTransactionDetail return all the field from server and you can use it here as per your need
             *  For Example you want to get Order id from detail use message.getTransactionDetail().order.orderId */

            if(!TextUtils.isEmpty(message.getTransactionId())) {
               Toast.makeText(MyWalletActivity.this, "Your Transaction is succeed with transaction id : "+message.getTransactionId(),
                       Toast.LENGTH_SHORT).show();


           }
        }

        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_FAILED)){
            // do your stuff here
            Toast.makeText(MyWalletActivity.this,"Your Transaction is failed",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SERVER_ISSUE)){
            // do your stuff here
            Toast.makeText(MyWalletActivity.this,PaykunHelper.MESSAGE_SERVER_ISSUE,Toast.LENGTH_SHORT).show();
        }else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_ACCESS_TOKEN_MISSING)){
            // do your stuff here
            Toast.makeText(MyWalletActivity.this,"Access Token missing",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_MERCHANT_ID_MISSING)){
            // do your stuff here
            Toast.makeText(MyWalletActivity.this,"Merchant Id is missing",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_INVALID_REQUEST)){
            Toast.makeText(MyWalletActivity.this,"Invalid Request",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_NETWORK_NOT_AVAILABLE)){
            Toast.makeText(MyWalletActivity.this,"Network is not available",Toast.LENGTH_SHORT).show();
        }
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
    private void startdatenull(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MyWalletActivity.this);
        builder1.setMessage("Amount can't less than \u20B9 10" +
                " !");
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
    private void notlogin() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MyWalletActivity.this);
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




//    private void createOrderOnServer() {
//
//         final ProgressDialog  progressDialog = new ProgressDialog(this);
//            progressDialog.setMessage("Please wait......");
//            progressDialog.show();
//            GetOrderIDRequest request = new GetOrderIDRequest();
//            request.setEnv(mCurrentEnv.name());
//            request.setBuyerName(SharedPrefManager.getInstance(getApplicationContext()).getUser().getName());
//            request.setBuyerEmail(SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail());
//            request.setBuyerPhone(SharedPrefManager.getInstance(getApplicationContext()).getUser().getPhone());
//            request.setDescription("no dis");
//            request.setAmount(money);
//
//            Call<GetOrderIDResponse> getOrderIDCall = myBackendService.createOrder(request);
//            getOrderIDCall.enqueue(new retrofit2.Callback<GetOrderIDResponse>() {
//                @Override
//                public void onResponse(Call<GetOrderIDResponse> call, Response<GetOrderIDResponse> response) {
//                    if (response.isSuccessful()) {
//                        String orderId = response.body().getOrderID();
//                         progressDialog.dismiss();
//                        Toast.makeText(MyWalletActivity.this, "hii", Toast.LENGTH_SHORT).show();
//                        if (!mCustomUIFlow) {
//                            // Initiate the default SDK-provided payment activity
//                            initiateSDKPayment(orderId);
//                           // Toast.makeText(MyWalletActivity.this, "hii2", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            // OR initiate a custom UI activity
//                            //initiateCustomPayment(orderId);
//                            progressDialog.dismiss();
//                           // Toast.makeText(MyWalletActivity.this, "hii3", Toast.LENGTH_SHORT).show();
//                        }
//
//                    } else {
//                        // Handle api errors
//                        progressDialog.dismiss();
//                        Toast.makeText(MyWalletActivity.this, "ok ", Toast.LENGTH_SHORT).show();
//
//                        try {
//                            JSONObject jObjError = new JSONObject(response.errorBody().string());
//                            Log.d(TAG, "Error in response" + jObjError.toString());
//                            //Toast.makeText(MyWalletActivity.this, "hii4", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            progressDialog.dismiss();
//                            //Toast.makeText(MyWalletActivity.this, "hii5", Toast.LENGTH_SHORT).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            //progressDialog.dismiss();
//                            Toast.makeText(MyWalletActivity.this, "hii6", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<GetOrderIDResponse> call, Throwable t) {
//                    // Handle call failure
//                    Log.d(TAG, "Failure");
//                    progressDialog.dismiss();
//
//                }
//            });
//        }
//
//        private void initiateSDKPayment (String orderID){
//            Instamojo.getInstance().initiatePayment(this, orderID, this);
//        }
//
////    private void initiateCustomPayment(String orderID) {
////        Intent intent = new Intent(getBaseContext(), CustomUIActivity.class);
////        intent.putExtra(Constants.ORDER_ID, orderID);
////        startActivityForResult(intent, Constants.REQUEST_CODE);
////    }
//
//        private void showToast ( final String message){
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//
//        /**
//         * Will check for the transaction status of a particular Transaction
//         *
//         * @param transactionID Unique identifier of a transaction ID
//         */
//        private void checkPaymentStatus ( final String transactionID, final String orderID){
//            if (transactionID == null && orderID == null) {
//                return;
//            }
//
//            if (dialog != null && !dialog.isShowing()) {
//                dialog.show();
//            }
//
//            showToast("Checking transaction status");
//            Call<GatewayOrderStatus> getOrderStatusCall = myBackendService.orderStatus(mCurrentEnv.name().toLowerCase(),
//                    orderID, transactionID);
//            getOrderStatusCall.enqueue(new retrofit2.Callback<GatewayOrderStatus>() {
//                @Override
//                public void onResponse(Call<GatewayOrderStatus> call, final Response<GatewayOrderStatus> response) {
//                    if (dialog != null && dialog.isShowing()) {
//                        dialog.dismiss();
//                    }
//
//                    if (response.isSuccessful()) {
//                        GatewayOrderStatus orderStatus = response.body();
//                        if (orderStatus.getStatus().equalsIgnoreCase("successful")) {
//                            showToast("Transaction still pending");
//                            return;
//                        }
//
//                        showToast("Transaction successful for id - " + orderStatus.getPaymentID());
//                        refundTheAmount(transactionID, orderStatus.getAmount());
//                       // progressDialog.dismiss();
//
//
//                    } else {
//                        showToast("Error occurred while fetching transaction status");
//                        //progressDialog.dismiss();
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<GatewayOrderStatus> call, Throwable t) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (dialog != null && dialog.isShowing()) {
//                                dialog.dismiss();
//                              //  progressDialog.dismiss();
//
//                            }
//                            showToast("Failed to fetch the transaction status");
//
//                           // progressDialog.dismiss();
//
//                        }
//                    });
//                }
//            });
//        }
//
//        /**
//         * Will initiate a refund for a given transaction with given amount
//         *
//         * @param transactionID Unique identifier for the transaction
//         * @param amount        amount to be refunded
//         */
//        private void refundTheAmount (String transactionID, String amount){
//            if (transactionID == null || amount == null) {
//                return;
//            }
//
//            if (dialog != null && !dialog.isShowing()) {
//                dialog.show();
//            }
//
//            showToast("Initiating a refund for - " + amount);
//            Call<ResponseBody> refundCall = myBackendService.refundAmount(
//                    mCurrentEnv.name().toLowerCase(),
//                    transactionID, amount);
//            refundCall.enqueue(new retrofit2.Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    if (dialog != null && dialog.isShowing()) {
//                        dialog.dismiss();
//                    }
//
//                    if (response.isSuccessful()) {
//                        showToast("Refund initiated successfully");
//
//                    } else {
//                        showToast("Failed to initiate a refund");
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    if (dialog != null && dialog.isShowing()) {
//                        dialog.dismiss();
//                        //progressDialog.dismiss();
//
//                    }
//
//                    showToast("Failed to Initiate a refund");
//                }
//            });
//        }
//
//        @Override
//        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
//            super.onActivityResult(requestCode, resultCode, data);
//            if (requestCode == Constants.REQUEST_CODE && data != null) {
//                String orderID = data.getStringExtra(Constants.ORDER_ID);
//                String transactionID = data.getStringExtra(Constants.TRANSACTION_ID);
//                String paymentID = data.getStringExtra(Constants.PAYMENT_ID);
//
//                // Check transactionID, orderID, and orderID for null before using them to check the Payment status.
//                if (transactionID != null || paymentID != null) {
//                    checkPaymentStatus(transactionID, orderID);
//                } else {
//                    showToast("Oops!! Payment was cancelled");
//                   // progressDialog.dismiss();
//                }
//            }
//        }
//
//
//        public void onInstamojoPaymentComplete (String orderID, String transactionID, String
//        paymentID, String paymentStatus){
//
//            Log.d(TAG, "Payment complete");
//            //progressDialog.dismiss();
//
//
//
//        }
//
//
//        public void onPaymentCancelled () {
//            Log.d(TAG, "Payment cancelled");
//            showToast("Payment cancelled by user");
//            //progressDialog.dismiss();
//        }
//
//
//        public void onInitiatePaymentFailure (String errorMessage){
//            Log.d(TAG, "Initiate payment failed");
//            showToast("Initiating payment failed. Error: " + errorMessage);
//           // progressDialog.dismiss();
//            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
//        }


    private void onpaymnetcomplete()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait......");
        progressDialog.show();

        String url ="http://xpertwebtech.in/gfood/api/user-wallet-update?user_id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()+"&money="+addmoney;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray wallet = obj.getJSONArray("wallet");
                            if (status.equals("200")) {
                                Toast.makeText(MyWalletActivity.this, "Update Wallet Sucessfully", Toast.LENGTH_SHORT).show();
                                 entramount.getText().clear();
                                showwallet();
                                progressDialog.dismiss();
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyWalletActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            // progressBar.setVisibility(View.GONE);
                             progressDialog.dismiss();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyWalletActivity.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private  void showwallet(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait......");
        progressDialog.show();
        String url ="http://xpertwebtech.in/gfood/api/view-wallet?user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
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
                                progressDialog.dismiss();

                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                           // Toast.makeText(MyWalletActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            // progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyWalletActivity.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
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
            String ug = "http://xpertwebtech.in/gfood/api/bill-submit";
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
                                  //  progressDialog.dismiss();

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
                            Toast.makeText(MyWalletActivity.this, "Order Place Succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MyWalletActivity.this, OrderHitoryActivity .class);
                            startActivity(intent);
                           // Toast.makeText(MyWalletActivity.this, "Server Not Responding" + error, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("product_id", AddPlaneActivity.pid);
                    params.put("invoice_no", "41543");
                    params.put("order_status", "1");
                    params.put("credit_use","0");
                    params.put("wallet_use","0");
                    params.put("unit_price",String.valueOf(AddPlaneActivity.pricceeeel));
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

