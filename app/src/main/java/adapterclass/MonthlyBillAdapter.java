package adapterclass;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xpertwebtech.gfoods.R;
import com.xpertwebtech.gfoods.ViewBillActivity;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import modelclass.MonthlyBillModelclass;

public class MonthlyBillAdapter extends RecyclerView.Adapter<MonthlyBillAdapter.ViewHolder> {
    private ArrayList<MonthlyBillModelclass>monthlyBillModelclasses;
    private Context context;


    public MonthlyBillAdapter(ArrayList<MonthlyBillModelclass> monthlyBillModelclasses, Context context) {
        this.monthlyBillModelclasses = monthlyBillModelclasses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.monthlybilllayout,parent,false);
        return new MonthlyBillAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String productname = monthlyBillModelclasses.get(position).getProductname();
        String price = monthlyBillModelclasses.get(position).getPrice();
        String quant = monthlyBillModelclasses.get(position).getQuantity();
        String volume = monthlyBillModelclasses.get(position).getProductvolume();
        String date = monthlyBillModelclasses.get(position).getDate();
        holder.productname.setText(productname);
        holder.volume.setText(volume);
        holder.price.setText("\u20B9" +price);
        holder.quantity.setText(quant+"pkt");
        String input_date = date;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
        Date dt1 = null;
        try {
            dt1 = format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("EEEE");
        DateFormat format = new SimpleDateFormat("dd");
        String dateee= format.format(dt1);
        String finalDay = format2.format(dt1);
        holder.day.setText(finalDay);
        holder.date.setText(dateee);

       int totalPrice=0;
        for(int i = 0 ; i < monthlyBillModelclasses.size(); i++) {
            totalPrice +=Integer.parseInt( monthlyBillModelclasses.get(i).getPrice());

        }
        ViewBillActivity.totalbillmonth.setText("\u20B9" +totalPrice);
       // Toast.makeText(context, "price=>"+totalPrice, Toast.LENGTH_SHORT).show();
    }
    @Override
    public int getItemCount()
    {
        return monthlyBillModelclasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,day,productname,volume,quantity,price;
        public ViewHolder(@NonNull View itemView)
        { super(itemView);
        date = itemView.findViewById(R.id.date);
        day = itemView.findViewById(R.id.day);
        productname =itemView.findViewById(R.id.invoice);
        volume =itemView.findViewById(R.id.dateee);
        quantity =itemView.findViewById(R.id.status);
        price =itemView.findViewById(R.id.price);
        }

    }
}
