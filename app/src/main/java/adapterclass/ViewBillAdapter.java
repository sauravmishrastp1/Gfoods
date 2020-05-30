package adapterclass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xpertwebtech.gfoods.AddPlaneActivity;
import com.xpertwebtech.gfoods.R;

import java.util.ArrayList;

import modelclass.Vactionmodel;
import modelclass.ViewBillModel;

public class  ViewBillAdapter extends RecyclerView.Adapter<ViewBillAdapter.ViewHolder> {
    private ArrayList<ViewBillModel>vactionmodels;
    private Context context;

    public ViewBillAdapter(ArrayList<ViewBillModel> vactionmodels, Context context) {
        this.vactionmodels = vactionmodels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewBillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myplanproductlyout,parent,false);
        return new ViewBillAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBillAdapter.ViewHolder holder, final int position) {
    final String startdate = vactionmodels.get(position).getDate1();
    final String enddate = vactionmodels.get(position).getDate2();
    String invoce = vactionmodels.get(position).getInvoce();
    final String img = vactionmodels.get(position).getImg();
    final String price = vactionmodels.get(position).getPrice();
    String status = vactionmodels.get(position).getOrderstatus();
    final String quant = vactionmodels.get(position).getQuant();
    final String name = vactionmodels.get(position).getProductname();

       holder.qaunt.setText(quant);
        Picasso.get().load(img).into(holder.imageView);
        holder.productname.setText(name);
        if(quant.equals("0")){
            holder.layout.setVisibility(View.GONE);
        }
        holder.productquant.setText(quant+"lt");
        holder.price.setText(price);

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(context, AddPlaneActivity.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              intent.putExtra("name",name);
              intent.putExtra("qunat",quant);
              intent.putExtra("price",price);
              intent.putExtra("img",img);
              intent.putExtra("startdate",startdate);
              intent.putExtra("enddate",enddate);
              intent.putExtra("id",vactionmodels.get(position).getId());
              intent.putExtra("pid",vactionmodels.get(position).getProductid());
              intent.putExtra("plantye",vactionmodels.get(position).getDaytype());
              intent.putExtra("type","myplan");

              context.startActivity(intent);
          }
      });
    }

    @Override
    public int getItemCount() {
        return vactionmodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView qaunt,price,productname,productquant;
        View layout;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           layout = itemView.findViewById(R.id.layouttt);
            qaunt = itemView.findViewById(R.id.count);
            productname =itemView.findViewById(R.id.productname);
            productquant = itemView.findViewById(R.id.productquantity);
            price = itemView.findViewById(R.id.mrp);
            imageView = itemView.findViewById(R.id.productimg);

        }
    }
}
