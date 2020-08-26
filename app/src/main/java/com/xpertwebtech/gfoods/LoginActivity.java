package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import constants.Otp_Reciver;
import modelclass.User;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class LoginActivity extends AppCompatActivity {
  private Button login;
  private TextView skip;
  private TextView register;
  private RelativeLayout backpress;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
  private EditText userEt;
  public static EditText passEt;
  private  String userId,password;
  private ProgressBar progressBar;
  private View otp_layout;
  private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        skip = findViewById(R.id.skip);
        register = findViewById(R.id.register);
        userEt = findViewById(R.id.shipingaddress_et);
        passEt = findViewById(R.id.passwordet);
        otp_layout = findViewById(R.id.inputlayousecond);
        progressBar = findViewById(R.id.progress_circular);
        requestsmspermission();
       
            userEt.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {}

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if(s.length() ==10){

                        userId = userEt.getText().toString();
                        sendotp();

                    }else {
                        otp_layout.setVisibility(View.GONE);
                    }

                }
            });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                User user=new User("null","null","null","null","null","null");
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

            }
        });

    }

    private void login()
    {
         userId = userEt.getText().toString();
         password = passEt.getText().toString();
        if (TextUtils.isEmpty(userId))
        {
            userEt.setError("Required");
            userEt.setFocusable(true);
        }
        else if (TextUtils.isEmpty(password))
        {
            passEt.setError("Required");
            passEt.setFocusable(true);
        }else if(!password.equals(otp)){
            passEt.setError("Otp not match");
            passEt.setFocusable(true);
        }

        else {
            loginUser();
        }
    }

    private void loginUser()
    {
        progressBar.setVisibility(View.VISIBLE);
        String url ="http://xpertwebtech.in/gfood/api/login?phone="+userId+"&user_otp="+password;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONObject userJson = obj.getJSONObject("user");

                            if (status.equals("200")) {


                                String userid = userJson.getString("id");
                                String username = userJson.getString("name");
                                String useremail = userJson.getString("email");
                                String userphone = userJson.getString("phone");
                                String city = userJson.getString("city");
                                String myrefercode = userJson.getString("own_refer_code");

                                User user=new User(userid,username,userphone,useremail,city,myrefercode);

                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                Toast.makeText(LoginActivity.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);

                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void sendotp(){


        otp_layout.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.getCache().clear();
        String url = "http://xpertwebtech.in/gfood/api/send-otp?phone="+userId;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");


                            if (status.equals("200")) {
                                Toast.makeText(LoginActivity.this, "Otp sent!", Toast.LENGTH_SHORT).show();

                                otp = obj.getString("otp");
                                new Otp_Reciver().setEditText(passEt);

                                   // progressDialog.dismiss();


//                                JSONArray userdata = obj.getJSONArray("user");
//                                for(int j=0;j<userdata.length();j++) {
//                                    JSONObject userJsonn = userdata.getJSONObject(j);
//
//                                    String userid = userJsonn.getString("id");
//                                    String username = userJsonn.getString("name");
//                                    String phoneno = userJsonn.getString("email");
//                                    String userphone = userJsonn.getString("email2");
//
//                                    User user = new User(userid, username, userphone, "", phoneno);
//
//                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//                                    Toast.makeText(LoginActivity.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                    progressBar.setVisibility(View.GONE);
//                                }



                            } else {

                                Toast.makeText(getApplicationContext(), "This no is not register", Toast.LENGTH_SHORT).show();
                               // progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "otp sent"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            //progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "otp sent"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                       // progressDialog.dismiss();
                    }
                }) {
//            @Override
//            protected Map<String, String> getParams()  {
//                Map<String, String> params = new HashMap<>();
//                params.put("name", name);
//                params.put("phone_number", phone);
//                params.put("password", pass);
//                params.put("email",email);
//                return params;
//            }
        };
        queue.getCache().clear();
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    protected void requestStoragePermission(){
        if(ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                LoginActivity.this,
                                new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }else {
            // Do something, when permissions are already granted
            Toast.makeText(this,"Permissions already granted",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        requestStoragePermission();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if(
                        (grantResults.length >0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        )
                ){
                    // Permissions are granted
                    Toast.makeText(this,"Permissions granted.",Toast.LENGTH_SHORT).show();
                }else {
                    // Permissions are denied
                    Toast.makeText(this,"Permissions denied.",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void requestsmspermission() {
        String smspermission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this,smspermission);
        //check if read SMS permission is granted or not
        if(grant!= PackageManager.PERMISSION_GRANTED)
        {
            String[] permission_list = new String[1];
            permission_list[0]=smspermission;
            ActivityCompat.requestPermissions(this,permission_list,1);
        }
    }

}
