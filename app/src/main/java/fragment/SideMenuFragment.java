package fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;


import com.xpertwebtech.gfoods.DeliveryBoyActivityLogin;
import com.xpertwebtech.gfoods.EarnAndRefferActivity;
import com.xpertwebtech.gfoods.HelpActivity;
import com.xpertwebtech.gfoods.LoginActivity;
import com.xpertwebtech.gfoods.MainActivity;
import com.xpertwebtech.gfoods.MonthlyWiseBillActivity;
import com.xpertwebtech.gfoods.MyProfileActivty;
import com.xpertwebtech.gfoods.MyWalletActivity;
import com.xpertwebtech.gfoods.OffersActivity;
import com.xpertwebtech.gfoods.OrderHitoryActivity;
import com.xpertwebtech.gfoods.R;
import com.xpertwebtech.gfoods.ViewBillActivity;
import com.xpertwebtech.gfoods.ViewVacationActivtiy;

import utils.SharedPrefManager;


public class SideMenuFragment extends BaseFragment  {


    private  View rootView;
    private View shareearnn,myvalletview,addvacation,profileview,
            offrerlayout,helpview,addplaneview,viewbilllayout,logout,mycleanderview,logivueww,deleveryboylayout,orderhistorylayout;


    @Override
    protected int getFragmentLayout() {
        return R.layout.ly_sidebar;
    }

    public SideMenuFragment() {

    }

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ly_sidebar, container, false);
     shareearnn= rootView.findViewById(R.id.sharearn);
     myvalletview = rootView.findViewById(R.id.mywallet);
     offrerlayout = rootView.findViewById(R.id.offerlayoutview);
     deleveryboylayout = rootView.findViewById(R.id.deliveryboy);
     addplaneview = rootView.findViewById(R.id.addplaneview);
     helpview = rootView.findViewById(R.id.helpview);
     orderhistorylayout=rootView.findViewById(R.id.orderhistory);
     logivueww = rootView.findViewById(R.id.LOGIN);
     viewbilllayout = rootView.findViewById(R.id.viewbill);
     logout = rootView.findViewById(R.id.logoutview);
     profileview = rootView.findViewById(R.id.profileviewlayout);
     mycleanderview = rootView.findViewById(R.id.cleanderview);

     String email = SharedPrefManager.getInstance(getContext()).getUser().getEmail();
     if(email.equals("null")){
         profileview.setVisibility(View.GONE);
         logout.setVisibility(View.GONE);
         logivueww.setVisibility(View.VISIBLE);
         deleveryboylayout.setVisibility(View.VISIBLE);
     }
        orderhistorylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderHitoryActivity.class);
                startActivity(intent);
            }
        });
     deleveryboylayout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), DeliveryBoyActivityLogin.class);
             startActivity(intent);
         }
     });
     logivueww.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), LoginActivity.class);
             startActivity(intent);
         }
     });
     mycleanderview.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), MainActivity.class);
             startActivity(intent);
         }
     });
     logout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {


                 AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                 builder1.setMessage("Are You Sure !! to Logout ?");
                 builder1.setCancelable(true);

                 builder1.setPositiveButton(
                         "Yes",
                         new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {

                            SharedPrefManager.getInstance(getContext()).logout();
                             }
                         });

                 builder1.setNegativeButton(
                         "No",
                         new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                                 dialog.cancel();
                             }
                         });

                 AlertDialog alert11 = builder1.create();
                 alert11.show();
             }


     });

     viewbilllayout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), ViewBillActivity.class);
             startActivity(intent);
         }
     });
     addplaneview.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), MonthlyWiseBillActivity.class);
             startActivity(intent);
         }
     });
     helpview.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), HelpActivity.class);
             startActivity(intent);
         }
     });

     offrerlayout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), OffersActivity.class);
             startActivity(intent);
         }
     });
     profileview.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), MyProfileActivty.class);
             startActivity(intent);
         }
     });
     addvacation = rootView.findViewById(R.id.addvactionview);
     addvacation.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), ViewVacationActivtiy.class);
             startActivity(intent);
         }
     });
     myvalletview.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), MyWalletActivity.class);
             startActivity(intent);
         }
     });
     shareearnn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), EarnAndRefferActivity.class);
             startActivity(intent);
         }
     });

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
      // ApiManager.getProfile(getActivity(), String.valueOf(UserInfo.getUserId(getActivity())),this);
    }


}





