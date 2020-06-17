package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import modelclass.User;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class RegisterActivity extends AppCompatActivity {
 private Button sinup;
 private EditText userId,EmalEt,PhoneEt,passwordEt,address;
 private String name,phone,email,pass;
 private ProgressBar progressBar;
 private Bundle bundle;
 private String cityname;
 private String randomNumber;
 private EditText refercalcodeet;
 private String enterreferalcode="null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sinup=findViewById(R.id.sinupup);
        refercalcodeet = findViewById(R.id.referandearn);
        userId = findViewById(R.id.shipingaddress_et);
        EmalEt = findViewById(R.id.emailet);
        PhoneEt = findViewById(R.id.phoneet);
        passwordEt = findViewById(R.id.passwordet);
        progressBar = findViewById(R.id.progress_circular);
        address = findViewById(R.id.location);


        sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFields();
            }
        });

    }

    private void verifyFields()
    {
        name=userId.getText().toString();
        phone=PhoneEt.getText().toString();
        pass=passwordEt.getText().toString();
       // confirmpass=confirmpassEt.getText().toString();
        email = EmalEt.getText().toString();
        cityname =address.getText().toString();
        enterreferalcode = refercalcodeet.getText().toString();



        if (TextUtils.isEmpty(name))
        {
            userId.setError("Required");
            userId.setFocusable(true);
        }
        else if (TextUtils.isEmpty(phone)){
            PhoneEt.setError("Required");
            PhoneEt.setFocusable(true);

        }
        else if (TextUtils.isEmpty(pass)){
            passwordEt.setError("Required");
            passwordEt.setFocusable(true);

        }
        else if (TextUtils.isEmpty(cityname)){
            address.setError("Required");
            address.setFocusable(true);

        }
        else if(phone.length()<10||phone.length()>10){
            PhoneEt.setError("Phone must be 10 digits");
            PhoneEt.setFocusable(true);
        }
         else if(TextUtils.isEmpty(email)){
            EmalEt.setError("Required");
            EmalEt.setFocusable(true);

        }
        else {
            char[] chars ="ABCDEFGHIJKLMNOPQRWXYZ12345678".toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            Random random = new Random();
            for(int i =0;i<6;i++){
                char c =chars[random.nextInt(chars.length)];
                stringBuilder.append(c);
            }

            randomNumber = stringBuilder.toString();
            Toast.makeText(this, "code=>"+randomNumber, Toast.LENGTH_SHORT).show();

            registerUser(randomNumber,enterreferalcode);
        }
    }

    private void registerUser(String random,String enterreferalcode)
    {

        progressBar.setVisibility(View.VISIBLE);
        String url = "http://lsne.in/gfood/api/register?name="+name+"&email="+email+"&password="+pass+"&phone="+phone+"&state="+cityname+"&city="+cityname+"&refer_code="+enterreferalcode+"&own_refer_code="+random;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                                String refercode = userJson.getString("own_refer_code");

                                User user=new User(userid,username,userphone,useremail,city,refercode);


                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                // Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                onpaymnetcomplete();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                                progressBar.setVisibility(View.GONE);

                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Allready Register", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
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
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void onpaymnetcomplete()
    {
        String url ="http://lsne.in/gfood/api/wallet?user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()+"&money="+"0";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray wallet = obj.getJSONArray("wallet");
                            if (status.equals("200")) {
                                Toast.makeText(RegisterActivity.this, "sucessfull", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            // progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }




}
