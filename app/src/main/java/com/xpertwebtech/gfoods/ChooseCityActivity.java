package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapterclass.CityAdapter;
import modelclass.CityModel;
import utils.VolleySingleton;

public class ChooseCityActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private ArrayList<CityModel>cityModels = new ArrayList<>();
private Bundle bundle;
public static String stateid,statename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        recyclerView = findViewById(R.id.cityrecyclerview);
        bundle = getIntent().getExtras();

        stateid = bundle.getString("id");
        statename = bundle.getString("name");
        //Toast.makeText(this, "id="+stateid, Toast.LENGTH_SHORT).show();
     getcity();
    }


    private void getcity()
    {
        String url ="http://lsne.in/gfood/api/city?state_id="+stateid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray state = obj.getJSONArray("City");

                            if (status.equals("200")) {


                                for(int j=0;j<state.length();j++) {

                                    JSONObject stateJSONObject = state.getJSONObject(j);
                                    String Name = stateJSONObject.getString("city");
                                    String id = stateJSONObject.getString("id");


                                   cityModels.add(new CityModel(Name,id));
                                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(ChooseCityActivity.this, 2);

                                    LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                                    recyclerView.setLayoutManager(layoutManager);
                                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                                    CityAdapter gridProductAdapter = new CityAdapter(cityModels,ChooseCityActivity.this);
                                    recyclerView.setAdapter(gridProductAdapter);
                                    gridProductAdapter.notifyDataSetChanged();


                                }
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ChooseCityActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                            //  progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseCityActivity.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
                        //   progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}


