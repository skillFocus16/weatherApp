package com.naamini.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.naamini.weatherapp.config.Endpoints.getThreeCitiesFullUrl;
import static com.naamini.weatherapp.config.Endpoints.weatherIconUrl;
import static com.naamini.weatherapp.config.WeatherApp.isOnline;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout layoutDots;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(layoutDots, viewPagerAdapter.getCount(), position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private CardView noNetCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            initComponents();
            gettingWeather();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        noNetCardView = findViewById(R.id.noNet);
        viewPager = findViewById(R.id.view_pager);
        layoutDots = findViewById(R.id.layoutDots);

        ArrayList<Region> regions = new ArrayList<>();

        viewPagerAdapter = new ViewPagerAdapter(regions);

        viewPagerAdapter.setItems(regions);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        bottomProgressDots(layoutDots, viewPagerAdapter.getCount(), 0);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        noNetCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gettingWeather();
            }
        });
    }

    private void bottomProgressDots(LinearLayout layoutDots, int size, int current_index) {
        ImageView[] dots = new ImageView[size];

        layoutDots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
            layoutDots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.cyan_500), PorterDuff.Mode.SRC_IN);
        }
    }

    private void gettingWeather() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getString(R.string.fetching_weather));
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestParams params = new RequestParams();

        final int DEFAULT_TIMEOUT = 20 * 1000;
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get(getThreeCitiesFullUrl, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                if (!isOnline(getApplicationContext())) {
                    noNetCardView.setVisibility(View.VISIBLE);
                }else {
                    noNetCardView.setVisibility(View.GONE);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody, StandardCharsets.UTF_8);

                    JSONObject jObj = new JSONObject(response);
                    getJSonObj(jObj);
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
                        String resuldata = new String(responseBody, StandardCharsets.UTF_8);
                        Toast.makeText(MainActivity.this, R.string.error_occured, Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void getJSonObj(JSONObject response) {
        try {
            JSONArray arrList = response.getJSONArray("list");

            ArrayList<Region> regionArrayList = new ArrayList<>();

            for (int i = 0; i < arrList.length(); i++) {
                JSONObject in = arrList.getJSONObject(i);
                Region mRegion = new Region();

                JSONObject main = in.getJSONObject("main");
                mRegion.setTemp(main.getDouble("temp"));
                mRegion.setPressure(main.getInt("pressure"));
                mRegion.setHumidity(main.getInt("humidity"));

                JSONObject wind = in.getJSONObject("wind");
                mRegion.setWindSpeed(wind.getDouble("speed"));
                mRegion.setName(in.getString("name"));

                JSONArray weatherArray = in.getJSONArray("weather");
                for (int j = 0; j < weatherArray.length(); j++) {
                    JSONObject we = weatherArray.getJSONObject(j);
                    mRegion.setMainDesc(we.getString("description"));
                    mRegion.setWeatherId(we.getInt("id"));
                    mRegion.setIcon(we.getString("icon"));
                }
                regionArrayList.add(mRegion);
            }
            viewPagerAdapter = new ViewPagerAdapter(regionArrayList);
            viewPager.setAdapter(viewPagerAdapter);
            viewPagerAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {
        ArrayList<Region> regions;
        private LayoutInflater layoutInflater;

        public ViewPagerAdapter(ArrayList<Region> regions) {
            this.regions = regions;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_card, container, false);
            regions.get(position);

            RelativeLayout mainLayout = view.findViewById(R.id.mainLayout);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (regions.get(position).getIcon().contains("n")) {
                    mainLayout.setBackground(getResources().getDrawable(R.drawable.night));
                } else {
                    mainLayout.setBackground(getResources().getDrawable(R.drawable.day));
                }
            }
            TextView temp = view.findViewById(R.id.tempDegrees);
            TextView desc = view.findViewById(R.id.desc);
            TextView regName = view.findViewById(R.id.regName);

            temp.setText(String.valueOf(regions.get(position).getTemp()) + (char) 0x00B0);
            desc.setText(String.valueOf(regions.get(position).getMainDesc()));

            regName.setText(String.valueOf(regions.get(position).getName()));

            ImageView icon = view.findViewById(R.id.icon);
            ImageView windIcon = view.findViewById(R.id.windIcon);
            ImageView humidityIcon = view.findViewById(R.id.humidityIcon);
            ImageView pressureIcon = view.findViewById(R.id.pressureIcon);

            ((TextView) view.findViewById(R.id.windTxt)).setText(String.format("%s%s", regions.get(position).getWindSpeed(), getString(R.string.windUnit)));
            ((TextView) view.findViewById(R.id.humidityTxt)).setText("" + regions.get(position).getHumidity() + getString(R.string.humUnit));
            ((TextView) view.findViewById(R.id.airPressTxt)).setText("" + regions.get(position).getPressure() + getString(R.string.pressUnit));

            Glide.with(MainActivity.this)
                    .load(Uri.parse(weatherIconUrl + regions.get(position).getIcon() + getString(R.string.png)
                    ))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .apply(RequestOptions.overrideOf(500, 500))
                    .into(icon);

            Glide.with(MainActivity.this)
                    .load(R.drawable.ic_wind)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(windIcon);

            Glide.with(MainActivity.this)
                    .load(R.drawable.ic_humidity)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(humidityIcon);

            Glide.with(MainActivity.this)
                    .load(R.drawable.ic_pressure)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(pressureIcon);

            container.addView(view);
            return view;
        }

        public Region getItem(int pos) {
            return regions.get(pos);
        }

        public void setItems(ArrayList<Region> regionList) {
            this.regions = regionList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return regions.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
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
}