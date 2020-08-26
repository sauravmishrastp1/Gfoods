package adapterclass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xpertwebtech.gfoods.AddPlaneActivity;
import com.xpertwebtech.gfoods.R;

import java.util.ArrayList;

import modelclass.MyPlanProductCat;

public class MyProductcategory extends RecyclerView.Adapter<MyProductcategory.ViewHolder> {
    private ArrayList<MyPlanProductCat>myPlanModelClasses;
    private Context context;

    public MyProductcategory(ArrayList<MyPlanProductCat> myPlanModelClasses, Context context) {
        this.myPlanModelClasses = myPlanModelClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myplanproductlyout,parent,false);
        return new MyProductcategory.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String img = myPlanModelClasses.get(position).getProductimg();
        final String name = myPlanModelClasses.get(position).getProducvtname();
        final String quant = myPlanModelClasses.get(position).getQuanty();
        final String mrp = myPlanModelClasses.get(position).getMrp();
        int color = myPlanModelClasses.get(position).getColor();
        final String quantity = myPlanModelClasses.get(position).getMainquantity();
        Picasso.get().load(img).into(holder.productimg);
        holder.productname.setText(name);
        holder.productquantity.setText(quant);
        holder.mainquantity.setText(quantity);
        holder.productprice.setText("\u20B9"+mrp);
        holder.cardView.setBackgroundResource(color);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "date=>"+myPlanModelClasses.get(position).getDate(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, AddPlaneActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("qunat",quantity);
                intent.putExtra("volume",quant);
                intent.putExtra("price",mrp);
                intent.putExtra("startdate",myPlanModelClasses.get(position).getDate());
                intent.putExtra("enddate","null");
                intent.putExtra("img",img);
                intent.putExtra("plantye","null");
                intent.putExtra("type","addplan");
                intent.putExtra("id",myPlanModelClasses.get(position).getO_id());
                intent.putExtra("pid",myPlanModelClasses.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myPlanModelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productimg;
        private CardView cardView;
        private TextView productname,productquantity,productprice,mainquantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.productimg);
            cardView =itemView.findViewById(R.id.cardview1);
            productname = itemView.findViewById(R.id.productname);
            productquantity = itemView.findViewById(R.id.productquantity);
            productprice = itemView.findViewById(R.id.mrp);
            mainquantity = itemView.findViewById(R.id.count);

        }
    }
}
