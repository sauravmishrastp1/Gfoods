package com.xpertwebtech.gfoods;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import fragment.BottomSheetFragmentUserLogin;
import utils.FileUtils;
import utils.SharedPrefManager;
import utils.VolleyMultipartRequest;
import utils.VolleySingleton;

public class MyProfileActivty extends AppCompatActivity {
    private RelativeLayout backpress;
    private TextView name,phone,email,location;
    private ImageView imageView;
    private  ImageView changeprofile;
    private static final int GALLERY_IMAGE = 1;
    private String filePathpic = "";
    private String extension;
    private String type="";
    private Button editprofilbtn;

    private byte pic[]="00.00.00".getBytes();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_activty);
        imageView = findViewById(R.id.profileimg);
        editprofilbtn = findViewById(R.id.buttonedit);
        changeprofile = findViewById(R.id.changeimage);
        name = findViewById(R.id.username);
        phone = findViewById(R.id.phoneNo);
        email = findViewById(R.id.emailet);
        location = findViewById(R.id.locationet);
        backpress = findViewById(R.id.backpresslayout);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivty.this,MainActivity.class);
                startActivity(intent);
            }
        });
        getprofiledata();
        changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             choosepic();
            }
        });
        editprofilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragmentUserLogin f = new BottomSheetFragmentUserLogin();
                FragmentManager fm = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                f.show(fm, "Select your option");
            }
        });
    }
    public void choosepic () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_IMAGE);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            //Toast.makeText(this, "you selected="+filePathpic+uri, Toast.LENGTH_SHORT).show();

            try {
                filePathpic = FileUtils.getPath(this, uri);
                 //imageView.setImageDrawable(Drawable.createFromPath(filePathpic));
                type = FileUtils.getMimeType(this, uri);
                extension = (String) FileUtils.getExtension(String.valueOf(uri));


                try {
                   // Toast.makeText(this, ""+filePathpic, Toast.LENGTH_SHORT).show();
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathpic);
                    imageView.setImageBitmap(bitmap);
                    // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    pic = baos.toByteArray();
                    changeimage();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            } catch (Exception e) {
                e.printStackTrace();
                //  Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void changeimage()
    {
         final ProgressDialog progressDialog = new ProgressDialog(this);
        // progressDialog.setIcon(R.drawable.headwaygmslogo);
        progressDialog.setTitle("Update.....");
        progressDialog.setMessage("Please wait......");
        progressDialog.show();
        String url ="http://lsne.in/gfood/api/upload-profile-image";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    // Toast.makeText(Registeration_Activity.this, ""+result, Toast.LENGTH_SHORT).show();
                    JSONObject obj = new JSONObject(resultResponse);
                    String status = obj.getString("status");
                    String message = obj.getString("msg");
                    JSONArray profiledata = obj.getJSONArray("data");
                    if (status.equals("200")) {
                        for (int i = 0; i < profiledata.length(); i++) {
                            JSONObject profiledataJSONObject = profiledata.getJSONObject(i);
                            String Img = profiledataJSONObject.getString("profile_image");
                            String uidd = profiledataJSONObject.getString("id");

                             if(uidd==SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()) {

                                 Picasso.get().load("http://lsne.in/gfood/upload/" + Img).into(imageView);
                             }


                        }
                        Toast.makeText(MyProfileActivty.this, "Update Succesfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                         else {
                        Log.i("Unexpected", message);
                        progressDialog.dismiss();
//                            Intent  inten=new Intent(Registeration_Activity.this,MainActivity.class);
//                            inten.putExtra("type","success");
//                            startActivity(inten);
                        Toast.makeText(MyProfileActivty.this, "" + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MyProfileActivty.this, "something went wrong" + e, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                        progressDialog.dismiss();
                        Toast.makeText(MyProfileActivty.this, "Timeout Error", Toast.LENGTH_SHORT).show();
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                        progressDialog.dismiss();
                        Toast.makeText(MyProfileActivty.this, "Server Not responding", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        Toast.makeText(MyProfileActivty.this, "" + result, Toast.LENGTH_SHORT).show();
                        // JSONObject response = new JSONObject(result);
//                            String status = response.getString("status");
//                            String message = response.getString("msg");

                        String status = "";
                        String message = "";

                        if (status.equals("200")) {
                            //Toast.makeText(Registeration_Activity.this, "msg", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(Registeration_Activity.this,MainActivity.class));
                            // finish();
                            progressDialog.dismiss();
                        }

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                            progressDialog.dismiss();
                            Toast.makeText(MyProfileActivty.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                            progressDialog.dismiss();
                            Toast.makeText(MyProfileActivty.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                            progressDialog.dismiss();
                            Toast.makeText(MyProfileActivty.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                            progressDialog.dismiss();
                            Toast.makeText(MyProfileActivty.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(MyProfileActivty.this, "Something went wrong" + e, Toast.LENGTH_SHORT).show();

                    }
                }
                Log.i("Error", errorMessage);
               progressDialog.dismiss();
                Toast.makeText(MyProfileActivty.this, "" + errorMessage, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                params.put("profile_image", new DataPart("pan_doc" + extension, pic, type));

                return params;


            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
                return params;
            }
        };


        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);

    }


    private void getprofiledata()
    {
      String url ="http://lsne.in/gfood/api/profile-details?id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            JSONArray profiledata = obj.getJSONArray("profile");
                            if (status.equals("200")) {
                                for(int i=0;i<profiledata.length();i++) {
                                    JSONObject profiledataJSONObject = profiledata.getJSONObject(i);
                                    String Name = profiledataJSONObject.getString("name");
                                    String emaiL = profiledataJSONObject.getString("email");
                                    String phonE = profiledataJSONObject.getString("phone");
                                    String city = profiledataJSONObject.getString("city");
                                    location.setText(city);

                                 name.setText(Name);
                                 email.setText(emaiL);
                                 phone.setText(phonE);



                                }
                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyProfileActivty.this, "somrthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            // progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyProfileActivty.this, "Server Not Responding"+error, Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.GONE);
                    }
                }) {

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}

