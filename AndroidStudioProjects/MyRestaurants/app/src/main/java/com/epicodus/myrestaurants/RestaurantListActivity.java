package com.epicodus.myrestaurants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.epicodus.myrestaurants.adapters.RestaurantListAdapter;
import com.epicodus.myrestaurants.models.Restaurant;
import com.epicodus.myrestaurants.services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class RestaurantListActivity extends AppCompatActivity {
    public static final String TAG = RestaurantListActivity.class.getSimpleName();

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private RestaurantListAdapter mAdapter;

    public ArrayList<Restaurant> mRestaurants = new ArrayList<>();

// private String[] restaurants = new String[]{"Mi Mero Mole", "Mother's Bistro", "Chipotle", "Subway"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

        //list of items

        //toast

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");    //Retrieve data from the intent.

//  SET TEXT      mLocationTextView.setText("Here are all the restaurants near: " + location);

        getRestaurants(location);
    }

    private void getRestaurants(String location) {
        //create a new instance of our YelpService,
        final YelpService yelpService = new YelpService();


        //call the findRestaurants() method (takes 2 arguments)
        // create a new empty Callback to provide as the second argument
        yelpService.findRestaurants(location, new Callback() {
            //Our callback will have two methods to override: onFailure() and onResponse()

            //onFailure() is triggered when our request fails (if we create a bad URL, for example)
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            //onResponse() is triggered when the request is successful.
            @Override
            public void onResponse(Call call, Response response) {
                mRestaurants = yelpService.processResults(response);

                RestaurantListActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//get names from API CALL below
                        mAdapter = new RestaurantListAdapter(getApplicationContext(), mRestaurants);
                        mRecyclerView.setAdapter(mAdapter); // set RestaurantListAdapter as its new adapter
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RestaurantListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager); //need to create and set an instance of the                                      LayoutManager the RecyclerView requires. We'll use the LinearLayoutManager
                        mRecyclerView.setHasFixedSize(true); // informs mRecyclerView that its width & height should always                             remain the same
                    }
                });
            }

//previous onresponse

        });

    }

}






                    //get names from API CALL and display them

//                        String[] restaurantNames = new String[mRestaurants.size()];
//for (int i = 0; i < restaurantNames.length; i++) {
//        restaurantNames[i] = mRestaurants.get(i).getName();
//        }
//
//        ArrayAdapter adapter = new ArrayAdapter(RestaurantListActivity.this, android.R.layout                                              .simple_list_item_1, restaurantNames);
//        mListView.setAdapter(adapter);
//
//        for (Restaurant restaurant : mRestaurants) {
//        Log.d(TAG, "Name: " + restaurant.getName());
//        Log.d(TAG, "Phone: " + restaurant.getPhone());
//        Log.d(TAG, "Website: " + restaurant.getWebsite());
//        Log.d(TAG, "Image url: " + restaurant.getImageUrl());
//        Log.d(TAG, "Rating: " + Double.toString(restaurant.getRating()));
//        Log.d(TAG, "Address: " + android.text.TextUtils.join(", ", restaurant.getAddress()));
//        //shortcut to join Lists or ArrayLists in Android.
//        Log.d(TAG, "Categories: " + restaurant.getCategories().toString());
//        }



// if hard coding an array of names or items

//  we create a new ArrayAdapter providing three arguments:
// 1. this, which represents the current context,
// 2. android.R.layout.simple_list_item_1, which is a layout built into Android that provides standard appearance               for text in a list,
//3. an ArrayList called restaurants (not seen here)
//                  ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants);
//                  mListView.setAdapter(adapter);


//display a toast containing the restaurant name when a list item is clicked
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String restaurant = ((TextView) view).getText().toString();
//                Toast.makeText(RestaurantListActivity.this, restaurant, Toast.LENGTH_LONG).show();
//            }
//        });






//      @Override public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    //create a new string, jsonData and set it to the string of the response body
//                    String jsonData = response.body().string();
//
//                    if (response.isSuccessful()) {
//                        Log.v(TAG, jsonData);// print the data to the logcat. If we catch any exceptions, we display their                          error messages.
//
//                        //trigger  callback, and collect its return value in a member variable called mRestaurants
//                        mRestaurants = yelpService.processResults(response);
//                        // method to parse JSON, create restaurant objects, and return an array of each new object
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
