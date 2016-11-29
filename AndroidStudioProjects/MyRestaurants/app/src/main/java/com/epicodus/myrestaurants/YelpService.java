package com.epicodus.myrestaurants;

/**
 * Created by T on 11/26/16.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class YelpService {

    public static void findRestaurants(String location, Callback callback) {
        //create an instance of OkHttpOAuthConsumer, and provide it our CONSUMER_KEY and CONSUMER_SECRET
        // class from SignPost that will create our signature. (SignPost is an OkHttp extension)
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.YELP_CONSUMER_KEY, Constants.YELP_CONSUMER_SECRET);
        //call setTokenWithSecret() and provide the TOKEN and TOKEN_SECRET
        consumer.setTokenWithSecret(Constants.YELP_TOKEN, Constants.YELP_TOKEN_SECRET);

        //create a new OkHttpClient to create and send our request.
        OkHttpClient client = new OkHttpClient.Builder()
                // We'll tie it to our consumer SignPost object responsible for creating our signature
                //adds our SignPost object to our OkHttp client as the SigningInterceptor. (Which is essentially just the thing responsible for managing that oauth_signature)
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        //creates a new URL using the YELP_BASE_URL value (Constants class)
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        //adds the YELP_LOCATION_QUERY_PARAMETER, and the specific location the user has opted to search.
        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
        //turns the finished URL into a string
        String url = urlBuilder.build().toString();

//        create our request using the new URL
        Request request= new Request.Builder()
                .url(url)
                .build();

        //create a Call object and place our request in it
        Call call = client.newCall(request);
        // to perform an asynchronous request
        // add our request to a queue
        //Since this is the only call in the queue of our app, it will run right away.
        // OkHttp will create a new thread to dispatch our request.
        // Once it has a readable response it will trigger our callback method, where it will send our response data.
        call.enqueue(callback);

    }

    public ArrayList<Restaurant> processResults(Response response) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try {
            String jsonData = response.body().string();

            if (response.isSuccessful()) {
                JSONObject yelpJSON = new JSONObject(jsonData);
                JSONArray businessesJSON = yelpJSON.getJSONArray("businesses"); //get from YELP JSON 'businesses" array

                for (int i = 0; i < businessesJSON.length(); i++) {
                    JSONObject restaurantJSON = businessesJSON.getJSONObject(i);
                    String name = restaurantJSON.getString("name");
                    String phone = restaurantJSON.optString("display_phone", "Phonen not available");
                    String website = restaurantJSON.getString("website");
                    double rating = restaurantJSON.getDouble("rating");
                    String imageUrl = restaurantJSON.getString("imageUrl");

                    double latitude = restaurantJSON.getJSONObject("location").getJSONObject("coordinate").getDouble                           ("latitude");

                    double longitude = restaurantJSON.getJSONObject("location").getJSONObject("coordinate").getDouble                           ("longitude");

                    ArrayList<String> address = new ArrayList<>();
                    JSONArray addressJSON = restaurantJSON.getJSONObject("location").getJSONArray("display_address");
                    for (int y = 0; y < addressJSON.length(); y++) {
                        address.add(addressJSON.get(y).toString());
                    }

                    ArrayList<String> categories = new ArrayList<>();
                    JSONArray categoriesJSON = restaurantJSON.getJSONArray("categories");
                    for (int y = 0; y < categoriesJSON.length(); y++) {
                        categories.add(categoriesJSON.getJSONArray(y).get(0).toString());
                    }

                    Restaurant restaurant = new Restaurant(name, phone, website, rating, imageUrl, address, latitude,                               longitude, categories);

                    restaurants.add(restaurant);
                } //for each business

            } //if succesful
        } catch (IOException e) {  //first try
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return restaurants;

    }

}
