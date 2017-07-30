/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.parser;

import com.montealegreluis.yelpv3.businesses.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class BusinessParser {
    static Business parseFrom(JSONObject businessInformation) {
        try {
            return new Business(
                BasicInformationParser.from(businessInformation),
                DetailsParser.from(businessInformation)
            );
        } catch (JSONException exception) {
            throw ParsingFailure.producedBy(businessInformation, exception);
        }
    }
}

class BasicInformationParser {
    static BasicInformation from(JSONObject information) {
        return new BasicInformation(
            information.getDouble("rating"),
            information.has("price") ? PricingLevel.fromSymbol(information.getString("price")) : PricingLevel.NONE,
            information.getString("phone"),
            information.getString("id"),
            information.getBoolean("is_closed"),
            buildCategories(information.getJSONArray("categories")),
            information.getInt("review_count"),
            information.getString("name"),
            information.getString("url"),
            CoordinatesParser.from(information.getJSONObject("coordinates")),
            information.getString("image_url"),
            LocationParser.from(information.getJSONObject("location")),
            !information.isNull("distance") ? new Distance(information.getDouble("distance")) : null,
            buildTransactions(information.getJSONArray("transactions"))
        );
    }

    private static List<Category> buildCategories(JSONArray businessCategories) {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < businessCategories.length(); i++)
            categories.add(CategoryParser.from(businessCategories.getJSONObject(i)));
        return categories;
    }

    private static List<String> buildTransactions(JSONArray registeredTransactions) {
        List<String> transactions = new ArrayList<>();
        for (int i = 0; i < registeredTransactions.length(); i++)
            transactions.add(registeredTransactions.getString(i));
        return transactions;
    }
}

class CoordinatesParser {
    static Coordinates from(JSONObject coordinates) {
        return new Coordinates(
            !coordinates.isNull("latitude") ? coordinates.getDouble("latitude") : 0,
            !coordinates.isNull("longitude") ? coordinates.getDouble("longitude") : 0
        );
    }
}

class LocationParser {
    static Location from(JSONObject location) {
        return new Location(
            location.getString("address1"),
            !location.isNull("address2") ? location.getString("address2") : "",
            !location.isNull("address3") ? location.getString("address3") : "",
            location.getString("city"),
            location.getString("state"),
            location.getString("country"),
            location.getString("zip_code"),
            !location.isNull("cross_streets") ? location.getString("cross_streets") : "",
            !location.isNull("display_address") ? setDisplayAddress(location.getJSONArray("display_address")) : null
        );
    }

    private static List<String> setDisplayAddress(JSONArray businessDisplayAddress) {
        List<String> displayAddress = new ArrayList<>();

        for (int i = 0; i < businessDisplayAddress.length(); i++)
            displayAddress.add(businessDisplayAddress.getString(i));

        return displayAddress;
    }
}

class CategoryParser {
    static Category from(JSONObject category) {
        return new Category(
            category.getString("alias"),
            category.getString("title")
        );
    }
}

class DetailsParser {
    static Details from(JSONObject information) {
        return new Details(
            !information.isNull("is_claimed") && information.getBoolean("is_claimed"),
            !information.isNull("photos") ? buildPhotos(information.getJSONArray("photos")) : null,
            !information.isNull("hours") ? ScheduleParser.from(information.getJSONArray("hours")) : null
        );
    }

    private static List<String> buildPhotos(JSONArray businessPhotos) {
        List<String> photos = new ArrayList<>();

        for (int i = 0; i < businessPhotos.length(); i++) photos.add(businessPhotos.getString(i));

        return photos;
    }
}

class ScheduleParser {
    static Schedule from(JSONArray hours) {
        JSONObject weekSchedule = hours.getJSONObject(0);

        return new Schedule(
            weekSchedule.getBoolean("is_open_now"),
            buildHours(weekSchedule.getJSONArray("open"))
        );
    }

    private static List<Hours> buildHours(JSONArray businessHours) {
        List<Hours> hours = new ArrayList<>();

        for (int i = 0; i < businessHours.length(); i++)
            hours.add(Hours.from(businessHours.getJSONObject(i)));

        return hours;
    }
}
