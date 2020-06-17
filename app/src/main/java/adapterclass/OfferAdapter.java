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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String img = stateNames.get(position).getProductimg();
        String productname = stateNames.get(position).getProductname();
        String offer = stateNames.get(position).getOffer();
        holder.offerprice.setText(offer+"%");
        Picasso.get().load(img).into(holder.productimg);
      // holder.cardView.setBackgroundResource(color);
        holder.cardView.setRadius(8);

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
