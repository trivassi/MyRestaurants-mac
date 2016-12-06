package com.epicodus.myrestaurants.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mSharedPreferences; //create member variables to store reference to shared preferences tool itself
    private SharedPreferences.Editor mEditor; //dedicated tool we must use to edit them

    @Bind(R.id.findRestaurantsButton) Button mFindRestaurantsButton;
    @Bind(R.id.locationEditText) EditText mLocationEditText;
//    @Bind(R.id.appNameTextView) TextView mAppNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

//        Typeface ostrichFont = Typeface.createFromAsset(getAssets(), "fonts/ostrich-regular.ttf");
//        mAppNameTextView.setTypeface(ostrichFont);


        mFindRestaurantsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == mFindRestaurantsButton) {
            String location = mLocationEditText.getText().toString();
            if (!(location).equals("")) {
                addToSharedPreferences(location);
            }
// a new instance of the Intent class
//two parameters: The current context, and the Activity class we want to start
            Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
//            intent.putExtra("location", location);
            startActivity(intent);

            //Toast.makeText(MainActivity.this, "Hello World!", Toast.LENGTH_LONG).show();
//A toast is a simple pop up message...three parameters; a context, a message, and a duration
        }

    }

    //which takes the user-inputted zip code as an argument
    private void addToSharedPreferences(String location) {
        //calls upon the editor to write this information to shared preferences.
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
        //shared preference data must be in key-value pairs
        // 1. key we've stored in our Constants file called PREFERENCES_LOCATION_KEY
        // 2.zip code value we've passed in as an argument, location.
        // Finally, we call apply() to save this information
    }

}