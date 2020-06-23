package adapterclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xpertwebtech.gfoods.R;

import java.util.ArrayList;

import modelclass.ViewBillModel;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private ArrayList<ViewBillModel>vactionmodels;
    private Context context;

    public OrderHistoryAdapter(ArrayList<ViewBillModel> vactionmodels, Context context) {
        this.vactionmodels = vactionmodels;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viebillivocelayout,parent,false);
        return new OrderHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ViewHolder holder, final int position) {
    final String startdate = vactionmodels.get(position).getDate1();
    final String enddate = vactionmodels.get(position).getDate2();
    String invoce = vactionmodels.get(position).getInvoce();
    final String img = vactionmodels.get(position).getImg();
    final String price = vactionmodels.get(position).getPrice();
    String status = vactionmodels.get(position).getOrderstatus();
    final String quant = vactionmodels.get(position).getQuant();
    final String name = vactionmodels.get(position).getProductname();
       holder.editicon.setVisibility(View.GONE);
       holder.qunat.setText(quant+"pkt");
        Picasso.get().load(img).into(holder.productimg);
        holder.productname.setText(name);

       // holder.productquant.setText(quant+"lt");
        holder.price.setText("\u20B9"+ price);
        if(status.equals("1")){
            holder.markdellever.setText("order placed");
        } if(status.equals("2")){
            holder.markdellever.setText("processing");
        } if(status.equals("3")){
            holder.markdellever.setText("Deleverd");
        }

//      holder.itemView.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              Intent intent=new Intent(context, AddPlaneActivity.class);
//              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//              intent.putExtra("name",name);
//              intent.putExtra("qunat",quant);
//              intent.putExtra("price",price);
//              intent.putExtra("img",img);
//              intent.putExtra("startdate",startdate);
//              intent.putExtra("enddate",enddate);
//              intent.putExtra("id",vactionmodels.get(position).getId());
//              intent.putExtra("pid",vactionmodels.get(position).getProductid());
//              intent.putExtra("plantye",vactionmodels.get(position).getDaytype());
//              intent.putExtra("type","myplan");
//
//              context.startActivity(intent);
//          }
//      });
    }

    @Override
    public int getItemCount() {
        return vactionmodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname,price,add,qunat,date;
        ImageView productimg,editicon;
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
            editicon = itemView.findViewById(R.id.editicon);

        }
    }
}
