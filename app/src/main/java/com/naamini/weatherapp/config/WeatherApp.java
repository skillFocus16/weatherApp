package com.naamini.weatherapp.config;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Naamini Yonazi on 06/05/20
 */
public class WeatherApp extends Application {

    private static WeatherApp sInstance;

    static WeatherApp weatherApp;

    public static synchronized WeatherApp getInstance()
    {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static boolean isOnline() {
        boolean success=true;
        try {
            ConnectivityManager conMgr = (ConnectivityManager) weatherApp.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

            if(netInfo != null && netInfo.isConnected()){
                /*try {
                    URL url = new URL("https://google.com");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(10000);
                    connection.connect();
                    success = connection.getResponseCode() == 200;
                    success = true;
                    Log.i("successIs?: ", String.valueOf(success));

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                Log.i("successNet?: ", String.valueOf(success));

            }else {
                success = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;

    }

}
