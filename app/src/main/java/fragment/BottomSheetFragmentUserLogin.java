package fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xpertwebtech.gfoods.MyProfileActivty;
import com.xpertwebtech.gfoods.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.SharedPrefManager;
import utils.VolleySingleton;

import static android.widget.Toast.LENGTH_SHORT;

public class BottomSheetFragmentUserLogin extends BottomSheetDialogFragment {

    private String id;
    public static String amount,coursename,userid,courseid,quizetiem,quizetiem1,paymentstatus;
    private Button button;
    private EditText username,phoneno,location;
    private String registerphone,otp;
    private String  name, phonemno,locationn;
    private String Otp_code;
    private String Name,Pone,Location;
    LinearLayout forget_password;
    View emiallayout, otplayout;
    private String blockstatus;
    private ProgressDialog progressDialog;
    private String status="null";

    public BottomSheetFragmentUserLogin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.editprofile, container, false);

     username = view.findViewById(R.id.shipingaddress_et);
     phoneno = view.findViewById(R.id.phoneet);
     location = view.findViewById(R.id.emailet);
     button = view.findViewById(R.id.sinupup);
     name =SharedPrefManager.getInstance(getContext()).getUser().getName();
        phonemno =SharedPrefManager.getInstance(getContext()).getUser().getPhone();
        locationn = SharedPrefManager.getInstance(getContext()).getUser().getCity();
        username.setText(name);
        phoneno.setText(phonemno);
        location.setText(locationn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editprofile();
            }
        });



        //showItemType();



        return view;
    }

    private void editprofile(){
         Name = username.getText().toString();
         Pone = phoneno.getText().toString();
         Location = location.getText().toString();

      progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Update.....");
        progressDialog.setMessage("Please wait......");
        progressDialog.show();

        String url = "http://lsne.in/gfood/api/update-profile-details";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //converting response to json object

                    JSONObject obj = new JSONObject(response);

                    String message = obj.getString("msg");
                    String status = obj.getString("status");
                    // String otp = obj.getString("otp");


                    if (status.equals("200")) {

                        Toast.makeText(getContext(), "Update Successfully", LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(getContext(), MyProfileActivty.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getContext(), "" + obj.getString("msg"), LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Something went wrong" + e.getMessage(), LENGTH_SHORT).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Server Not Respondin Check Your Internet Connection"+error.getMessage(), LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", Name);
                params.put("phone", Pone);
                params.put("state", Location);
                params.put("city", Location);
                params.put("id", SharedPrefManager.getInstance(getContext()).getUser().getId());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).getRequestQueue().getCache().clear();

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


}