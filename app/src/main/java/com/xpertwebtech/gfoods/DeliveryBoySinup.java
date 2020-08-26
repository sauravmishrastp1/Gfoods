package com.xpertwebtech.gfoods;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofitfileupload.Api;
import utils.FileUtils;
import utils.MultiPartHelperClass;
import utils.VolleyMultipartRequest;
import utils.VolleySingleton;

public class DeliveryBoySinup extends AppCompatActivity {
    private EditText fullnameEt,passwordet;
    private EditText phonenoEt;
    private EditText emailEt;
    private EditText locationEt;
    private Button sinup;
    private View uploadImage;
    private static final int GALLERY_IMAGE = 1;
    private String filePathpic = "null";
    private String extension="null";
    private String type="null";
    private byte pic[]="00.00.00".getBytes();
    private ImageView uploadyes,uploadno;
    private String fullname,phoneno,emailid,location,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_sinup);
        fullnameEt = findViewById(R.id.shipingaddress_et);
        phonenoEt = findViewById(R.id.phoneet);
        uploadyes = findViewById(R.id.uploadsuccess);
        uploadno = findViewById(R.id.notupload);
        passwordet = findViewById(R.id.passwortet);
        emailEt = findViewById(R.id.emailet);
        locationEt = findViewById(R.id.location);
        uploadImage = findViewById(R.id.choosevideoview);
        sinup = findViewById(R.id.sinupup);
        sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimage();
            }


        });

    }

    private void uploadimage() {
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
               // Toast.makeText(this, "extension=>"+extension, Toast.LENGTH_SHORT).show();
               // Toast.makeText(this, "extension=>"+type, Toast.LENGTH_SHORT).show();


                try {
                    // Toast.makeText(this, ""+filePathpic, Toast.LENGTH_SHORT).show();
                    ContentResolver cr = getBaseContext().getContentResolver();
                    InputStream inputStream = cr.openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathpic);
                    //  imageView.setImageBitmap(bitmap);
                    uploadno.setVisibility(View.GONE);
                    uploadyes.setVisibility(View.VISIBLE);
                     //Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    pic = baos.toByteArray();
                   // Toast.makeText(this, "pic=>"+pic, Toast.LENGTH_SHORT).show();
                    uploadno.setVisibility(View.GONE);
                    uploadyes.setVisibility(View.VISIBLE);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            } catch (Exception e) {
                e.printStackTrace();
                //  Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void register(){
        fullname = fullnameEt.getText().toString();
        emailid = emailEt.getText().toString();
        location = locationEt.getText().toString();
        phoneno = phonenoEt.getText().toString();
        pass = passwordet.getText().toString();

        if (TextUtils.isEmpty(fullname))
        {
            fullnameEt.setError("Required");
            fullnameEt.setFocusable(true);
        }
        else if (TextUtils.isEmpty(location)){
            locationEt.setError("Required");
            locationEt.setFocusable(true);

        }
        else if (TextUtils.isEmpty(pass)){
            passwordet.setError("Required");
            passwordet.setFocusable(true);

        }
        else if(phoneno.length()<10||phoneno.length()>10){
            phonenoEt.setError("Phone must be 10 digits");
            phonenoEt.setFocusable(true);
        }
        else {
            validate();
        }
    }

    private void registerprocess() {
        Toast.makeText(this, ""+extension+type+pic, Toast.LENGTH_SHORT).show();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        // progressDialog.setIcon(R.drawable.headwaygmslogo);
        progressDialog.setTitle("Register.....");
        progressDialog.setMessage("Please wait......");
        progressDialog.show();
        String url ="http://xpertwebtech.in/gfood/api/register-dilevery-boy";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    //JSONObject result = new JSONObject(resultResponse);
                    // Toast.makeText(Registeration_Activity.this, ""+result, Toast.LENGTH_SHORT).show();
                    JSONObject obj = new JSONObject(resultResponse);
                    String status = obj.getString("status");
                    String message = obj.getString("msg");
                    //JSONArray profiledata = obj.getJSONArray("dilevery_boy");
                    if (status.equals("200")) {
                        Toast.makeText(DeliveryBoySinup.this, "Register Sucessfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else {
                        Log.i("Unexpected", message);
                        progressDialog.dismiss();
//                            Intent  inten=new Intent(Registeration_Activity.this,MainActivity.class);
//                            inten.putExtra("type","success");
//                            startActivity(inten);
                        Toast.makeText(DeliveryBoySinup.this, "" + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DeliveryBoySinup.this, "something went wrong" + e, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DeliveryBoySinup.this, "Timeout Error", Toast.LENGTH_SHORT).show();
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                        progressDialog.dismiss();
                        Toast.makeText(DeliveryBoySinup.this, "Server Not responding", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        Toast.makeText(DeliveryBoySinup.this, "" + result, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(DeliveryBoySinup.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                            progressDialog.dismiss();
                            Toast.makeText(DeliveryBoySinup.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                            progressDialog.dismiss();
                            Toast.makeText(DeliveryBoySinup.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                            progressDialog.dismiss();
                            Toast.makeText(DeliveryBoySinup.this, "Server Not responding", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(DeliveryBoySinup.this, "Something went wrong" + e, Toast.LENGTH_SHORT).show();

                    }
                }
                Log.i("Error", errorMessage);
                progressDialog.dismiss();
                Toast.makeText(DeliveryBoySinup.this, "" + errorMessage, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                params.put("aadhar_card", new DataPart("addhar" + extension, pic, type));
                return params;


            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", fullname);
                params.put("email", emailid);
                params.put("password",pass);
                params.put("phone",phoneno);
                params.put("address",location);
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

    private void validate() {

        // Toast.makeText(this, "" +account_type, Toast.LENGTH_SHORT).show();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        // progressDialog.setIcon(R.drawable.headwaygmslogo);
        progressDialog.setTitle("Submit.....");
        progressDialog.setMessage("Please wait......");
        progressDialog.show();


        File file = new File(filePathpic);
      //  File file1 = new File(filePatadhar);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), filePathpic);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("newimage", file.getName(), requestBody);
       // MultipartBody.Part parts2 = MultipartBody.Part.createFormData("newimage", file1.getName(), requestBody);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //creating our api
        Api api = retrofit.create(Api.class);


        //  RequestBody someData = RequestBody.create(MediaType.parse("text/plain"), "This is a new Image");

        Api uploadApis = retrofit.create(Api.class);
        Call call = uploadApis.becomevendor(MultiPartHelperClass.getMultipartData(new File(filePathpic),"aadhar_card"),fullname,emailid,pass,phoneno,location);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit2.Call<Response> call, retrofit2.Response<Response> response) {
                try {
                    assert response.body() != null;
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(DeliveryBoySinup.this);
                    builder1.setMessage("Thanku! Your Details is Submit");
                    builder1.setCancelable(true);
                    builder1.setIcon(R.drawable.logo);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(DeliveryBoySinup.this, MainActivity.class);
                                    startActivity(intent);

                                }
                            });


                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    progressDialog.dismiss();
                 progressDialog.dismiss();


                } catch (Exception e) {
                    progressDialog.dismiss();

                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}




