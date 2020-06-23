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

import com.xpertwebtech.gfoods.ChooseCityActivity;
import com.xpertwebtech.gfoods.R;

import java.util.ArrayList;

import modelclass.StateName;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {
    private ArrayList<StateName>stateNames;
    private Context context;

    public StateAdapter(ArrayList<StateName> stateNames, Context context) {
        this.stateNames = stateNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.statelayout,parent,false);
        return new StateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        int img = stateNames.get(position).getImg();
        final String name = stateNames.get(position).getStatename();
        holder.statename.setText(name);

        holder.stateimg.setImageResource(img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChooseCityActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",stateNames.get(position).getStatename());
                intent.putExtra("id",stateNames.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stateNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView stateimg;
        private TextView statename;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stateimg = itemView.findViewById(R.id.stateimg);
            statename = itemView.findViewById(R.id.statename);
        }
    }
}
