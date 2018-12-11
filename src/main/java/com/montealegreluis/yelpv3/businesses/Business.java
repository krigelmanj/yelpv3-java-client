/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import com.montealegreluis.yelpv3.businesses.distance.Distance;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Business {
    public double rating;
    public PricingLevel pricingLevel;
    public String phone;
    public String id;
    public boolean isClosedPermanently;
    public Categories categories;
    public int reviewCount;
    public String name;
    public URL url;
    public Coordinates coordinates;
    public URL image;
    public Location location;
    public Distance distance;
    public final List<Transaction> transactions;

    public Business(
        double rating,
        PricingLevel pricingLevel,
        String phone,
        String id,
        boolean isClosedPermanently,
        Categories categories,
        int reviewCount,
        String name,
        URL url,
        Coordinates coordinates,
        URL image,
        Location location,
        Distance distance,
        List<Transaction> transactions
    ) {
        this.rating = rating;
        this.pricingLevel = pricingLevel;
        this.phone = phone;
        this.id = id;
        this.isClosedPermanently = isClosedPermanently;
        this.categories = categories;
        this.reviewCount = reviewCount;
        this.name = name;
        this.url = url;
        this.coordinates = coordinates;
        this.image = image;
        this.location = location;
        this.distance = distance;
        this.transactions = Collections.unmodifiableList(transactions);
    }

    public Business() {

        transactions = null;
    }

    public boolean isInCity(String city) {
        return location.city.equalsIgnoreCase(city);
    }

    public boolean isWithinRadius(Distance radius) {
        return distance.smallerThan(radius);
    }

    public boolean hasPricingLevel(PricingLevel pricingLevel) {
        return this.pricingLevel.equals(pricingLevel);
    }

    public boolean hasMoreReviewsThan(Business business) {
        return reviewCount > business.reviewCount;
    }

    public boolean isInCategory(String categoryAlias) {
        return categories
            .stream()
            .filter(category -> category.hasAlias(categoryAlias))
            .collect(Collectors.toList())
            .size() > 0
        ;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || !(other instanceof Business)) return false;

        Business anotherBusiness = (Business) other;

        return this.id.equals(anotherBusiness.id);
    }
}
