package com.epicodus.myrestaurants.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.R;
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
    private SharedPreferences mSharedPreferences; //call the dedicated PreferenceManager to access shared preferences
    private SharedPreferences.Editor mEditor; //need access to the Editor to stash this new zip code in SharedPreferences
    private String mRecentAddress;

//    public static final String TAG = RestaurantListActivity.class.getSimpleName();

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

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this); //retrieve our shared preferences from the preference manager
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);//pull data from it by calling getString() and providing the key that corresponds to the data we'd like to retrieve. also pass in the default value null
        // The default value will be returned if the getString() method is unable to find a value that corresponds to the key we provided.
        if (mRecentAddress != null) {
            // we know we have a zip code saved, and we pass that zip code to our getRestaurants() method. As we know, the getRestaurants() method then calls the Yelp API and returns restaurants near that location
            getRestaurants(mRecentAddress);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this); // inflate and bind our Views

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search); //to retrieve a user’s search from the SearchView, we must grab the action_search menu item from our new layout
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
  //gather the input after the user has submitted something (and not every time they type a single character into the field)
//place logic into onQueryTextSubmit
            @Override
            public boolean onQueryTextSubmit(String query) { //run automatically when the user submits a query into our SearchView
                addToSharedPreferences(query); //save the zip code the user searches
                getRestaurants(query); //begin executing a request to the Yelp API to return restaurants
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { //whenever any changes to the SearchView contents occur
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item); //ensures that all functionality from the parent class (referred to here as super) will still apply despite us manually overriding portions of the menu's functionality.
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

    private void addToSharedPreferences(String location) { //responsible for writing data to Shared Preferences
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
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
