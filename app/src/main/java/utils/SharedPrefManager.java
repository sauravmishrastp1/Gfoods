package utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.xpertwebtech.gfoods.LoginActivity;

import modelclass.User;


/**
 * Created by Sharad
 */

//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    public static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERID="keyUserId";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_PHONE = "keyphone";
    private static  final  String KEY_EMAL = "keyemail";
    private static  final  String KEY_CITY = "keycity";
    private static final String KEY_REFERCODE = "refercode";



    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERID, user.getId());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_EMAL,user.getEmail());
        editor.putString(KEY_CITY,user.getCity());
        editor.putString(KEY_REFERCODE,user.getRefercode());
        editor.commit();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAL, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_USERID,null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_EMAL,null),
                sharedPreferences.getString(KEY_CITY,null),
                sharedPreferences.getString(KEY_REFERCODE,null));

    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(mCtx, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }
}