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

import modelclass.OfferModel;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    private ArrayList<OfferModel>stateNames;
    private Context context;

    public OfferAdapter(ArrayList<OfferModel> stateNames, Context context) {
        this.stateNames = stateNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offerlayout,parent,false);
        return new OfferAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String img = stateNames.get(position).getProductimg();
        String productname = stateNames.get(position).getProductname();
        String offer = stateNames.get(position).getOffer();
        holder.offerprice.setText(offer+"%");
        Picasso.get().load(img).into(holder.productimg);
      // holder.cardView.setBackgroundResource(color);
        holder.cardView.setRadius(8);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddPlaneActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",stateNames.get(position).getProductname());
                intent.putExtra("qunat","0");
                intent.putExtra("price","200");
                intent.putExtra("img",img);
                intent.putExtra("startdate","null");
                intent.putExtra("enddate","null");
                intent.putExtra("id","1");
                intent.putExtra("pid","2");
                intent.putExtra("plantye","null");
                intent.putExtra("type","myplan");
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return stateNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private ImageView productimg;
       private TextView offerprice;
       private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.productimg);
            cardView = itemView.findViewById(R.id.carddd);
            offerprice = itemView.findViewById(R.id.pp);
        }
    }
}
