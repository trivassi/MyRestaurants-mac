package com.epicodus.myrestaurants.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.myrestaurants.models.Restaurant;
import com.epicodus.myrestaurants.ui.RestaurantDetailFragment;

import java.util.ArrayList;

/**
 * Created by T on 11/30/16.
 */

public class RestaurantPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Restaurant> mRestaurants;

    //constructor where we set the required FragmentManager and array list of restaurants we will be swiping through.
    public RestaurantPagerAdapter(FragmentManager fm, ArrayList<Restaurant> restaurants) {
        super(fm); //calling from parent class FragmentManager
        mRestaurants = restaurants;
    }

    @Override //returns an instance of the RestaurantDetailFragment for the restaurant in the position provided as an argument.
    public Fragment getItem(int position) {
        return RestaurantDetailFragment.newInstance(mRestaurants.get(position));
    }

    @Override //simply determines how many restaurants are in our Array List.
    // Lets our adapter know how many fragments it must create.
    public int getCount() {
        return mRestaurants.size();
    }

    @Override //updates the title that appears in the scrolling tabs at the top of the screen
    public CharSequence getPageTitle(int position) {
        return mRestaurants.get(position).getName();
    }


}
