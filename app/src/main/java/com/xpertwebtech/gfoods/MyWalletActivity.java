package com.xpertwebtech.gfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import adapterclass.MyWalletLayout;
import adapterclass.StateAdapter;
import modelclass.MyWalletModel;
import modelclass.StateName;

public class MyWalletActivity extends AppCompatActivity {
    private RecyclerView recyclerViewl;
    private ArrayList<MyWalletModel>myWalletModels=new ArrayList<>();
    private EditText entramount;
    private TextView blanceavalb;
    private Button addmonry;
    private RelativeLayout backpress;
    private TextView rssymbole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        recyclerViewl = findViewById(R.id.recyclerviewwallet);
        entramount = findViewById(R.id.enteramount);
        backpress = findViewById(R.id.backpresslayout);
        rssymbole = findViewById(R.id.rssymbole);
        rssymbole.setText("\u20B9");
        addmonry = findViewById(R.id.addmoneybtn);
        addmonry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWalletActivity.this,AddMoneyLyoutActivity.class);
                startActivity(intent);
            }
        });
        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            String money = bundle.getString("money");
            entramount.setText(money);


        }else {
            entramount.setHint("Enter Amount");
        }

        blanceavalb = findViewById(R.id.blance);
        blanceavalb.setText("\u20B9"+"200");
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWalletActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        statedata();
    }

    private void statedata()
    {
        myWalletModels.add(new MyWalletModel("Pay"+" "+"\u20B9"+"50","Rs.25 Cashback"));
        myWalletModels.add(new MyWalletModel("Pay"+" "+"\u20B9"+"50","Rs.25 Cashback"));
        myWalletModels.add(new MyWalletModel("Pay"+" "+"\u20B9"+"50","Rs.25 Cashback"));
        myWalletModels.add(new MyWalletModel("Pay"+" "+"\u20B9"+"50","Rs.25 Cashback"));


        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(MyWalletActivity.this, 2);

        recyclerViewl.setLayoutManager(gridLayoutManager1);
        MyWalletLayout gridProductAdapter = new MyWalletLayout(myWalletModels, MyWalletActivity.this);
        recyclerViewl.setAdapter(gridProductAdapter);
        gridProductAdapter.notifyDataSetChanged();
    }
}
