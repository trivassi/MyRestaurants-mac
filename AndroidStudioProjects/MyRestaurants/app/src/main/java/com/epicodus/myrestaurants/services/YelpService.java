package com.epicodus.myrestaurants.services;

/**
 * Created by T on 11/26/16.
 */

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.models.Restaurant;

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
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.YELP_CONSUMER_KEY, Constants.YELP_CONSUMER_SECRET);
        consumer.setTokenWithSecret(Constants.YELP_TOKEN, Constants.YELP_TOKEN_SECRET); //call setTokenWithSecret() and                 provide the TOKEN and TOKEN_SECRET


        OkHttpClient client = new OkHttpClient.Builder() //create a new OkHttpClient to create and send our request.
                .addInterceptor(new SigningInterceptor(consumer))//adds our SignPost object to our OkHttp client as the                         SigningInterceptor
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();//creates a new URL using the                  YELP_BASE_URL value
        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location); //adds the                 Y                   YELP_LOCATION_QUERY_PARAMETER, and the specific location the user has opted to search.
        String url = urlBuilder.build().toString(); //turns the finished URL into a string

        Request request= new Request.Builder() //create our request using the new URL
                .url(url)
                .build();


        Call call = client.newCall(request);  //create a Call object and place our request in it
        call.enqueue(callback); // add our request to a queue
    }

    public ArrayList<Restaurant> processResults(Response response) {
        ArrayList<Restaurant> restaurants = new ArrayList<>(); //create array to contain all restaurant objects

        try {
            String jsonData = response.body().string(); //transform the API response into a string
            if (response.isSuccessful()) {
                JSONObject yelpJSON = new JSONObject(jsonData); //create JSON object if response is succesful
                JSONArray businessesJSON = yelpJSON.getJSONArray("businesses"); //get from YELP JSON 'businesses" array
                for (int i = 0; i < businessesJSON.length(); i++) {
                    JSONObject restaurantJSON = businessesJSON.getJSONObject(i);
                    String name = restaurantJSON.getString("name");
                    String phone = restaurantJSON.optString("display_phone", "Phone not available"); //if no info available
                    String website = restaurantJSON.getString("url");
                    double rating = restaurantJSON.getDouble("rating");
                    String imageUrl = restaurantJSON.getString("image_url");
                    double latitude = restaurantJSON.getJSONObject("location")
                            .getJSONObject("coordinate").getDouble("latitude");
                    double longitude = restaurantJSON.getJSONObject("location")
                            .getJSONObject("coordinate").getDouble("longitude");
                    ArrayList<String> address = new ArrayList<>();
                    JSONArray addressJSON = restaurantJSON.getJSONObject("location")
                            .getJSONArray("display_address");
                    for (int y = 0; y < addressJSON.length(); y++) {
                        address.add(addressJSON.get(y).toString());
                    }

                    ArrayList<String> categories = new ArrayList<>();
                    JSONArray categoriesJSON = restaurantJSON.getJSONArray("categories");

                    for (int y = 0; y < categoriesJSON.length(); y++) {
                        categories.add(categoriesJSON.getJSONArray(y).get(0).toString());
                    }
                    Restaurant restaurant = new Restaurant(name, phone, website, rating,
                            imageUrl, address, latitude, longitude, categories);
                    restaurants.add(restaurant);  //add it to array
                }//for each business {}
            } //for each business {}
        } catch (IOException e) { //first try{}
            e.printStackTrace();
        } catch (JSONException e) { //catch exceptions
            e.printStackTrace();
        }
        return restaurants;  //return list of restaurants
    }


}