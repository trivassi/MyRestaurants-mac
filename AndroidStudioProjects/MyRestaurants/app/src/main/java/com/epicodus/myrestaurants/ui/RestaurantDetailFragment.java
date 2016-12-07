package com.epicodus.myrestaurants.ui;


import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.models.Restaurant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailFragment extends Fragment implements View.OnClickListener {
    //create final variables to hold our values that correspond to the maximum width & height we'd like our images resized to
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    @Bind(R.id.restaurantImageView) ImageView mImageLabel;
    @Bind (R.id.restaurantNameTextView) TextView mNameLabel;
    @Bind (R.id.cuisineTextView) TextView mCategoriesLabel;
    @Bind(R.id.ratingTextView) TextView mRatingLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.saveRestaurantButton) TextView mSaveRestaurantButton;

    private Restaurant mRestaurant;

    //used instead of a constructor and returns a new instance of our RestaurantDetailFragment
    public static RestaurantDetailFragment newInstance(Restaurant restaurant) {
        // Required empty public constructor
        RestaurantDetailFragment restaurantDetailFragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("restaurant", Parcels.wrap(restaurant)); //use the Parceler library to add our restaurant object to our          bundle
        restaurantDetailFragment.setArguments(args);  //set the bundle as the argument for our new RestaurantDetailFragment.
        return restaurantDetailFragment; //allows us to access necessary data when a new instance of our fragment is created
    }

    @Override //called when the fragment is created
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestaurant = Parcels.unwrap(getArguments().getParcelable("restaurant")); //unwrap our restaurant object from the                       arguments we added in the newInstance() method
    }

    @Override //this restaurant object is then used to set our ImageView and TextViews
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//          Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext())
                .load(mRestaurant.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT) // method built into Picasso, we scale the image down
                .centerCrop() // call the centerCrop() method so that the image scales properly when filling the requested bounds.
                .into(mImageLabel);

        mNameLabel.setText(mRestaurant.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", " , mRestaurant.getCategories()));
        mRatingLabel.setText(Double.toString(mRestaurant.getRating()) + "/5");
        mPhoneLabel.setText(mRestaurant.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", " , mRestaurant.getAddress()));

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);

        mSaveRestaurantButton.setOnClickListener(this); //attach a click listener to our "Save Restaurants" button

        return view;
// default      return inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
    }

    @Override
    public void onClick(View v) {

        if (v == mWebsiteLabel) {        //implicit intents
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mRestaurant.getWebsite()));
            startActivity(webIntent);
        }
        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mRestaurant.getPhone()));
            startActivity(phoneIntent);
        }
        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mRestaurant.getLatitude()
                            + "," + mRestaurant.getLongitude()
                            + "?q=(" + mRestaurant.getName() + ")"));
            startActivity(mapIntent);
        }

        if (v == mSaveRestaurantButton) { //add code to our onClick() method that will save a restaurant to our restaurants node in Firebase when the button is selected
            DatabaseReference restaurantRef = FirebaseDatabase //create new DatabaseReference object
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RESTAURANTS); //passing in the key for our restaurants node.
            restaurantRef.push().setValue(mRestaurant); //passing in our restaurant object as an argument,
            // to create a node for the selected restaurant with a unique push id.
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }


}
