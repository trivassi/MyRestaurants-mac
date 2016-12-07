package com.epicodus.myrestaurants.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 11/26/16.
 */
//Each member variable name must match the name of the keys of our children nodes. In our case, this means we need to remove the ‘m’ prefix from variables in our Restaurant class.

//Each member variable must be a valid JSON type. ArrayList is not a valid JSON type, so we need to change our address and categories variables to List types.

//We must provide an empty constructor.

//We must also provide public getter methods for every member variable.


@Parcel
public class Restaurant {
    String name;
    String phone;
    String website;
    double rating;
    String imageUrl;
    List<String> address = new ArrayList<>();
    double latitude;
    double longitude;
    List<String> categories = new ArrayList<>();

    public Restaurant() {}

    public Restaurant(String name, String phone, String website, double rating, String imageUrl, ArrayList<String> address, double latitude, double longitude, ArrayList<String> categories) {
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.rating = rating;
        this.imageUrl = getLargeImageUrl(imageUrl);
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public double getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<String> getCategories() {
        return categories;
    }

    //replaces the last characters in the image's filepath with the characters that correspond to the higher-quality version
    public String getLargeImageUrl(String imageUrl) {
        String largeImageUrl = imageUrl.substring(0, imageUrl.length() - 6).concat("o.jpg");
        return largeImageUrl;
    }
}





