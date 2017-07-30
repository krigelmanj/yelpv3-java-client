/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.stream.Collectors;

public class Business {
    public final BasicInformation basicInformation;
    public final Details details;

    public Business(BasicInformation basicInformation, Details details) {
        this.basicInformation = basicInformation;
        this.details = details;
    }

    public boolean isInCategory(String categoryAlias) {
        return basicInformation.categories
            .stream()
            .filter(category -> category.hasAlias(categoryAlias))
            .collect(Collectors.toList())
            .size() > 0
        ;
    }
}
