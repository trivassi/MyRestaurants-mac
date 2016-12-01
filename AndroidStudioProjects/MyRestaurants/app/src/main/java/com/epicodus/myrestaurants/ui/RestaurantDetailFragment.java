package com.epicodus.myrestaurants.ui;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.models.Restaurant;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailFragment extends Fragment {
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

        Picasso.with(view.getContext()).load(mRestaurant.getImageUrl()).into(mImageLabel);

        mNameLabel.setText(mRestaurant.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", " , mRestaurant.getCategories()));
        mRatingLabel.setText(Double.toString(mRestaurant.getRating()) + "/5");
        mPhoneLabel.setText(mRestaurant.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", " , mRestaurant.getAddress()));

        return view;
// default      return inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
    }

}
