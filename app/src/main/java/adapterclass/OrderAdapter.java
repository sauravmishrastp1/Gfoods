package adapterclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import com.xpertwebtech.gfoods.ChooseCityActivity;
import com.xpertwebtech.gfoods.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.xml.transform.Templates;

import modelclass.CityModel;
import modelclass.OrderModel;
import utils.SharedPrefManager;
import utils.VolleySingleton;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<OrderModel>orderModels;
    private Context context;
    private String datev;

    public OrderAdapter(ArrayList<OrderModel> orderModels, Context context) {
        this.orderModels = orderModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viebillivocelayout,parent,false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String id = orderModels.get(position).getId();
        String pid = orderModels.get(position).getProductid();
        String pname = orderModels.get(position).getProductname();
         datev = orderModels.get(position).getDate();
        String quant = orderModels.get(position).getQuantity();
        String totalprice = orderModels.get(position).getPrice();
        String img = orderModels.get(position).getImage();
        String city = orderModels.get(position).getCity();
        holder.productname.setText(pname);
        Picasso.get().load(img).into(holder.productimg);
        holder.add.setText(city);
        holder.date.setText(datev);
        holder.qunat.setText(quant+"pkt");
        holder.price.setText("\u20B9"+totalprice);
        holder.markdellever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url ="http://lsne.in/gfood/api/mark-deleverd?date="+datev+"&invoice_no"+"1230456"+"&makr_dileverd="+"1";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    JSONObject obj = new JSONObject(response);
                                    String status = obj.getString("state");
                                    JSONArray state = obj.getJSONArray("mark_dileverd");

                                    if (status.equals("200")) {
                                        holder.markdellever.setText("Deleverd");
                                        holder.markdellever.setClickable(false);
                                        Toast.makeText(context, "Deleverd Sucessfully", Toast.LENGTH_SHORT).show();

//                                for(int j=0;j<state.length();j++) {
//
//                                    JSONObject stateJSONObject = state.getJSONObject(j);
//                                    String Name = stateJSONObject.getString("city");
//                                    String id = stateJSONObject.getString("id");
//
//
//
//
//                                }
                                    } else {

                                        Toast.makeText(context, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                        // progressBar.setVisibility(View.GONE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "Data Not Found"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    //  progressBar.setVisibility(View.GONE);
                                }
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Server Not Responding", Toast.LENGTH_SHORT).show();
                                //   progressBar.setVisibility(View.GONE);
                            }
                        }) {

                };
                VolleySingleton.getInstance(context).getRequestQueue().getCache().clear();
                VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


            }
        });
    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname,price,add,qunat,date;
        ImageView productimg;
        Button markdellever;
        View view ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productname = itemView.findViewById(R.id.invoice);
            price = itemView.findViewById(R.id.price);
            add= itemView.findViewById(R.id.ordertimimng);
            qunat = itemView.findViewById(R.id.status);
            productimg = itemView.findViewById(R.id.img);
            markdellever = itemView.findViewById(R.id.button);
            date = itemView.findViewById(R.id.dateee);
            view = itemView.findViewById(R.id.view);
        }
    }

    private void markasdilever()
    {

    }
}
