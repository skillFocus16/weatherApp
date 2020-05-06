package com.naamini.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
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
import com.naamini.weatherapp.R;
import com.naamini.weatherapp.model.Region;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_STEP = 3;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout layoutDots;
    private static String[] array_welcome_title = {"Hello", "Howdy", "Hi", "Morning",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initComponents();
    }

    private void initComponents() {
        viewPager = findViewById(R.id.view_pager);
        layoutDots = findViewById(R.id.layoutDots);

        viewPagerAdapter = new ViewPagerAdapter();

        List<Region> regions = new ArrayList<>();
        for (int i = 0; i < array_welcome_title.length; i++) {
            Region obj = new Region();
            obj.name = array_welcome_title[i];
            regions.add(obj);
        }
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

        public ViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final Region region = regions.get(position);

            View view = layoutInflater.inflate(R.layout.item_card, container, false);

            ((TextView) view.findViewById(R.id.title)).setText(array_welcome_title[position]);

            RelativeLayout mainLayout = view.findViewById(R.id.mainLayout);
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mainLayout.setBackground(getResources().getDrawable(R.drawable.ic_launcher_background));
            }*/

            TextView temp = view.findViewById(R.id.tempDegrees);
            TextView desc = view.findViewById(R.id.desc);
            TextView regName = view.findViewById(R.id.regName);

            temp.setText(String.valueOf(region.temp));
            desc.setText(String.valueOf(region.mainDesc));
            regName.setText(String.valueOf(region.name));

            ImageView windIcon = view.findViewById(R.id.windIcon);
            ImageView humidityIcon = view.findViewById(R.id.humidityIcon);
            ImageView pressureIcon = view.findViewById(R.id.pressureIcon);

            /*TextView windTxt = view.findViewById(R.id.windTxt);
            TextView humidityTxt = view.findViewById(R.id.humidityTxt);
            TextView airPressTxt = view.findViewById(R.id.airPressTxt);*/

            ((TextView) view.findViewById(R.id.windTxt)).setText(String.valueOf(region.windSpeed));
            ((TextView) view.findViewById(R.id.humidityTxt)).setText("55%");
            ((TextView) view.findViewById(R.id.airPressTxt)).setText("1000pkj");

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

//            ((TextView) view.findViewById(R.id.title)).setText(region.name);

           /* ((TextView) view.findViewById(R.id.description)).setText(about_description_array[position]);
            ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);
            ((ImageView) view.findViewById(R.id.image_bg)).setImageResource(bg_images_array[position]);

            btnNext = (Button) view.findViewById(R.id.btn_next);
*/
           /* if (position == about_title_array.length - 1) {
                btnNext.setText("Get Started");
            } else {
                btnNext.setText("Next");
            }

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int current = viewPager.getCurrentItem() + 1;
                    if (current < MAX_STEP) {
                        // move to next screen
                        viewPager.setCurrentItem(current);
                    } else {
                        finish();
                    }
                }
            });*/

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
            return 3;
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
