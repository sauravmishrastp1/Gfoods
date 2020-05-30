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
import com.xpertwebtech.gfoods.MainActivity;
import com.xpertwebtech.gfoods.R;
import com.xpertwebtech.gfoods.RegisterActivity;

import java.util.ArrayList;

import modelclass.CityModel;
import modelclass.StateName;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private ArrayList<CityModel>stateNames;
    private Context context;

    public CityAdapter(ArrayList<CityModel> stateNames, Context context) {
        this.stateNames = stateNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.citylayout,parent,false);
        return new CityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String name = stateNames.get(position).getCityname();
        holder.statename.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, RegisterActivity.class);
                intent.putExtra("cityname",stateNames.get(position).getCityname());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
            statename = itemView.findViewById(R.id.cityname);
        }
    }
}
