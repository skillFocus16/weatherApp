package com.naamini.weatherapp.config;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.naamini.weatherapp.R;

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

    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_LONG).show();

            return false;
        }
        return true;
    }
}
