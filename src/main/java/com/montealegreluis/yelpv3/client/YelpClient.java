/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.client;

import com.montealegreluis.yelpv3.search.SearchCriteria;

import java.util.Map;

/**
 * This client is currently supporting the following endpoints
 * <p>
 * <ul>
 * <li>Authentication</li>
 * <li>Search API</li>
 * <li>Business API</li>
 * <li>Reviews API</li>
 * </ul>
 */
public interface YelpClient {
    void allBusinessesMatching(SearchCriteria criteria);

    void businessWith(String id);

    void allReviewsFor(String businessId);

    void authenticate(Map<String, String> credentials);

    String responseBody();
}
