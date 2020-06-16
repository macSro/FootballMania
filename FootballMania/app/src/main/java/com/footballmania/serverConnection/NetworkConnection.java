package com.footballmania.serverConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnection {

    private NetworkConnection(){}


    public static boolean networkAvailable(Context context){
        boolean networkAvailable = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if(activeNetwork != null){
            switch(activeNetwork.getType()){
                case ConnectivityManager.TYPE_WIFI:
                    networkAvailable = true;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    networkAvailable = true;
                    break;
                default:
                    break;
            }
        } else {
            //not connected to Network
        }
        return networkAvailable;
    }
}
