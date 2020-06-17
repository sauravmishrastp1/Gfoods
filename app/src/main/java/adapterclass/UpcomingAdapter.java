package adapterclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xpertwebtech.gfoods.R;

import java.util.ArrayList;

import modelclass.UpcomingModel;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {
    private ArrayList<UpcomingModel>myPlanModelClasses;
    private Context context;

    public UpcomingAdapter(ArrayList<UpcomingModel> myPlanModelClasses, Context context) {
        this.myPlanModelClasses = myPlanModelClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.upcominglorderlayout,parent,false);
        return new UpcomingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String img = myPlanModelClasses.get(position).getImaage();
        final String name = myPlanModelClasses.get(position).getProductname();
        final String quant = myPlanModelClasses.get(position).getQuant();
        Picasso.get().load(img).into(holder.productimg);
        holder.productname.setText(name);
        holder.productquantity.setText(quant);


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
            cardView = itemView.findViewById(R.id.cardview1);
            productname = itemView.findViewById(R.id.productname);
            productquantity = itemView.findViewById(R.id.count);
            productprice = itemView.findViewById(R.id.mrp);
        }
    }
}
