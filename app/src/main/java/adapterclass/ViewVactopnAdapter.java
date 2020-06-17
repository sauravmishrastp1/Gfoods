package adapterclass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xpertwebtech.gfoods.EndVavationActivity;
import com.xpertwebtech.gfoods.R;

import java.util.ArrayList;

import modelclass.Vactionmodel;

public class ViewVactopnAdapter extends RecyclerView.Adapter<ViewVactopnAdapter.ViewHolder> {
    private ArrayList<Vactionmodel>vactionmodels;
    private Context context;

    public ViewVactopnAdapter(ArrayList<Vactionmodel> vactionmodels, Context context) {
        this.vactionmodels = vactionmodels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewVactopnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vacationlayouttt,parent,false);
        return new ViewVactopnAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewVactopnAdapter.ViewHolder holder, final int position) {
    final String startdate = vactionmodels.get(position).getStartdate();
    final String enddate = vactionmodels.get(position).getEnddate();
    holder.startdate.setText(startdate);
    holder.enddate.setText(enddate);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, EndVavationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("start",startdate);
            intent.putExtra("end",enddate);
            intent.putExtra("id",vactionmodels.get(position).getId());
            context.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return vactionmodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView startdate,enddate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startdate = itemView.findViewById(R.id.startdateee);
            enddate = itemView.findViewById(R.id.enddatee);
        }
    }
}
