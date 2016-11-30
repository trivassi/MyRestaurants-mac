package com.epicodus.myrestaurants.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by T on 11/29/16.
 */
//recyclerView.adapter will populate the data into RecyclerView & converts Java object into individual list item View
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private Context mContext;

    public RestaurantListAdapter(Context context, ArrayList<Restaurant> restaurants) {
        mContext = context; // to create our ViewHolder
        mRestaurants = restaurants; //to calculate the item count, which informs the RecyclerView how many individual list              item Views it will need to recycle
    }


    //3 methods required by the RecyclerView.Adapter: onCreateViewHolder(), onBindViewHolder(), and getItemCount()


    @Override //inflates the layout, and creates the ViewHolder object required from the adapter
    public RestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        RestaurantViewHolder viewHolder = new RestaurantViewHolder(view);
        return viewHolder;
    }

    @Override //updates the contents of the ItemView to reflect the restaurant in the given position
    public void onBindViewHolder(RestaurantListAdapter.RestaurantViewHolder holder, int position) {
        holder.bindRestaurant(mRestaurants.get(position));
    }

    @Override // sets the number of items the adapter will display
    public int getItemCount() {
        return mRestaurants.size();
    }

    //create this as an inner-class(nested) here within our RestaurantListAdapter class
    // - same functionailty, limited scope, full access to class in which they are nested
    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.restaurantImageView) ImageView mRestaurantImageView; //from restaurant_list_item.xml
        @Bind(R.id.restaurantNameTextView) TextView mNameTextView;
        @Bind(R.id.categoryTextView) TextView mCategoryTextView;
        @Bind(R.id.ratingTextView) TextView mRatingTextView;
        private Context mContext;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindRestaurant(Restaurant restaurant) { //method will set the contents of the layout's TextViews to the             attributes of a specific restaurant
            Picasso.with(mContext).load(restaurant.getImageUrl()).into(mRestaurantImageView);//allow Picasso to handle the                  image loading from api
            mNameTextView.setText(restaurant.getName());
            mCategoryTextView.setText(restaurant.getCategories().get(0));
            mRatingTextView.setText("Rating: " + restaurant.getRating() + "/5");
        }
    }
}