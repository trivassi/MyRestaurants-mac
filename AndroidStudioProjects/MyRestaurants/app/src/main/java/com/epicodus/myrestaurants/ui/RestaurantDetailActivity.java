package com.epicodus.myrestaurants.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.adapters.RestaurantPagerAdapter;
import com.epicodus.myrestaurants.models.Restaurant;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RestaurantDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private RestaurantPagerAdapter adadpterViewPager;
    ArrayList<Restaurant> mRestaurants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);

        mRestaurants = Parcels.unwrap(getIntent().getParcelableExtra("restaurants"));//pull out our ArrayList<Restaurant>                  Parcelable using the unwrap() method on our "restaurants" intent extra
        int startingPosition = getIntent().getIntExtra("position", 0);//also retrieve the position int included as an intent extra

        // create a new pager adapter called adapterViewPager, providing the mRestaurants as an argument
        adadpterViewPager = new RestaurantPagerAdapter(getSupportFragmentManager(), mRestaurants);
        mViewPager.setAdapter(adadpterViewPager);//instruct our ViewPager to use this new adapter
        mViewPager.setCurrentItem(startingPosition);//set the current item to the position of the item that was just clicked on
    }
}
