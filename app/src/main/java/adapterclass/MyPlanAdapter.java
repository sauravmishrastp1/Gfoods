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

import com.xpertwebtech.gfoods.MainActivity;
import com.xpertwebtech.gfoods.R;

import java.util.ArrayList;

import modelclass.CityModel;
import modelclass.MyPlanModelClass;

public class MyPlanAdapter extends RecyclerView.Adapter<MyPlanAdapter.ViewHolder> {
    private ArrayList<MyPlanModelClass>myPlanModelClasses;
    private Context context;

    public MyPlanAdapter(ArrayList<MyPlanModelClass> myPlanModelClasses, Context context) {
        this.myPlanModelClasses = myPlanModelClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.whatulookingfor,parent,false);
        return new MyPlanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
       int img = myPlanModelClasses.get(position).getImg();
       final String name = myPlanModelClasses.get(position).getProductname();
      final int color = myPlanModelClasses.get(position).getColor();

       holder.cardView.setBackgroundResource(color);
       holder.productname.setText(name);
     //  holder.productquantity.setText(quant);
      // holder.productprice.setText(mrp);
        if(name.equals("Dairy Products")){
            holder.productimg.setImageResource(R.drawable.daryproduct);
            holder.cardView.setBackgroundResource(R.color.dary);
        }
        if(name.equals("Frozen Product")){
            holder.productimg.setImageResource(R.drawable.frozenproducta);
            holder.cardView.setBackgroundResource(R.color.frozen);
        }
        if(name.equals("Cookies Products")){
            holder.productimg.setImageResource(R.drawable.cookies);
            holder.cardView.setBackgroundResource(R.color.cookies);
        }
        if(name.equals("Bakery Products")){
            holder.productimg.setImageResource(R.drawable.bakery);
            holder.cardView.setBackgroundResource(R.color.bakery);
        }
        if(name.equals("Sweet Product")){
            holder.productimg.setImageResource(R.drawable.breakfast);
            holder.cardView.setBackgroundResource(R.color.sweets);
        }
        if(name.equals("Morning Breakfast")){
            holder.productimg.setImageResource(R.drawable.breakfast);
            holder.cardView.setBackgroundResource(R.color.morning);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("productname",name);
                intent.putExtra("color",color);
                intent.putExtra("id",myPlanModelClasses.get(position).getId());
                intent.putExtra("type","1");
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
       private TextView productname,productquantity,productprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.productimg);
            productname = itemView.findViewById(R.id.productname);
            //productquantity = itemView.findViewById(R.id.productquantity);
           // productprice = itemView.findViewById(R.id.mrp);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
