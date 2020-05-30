package adapterclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xpertwebtech.gfoods.AddPlaneActivity;
import com.xpertwebtech.gfoods.R;

import java.util.ArrayList;

import modelclass.Daysmodelcalss;

public class WeekDaysAdapter extends RecyclerView.Adapter<WeekDaysAdapter.ViewHolder> {
    public WeekDaysAdapter(ArrayList<Daysmodelcalss> daysmodelcalsses, Context context) {
        this.daysmodelcalsses = daysmodelcalsses;
        this.context = context;
    }

    public static ArrayList<Daysmodelcalss>daysmodelcalsses;
    public static ArrayList<Daysmodelcalss>newlistday = new ArrayList<>();
    private Context context;




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.weekdayslayout,parent,false);
        return new WeekDaysAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
     String daysname = daysmodelcalsses.get(position).getDays();
     holder.textView.setText(daysname);

        final int pos = position;
        final Daysmodelcalss items = daysmodelcalsses.get(pos);
        holder.checkBox.setChecked(Boolean.parseBoolean(daysmodelcalsses.get(position).getDays()));
        holder.checkBox.setChecked(daysmodelcalsses.get(position).isIsselected());
        holder.checkBox.setTag(position);


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Integer pos = (Integer) holder.checkBox.getTag();
               // Toast.makeText(context, daysmodelcalsses.get(pos).getDays() + " selected!", Toast.LENGTH_SHORT).show();

                if (daysmodelcalsses.get(pos).isIsselected()) {
                    daysmodelcalsses.get(pos).setIsselected(false);
                    newlistday.remove(pos);

                } else {
                    daysmodelcalsses.get(pos).setIsselected(true);
                    Daysmodelcalss model = new Daysmodelcalss();
                    model.setDays(daysmodelcalsses.get(position).getDays());
                    newlistday.add(model);

                }
            }
        });


    }
    public ArrayList<Daysmodelcalss> getAllData () {
        return daysmodelcalsses;
    }
    public void setCheckBox ( int position){


    }

    @Override
    public int getItemCount() {
        return daysmodelcalsses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.days);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
