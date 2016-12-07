package com.epicodus.myrestaurants.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.adapters.FirebaseRestaurantViewHolder;
import com.epicodus.myrestaurants.models.Restaurant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedRestaurantListActivity extends AppCompatActivity {
    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants); //method to display the correct layout(activity_restaurants)
        ButterKnife.bind(this);

        //set the mRestaurantReference using the "restaurants" child node key from our Constants class
        mRestaurantReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RESTAURANTS);

        setUpFirebaseAdapter();

    }

    private void setUpFirebaseAdapter() {// takes the model class, the list item layout, the view holder, and the database reference as parameters.
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder>(Restaurant.class, R.layout.restaurant_list_item, FirebaseRestaurantViewHolder.class, mRestaurantReference) {

            @Override
            protected void populateViewHolder(FirebaseRestaurantViewHolder viewHolder, Restaurant model, int position) {
     // bindRestaurant() method on our viewHolder to set the appropriate text and image with the given restaurant.
                viewHolder.bindRestaurant(model);
            }
        };
        //set the adapter on our RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override //to clean up the FirebaseAdapter
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();//cleanup() on the adapter so that it can stop listening for changes in the Firebase database.
    }

}
