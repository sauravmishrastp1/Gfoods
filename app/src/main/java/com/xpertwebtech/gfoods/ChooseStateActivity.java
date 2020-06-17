package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapterclass.StateAdapter;
import modelclass.StateName;
import utils.VolleySingleton;

public class ChooseStateActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<StateName>stateNames =new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_state);
        recyclerView = findViewById(R.id.stateRecycler);
        progressBar = findViewById(R.id.progressbarr);
        getstate();
    }


    private void getstate()
    {
        progressBar.setVisibility(View.VISIBLE);
        String url ="http://lsne.in/gfood/api/state";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray state = obj.getJSONArray("State");

                            if (status.equals("200")) {


                                for(int j=0;j<state.length();j++) {

                                    JSONObject stateJSONObject = state.getJSONObject(j);
                                    String Name = stateJSONObject.getString("state");
                                    String id = stateJSONObject.getString("id");


                                    stateNames.add(new StateName(R.drawable.delhi,Name,id));

                                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(ChooseStateActivity.this, 2);

                                    recyclerView.setLayoutManager(gridLayoutManager1);
                                    StateAdapter gridProductAdapter = new StateAdapter(stateNames, ChooseStateActivity.this);
                                    recyclerView.setAdapter(gridProductAdapter);
                                    gridProductAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);

                                }
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ChooseStateActivity.this, "somrthing went wrong", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseStateActivity.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
                       progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    }


