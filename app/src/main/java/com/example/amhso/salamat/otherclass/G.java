package com.example.amhso.salamat.otherclass;



import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class G extends Application {

    public static Context context;
    public static Activity activity;

    public static String urlserver="http://salamatpay.com/android/";
    public static final String urlimage = "http://salamatpay.com/uploads/";
    public static final String Service = "ws://138.201.195.173:9000";



//    public static String urlserver="http://192.168.1.7/android/";
//    public static final String urlimage = "http://192.168.1.7/uploads/";
//    public static final String Service = "ws://192.168.1.7:9000";

    public static Boolean firstlunch=true;











    @Override
    public void onCreate() {

        context = getApplicationContext();
        super.onCreate();


    }






    public static boolean checknet() {
        ConnectivityManager conMgr;
        conMgr = (ConnectivityManager) G.activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            return   true;

        } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            return false;
        }
        return false;
    }






}