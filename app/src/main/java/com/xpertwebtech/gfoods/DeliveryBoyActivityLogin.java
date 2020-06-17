package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import modelclass.User;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class DeliveryBoyActivityLogin extends AppCompatActivity {
      private TextView register;
    private Button login;
    private EditText useridEt,passEt;
    private  String userId,password;
    private TextView skip;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_login);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        skip = findViewById(R.id.skip);
        useridEt = findViewById(R.id.shipingaddress_et);
        progressBar = findViewById(R.id.progress_circular);
        passEt = findViewById(R.id.passwordet);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryBoyActivityLogin.this,DeliveryBoySinup.class);
                startActivity(intent);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryBoyActivityLogin.this,MainActivity.class);
                startActivity(intent);
                User user=new User("null","null","null","null","null","null");
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();

            }
        });
    }


    private void login()
    {
        userId = useridEt.getText().toString();
        password = passEt.getText().toString();
        if (TextUtils.isEmpty(userId))
        {
            useridEt.setError("Required");
            useridEt.setFocusable(true);
        }
        else if (TextUtils.isEmpty(password))
        {
            passEt.setError("Required");
            passEt.setFocusable(true);
        }

        else {
            loginUser();
        }
    }

    private void loginUser()
    {
        progressBar.setVisibility(View.VISIBLE);
        String url ="http://lsne.in/gfood/api/login-dilevery-boy?phone="+userId+"&password="+password;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONObject userJson = obj.getJSONObject("dilevery_boy");

                            if (status.equals("200")) {


                                String userid = userJson.getString("id");
                                String username = userJson.getString("name");
                                String useremail = userJson.getString("email");
                                String userphone = userJson.getString("phone");
                                String city = userJson.getString("active");

                                User user=new User(userid,username,userphone,useremail,city,"");

                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                if(city.equals("1")) {
                                    Toast.makeText(DeliveryBoyActivityLogin.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(DeliveryBoyActivityLogin.this, OrderActivity.class);
                                    startActivity(intent);
                                }
                                progressBar.setVisibility(View.GONE);

                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DeliveryBoyActivityLogin.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DeliveryBoyActivityLogin.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}

