package com.naamini.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.naamini.weatherapp.R;
import com.naamini.weatherapp.model.Region;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.naamini.weatherapp.config.Endpoints.get3Cities;
import static com.naamini.weatherapp.config.Endpoints.getoneCity;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_STEP = 3;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout layoutDots;
    private static String[] array_welcome_title = {"Hello", "Howdy", "Hi"};
    private ArrayList<Region> regionArrayList;
    private Region mRegion;
    private String regionName, desc;
    private int windSpd,hum,press;
    private double temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initComponents();
//        loadWeather();
//        new getWeather().execute(getoneCity+api_key);
        gettingWeather();
    }

    private void initComponents() {
        viewPager = findViewById(R.id.view_pager);
        layoutDots = findViewById(R.id.layoutDots);

        List<Region> regions = new ArrayList<>();
        for (int i = 0; i < array_welcome_title.length; i++) {
            Region obj = new Region();
//            obj.name = array_welcome_title[i];
//            obj.setName(array_welcome_title[i]);
            obj.setName(regionName);
            obj.setWindSpeed(windSpd);
            obj.setHumidity(hum);
            obj.setPressure(press);
            obj.setTemp(temp);
            obj.setMainDesc("yesss");
            regions.add(obj);
        }
        viewPagerAdapter = new ViewPagerAdapter(regions);

        viewPagerAdapter.setItems(regions);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        bottomProgressDots(viewPagerAdapter.getCount(),0);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    private void bottomProgressDots(int size, int current_index) {
        ImageView[] dots = new ImageView[size];

        layoutDots.removeAllViews();

        for (int i = 0; i< dots.length; i++){
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            layoutDots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.cyan_500), PorterDuff.Mode.SRC_IN);
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(viewPagerAdapter.getCount(),position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        List<Region> regions;

        public ViewPagerAdapter(List<Region> regions) {
            this.regions = regions;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            Region r = regions.get(position);

            View view = layoutInflater.inflate(R.layout.item_card, container, false);
            regions.get(position);

//            ((TextView) view.findViewById(R.id.title)).setText(array_welcome_title[position]);

//            RelativeLayout mainLayout = view.findViewById(R.id.mainLayout);
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mainLayout.setBackground(getResources().getDrawable(R.drawable.ic_launcher_background));
            }*/

            TextView temp = view.findViewById(R.id.tempDegrees);
            TextView desc = view.findViewById(R.id.desc);
            TextView regName = view.findViewById(R.id.regName);

            temp.setText(String.valueOf(regions.get(position).getTemp()));
            desc.setText(String.valueOf(regions.get(position).getMainDesc()));

            regName.setText(String.valueOf(regions.get(position).getName()));

            ImageView windIcon = view.findViewById(R.id.windIcon);
            ImageView humidityIcon = view.findViewById(R.id.humidityIcon);
            ImageView pressureIcon = view.findViewById(R.id.pressureIcon);

            TextView windTxt = view.findViewById(R.id.windTxt);
            TextView humidityTxt = view.findViewById(R.id.humidityTxt);
            TextView airPressTxt = view.findViewById(R.id.airPressTxt);

            Log.e("windSpeed?: ", String.valueOf(regions.get(position).getWindSpeed()));
            ((TextView) view.findViewById(R.id.windTxt)).setText(""+regions.get(position).getWindSpeed());
            ((TextView) view.findViewById(R.id.humidityTxt)).setText(""+regions.get(position).getHumidity());
            ((TextView) view.findViewById(R.id.airPressTxt)).setText(""+regions.get(position).getPressure());

            Glide.with(MainActivity.this)
                    .load(R.mipmap.ic_launcher)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(windIcon);

            Glide.with(MainActivity.this)
                    .load(R.mipmap.ic_launcher)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(humidityIcon);

            Glide.with(MainActivity.this)
                    .load(R.mipmap.ic_launcher)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(pressureIcon);
            container.addView(view);
            return view;
        }
        public Region getItem(int pos) {
            return regions.get(pos);
        }

        public void setItems(List<Region> regionList) {
            this.regions = regionList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
//            return about_title_array.length;
//            return regions.size();
            return regions.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void gettingWeather() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("fetching weather");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestParams params = new RequestParams();

        final int DEFAULT_TIMEOUT = 20 * 1000;
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
//        client.setBasicAuth(basicAuthUsername, basicAuthPassword);
        client.get(get3Cities, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
               /* if (!isOnline()) {
                    progressDialog.dismiss();
//                    showErrorSnackbar(getString(R.string.no_internet), getString(R.string.retry), 2);
                }*/
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    String response = new String(responseBody, "UTF-8");

                    Log.e("responseLoginMain?: ", response);

                    /*save user login data to shareprefs*/

                    getJSonObj(response);

                    progressDialog.dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    progressDialog.dismiss();

                    if (responseBody != null) {
                        String resuldata = new String(responseBody, "UTF-8");
                        Log.e("failureMainn", statusCode + resuldata);
//                        showErrorSnackbar(getString(R.string.incorrect_username_password), getString(R.string.retry), 2);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void getJSonObj(String response) {
        regionArrayList = new ArrayList<>();
        mRegion = new Region();
        try {
            JSONObject jObj = new JSONObject(response);

            JSONArray jsonArray = jObj.getJSONArray("list");
            for (int i=0; i<jObj.length(); i++) {
                JSONObject in = jsonArray.getJSONObject(i);

                JSONObject main = in.getJSONObject("main");
                mRegion.setTemp(main.getDouble("temp"));
                mRegion.setPressure(main.getInt("pressure"));
                mRegion.setHumidity(main.getInt("humidity"));

                /*JSONArray weatherArray = in.getJSONArray("weather");
                for (int j=0; j<weatherArray.length(); j++) {
                    JSONObject we = weatherArray.getJSONObject(i);
                    mRegion.setMainDesc(we.getString("description"));
                    Log.e("getting2??: ",in.getString("name"));

                }*/
                JSONObject wind = in.getJSONObject("wind");
                mRegion.setWindSpeed(wind.getInt("speed"));

                mRegion.setName(in.getString("name"));
            }

            regionArrayList.add(mRegion);

            viewPagerAdapter = new ViewPagerAdapter(regionArrayList);
            viewPager.setAdapter(viewPagerAdapter);
            viewPagerAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
                e.printStackTrace();
            }
        }

}
