package com.epicodus.myrestaurants;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import java.io.IOException;


public class RestaurantsActivity extends AppCompatActivity {

    public static final String TAG = RestaurantsActivity.class.getSimpleName();


    @Bind(R.id.locationTextView)
    TextView mLocationTextView;
    @Bind(R.id.listView)
    ListView mListView;

    private String[] restaurants = new String[]{"Mi Mero Mole", "Mother's Bistro",
            "Life of Pie", "Screen Door", "Luc Lac", "Sweet Basil",
            "Slappy Cakes", "Equinox", "Miss Delta's", "Andina",
            "Lardo", "Portland City Grill", "Fat Head's Brewery",
            "Chipotle", "Subway"};

    //initialize
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);


        //find components with id
        ButterKnife.bind(this);

        //  we create a new ArrayAdapter providing three arguments:
        // 1. this, which represents the current context,
        // 2. android.R.layout.simple_list_item_1, which is a layout built into Android that provides standard appearance for text in a list,
        //3. an ArrayList called restaurants (not seen here)
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants);
        mListView.setAdapter(adapter);


        //display a toast containing the restaurant name when a list item is clicked
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String restaurant = ((TextView) view).getText().toString();
                Toast.makeText(RestaurantsActivity.this, restaurant, Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");    //Retrieve extended data from the intent.

        mLocationTextView.setText("Here are all the restaurants near: " + location);

        getRestaurants(location);
    }

    private void getRestaurants(String location) {
        //create a new instance of our YelpService,
        final YelpService yelpService = new YelpService();
        // and call the findRestaurants() method (takes 2 arguments)
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
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    //create a new string, jsonData and set it to the string of the response body
                    String jsonData = response.body().string();
                    // print the data to the logcat. If we catch any exceptions, we display their error messages.
                    Log.v(TAG, jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

    }


}