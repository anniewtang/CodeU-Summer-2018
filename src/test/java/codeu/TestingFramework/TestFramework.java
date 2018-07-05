// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.TestingFramework;

import codeu.model.data.Constants;
import codeu.model.data.Dish;
import codeu.model.data.Review;
import codeu.model.data.Tag;
import org.junit.After;
import org.junit.Before;

import java.util.*;


/**
 * Extendable testing class with Data object constants
 * that can be reused for other Testing classes.
 */
public class TestFramework {
    // Dish Attributes
    public Dish dish;
    public UUID dishID = UUID.randomUUID();
    public String name = "Sesame Noodles";
    public String restaurant = "Asian Fusion";
    public int rating = 4;
    public Map<String, Set<String>> tags;
    public Set<String> restrictions;
    public Set<String> cuisine;
    public Set<String> allTagValues;
    public Map<String, Set<String>> tagsOne;
    public Set<String> restrictionsOne;
    public Set<String> cuisineOne;

    public Dish dishTwo;
    public UUID dishIDTwo = UUID.randomUUID();
    public String nameTwo = "Ramen";
    public String restaurantTwo = "Ramen Shop";
    public int ratingTwo = 3;
    public Map<String, Set<String>> tagsTwo;
    public Set<String> restrictionsTwo;
    public Set<String> cuisineTwo;
    public Set<String> allTagValuesTwo;

    // Review Attributes
    public Review review;
    public UUID reviewID = UUID.randomUUID();
    public UUID author = UUID.randomUUID();
    public String desc = "Desc for review 0";

    public Review reviewOne;
    public UUID reviewIDOne = UUID.randomUUID();
    public UUID authorOne = UUID.randomUUID();
    public String descOne = "Desc for review 1";

    public Review reviewTwo;
    public UUID reviewIDTwo = UUID.randomUUID();
    public UUID authorTwo = UUID.randomUUID();
    public String descTwo = "Desc for review 2";

    public Review reviewThree;
    public UUID reviewIDThree = UUID.randomUUID();
    public UUID authorThree = UUID.randomUUID();
    public String descThree = "Desc for review 3";


    // Tag Attributes
    public Tag cuisineTag;
    public String cuisineType = Constants.CUISINE;
    public Map<String, Set<UUID>> dishesByValue;
    public Set<UUID> cuisineChineseDishes;
    public Set<UUID> cuisineJapaneseDishes;
    public Set<UUID> cuisineAsianDishes;
    public Set<String> cuisineAllTags;

    public Tag restrictionTag;
    public String restrictionType = Constants.RESTRICTION;
    public Map<String, Set<UUID>> dishesByValueTwo;
    public Set<UUID> restrictionVeganDishes;
    public Set<UUID> restrictionVegetarianDishes;
    public Set<UUID> restrictionGlutenFreeDishes;
    public Set<String> restrictionAllTags;

    @Before
    public void setup() {
        // Dish 1
        tags = new HashMap<>();
        restrictions = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.VEGAN));
        cuisine = new HashSet<>(Arrays.asList(Constants.CHINESE, Constants.ASIAN));
        tags.put(Constants.RESTRICTION, restrictions);
        tags.put(Constants.CUISINE, cuisine);

        tagsOne = new HashMap<>();
        restrictionsOne = new HashSet<>(Arrays.asList(Constants.GLUTENFREE, Constants.VEGAN));
        cuisineOne = new HashSet<>(Arrays.asList(Constants.ASIAN));
        tagsOne.put(Constants.RESTRICTION, restrictionsOne);
        tagsOne.put(Constants.CUISINE, cuisineOne);

        allTagValues = new HashSet<>(Arrays.asList(Constants.GLUTENFREE, Constants.VEGETARIAN, Constants.VEGAN, Constants.CHINESE, Constants.ASIAN));
        dish = new Dish(dishID, name, restaurant, rating, tags, allTagValues);

        // Dish 2
        tagsTwo = new HashMap<>();
        restrictionsTwo = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.NUTFREE));
        cuisineTwo = new HashSet<>(Arrays.asList(Constants.JAPANESE, Constants.ASIAN));
        tagsTwo.put(Constants.RESTRICTION, restrictionsTwo);
        tagsTwo.put(Constants.CUISINE, cuisineTwo);
        allTagValuesTwo = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.JAPANESE, Constants.ASIAN));
        dishTwo = new Dish(dishIDTwo, nameTwo, restaurantTwo, ratingTwo, tagsTwo, allTagValuesTwo);

        // Reviews
        review = new Review(reviewID, author, dishID, rating, desc, tags);
        reviewOne = new Review(reviewIDOne, authorOne, dishID, rating, descOne, tagsOne);

        reviewTwo = new Review(reviewIDTwo, authorTwo, dishIDTwo, ratingTwo, descTwo, tagsTwo);
        reviewThree = new Review(reviewIDThree, authorThree, dishIDTwo, ratingTwo, descThree, tagsTwo);

        // Tag: Cuisine
        cuisineChineseDishes = new HashSet<>(Arrays.asList(dishID));
        cuisineJapaneseDishes = new HashSet<>(Arrays.asList(dishIDTwo));
        cuisineAsianDishes = new HashSet<>(Arrays.asList(dishID, dishIDTwo));
        dishesByValue = new HashMap<>();
        dishesByValue.put(Constants.CHINESE, cuisineChineseDishes);
        dishesByValue.put(Constants.JAPANESE, cuisineJapaneseDishes);
        dishesByValue.put(Constants.ASIAN, cuisineAsianDishes);
        cuisineAllTags = new HashSet<>();
        cuisineAllTags.addAll(Arrays.asList(Constants.CHINESE, Constants.JAPANESE, Constants.ASIAN));
        cuisineTag = new Tag(cuisineType, dishesByValue, cuisineAllTags);

        // Tag: Restriction
        restrictionVeganDishes = new HashSet<>(Arrays.asList(dishID));
        restrictionVegetarianDishes = new HashSet<>(Arrays.asList(dishID, dishIDTwo));
        restrictionGlutenFreeDishes = new HashSet<>(Arrays.asList(dishID));
        restrictionAllTags = new HashSet<>(Arrays.asList(Constants.VEGAN, Constants.VEGETARIAN, Constants.GLUTENFREE, Constants.NUTFREE));

    }

    @After
    public void teardown() {
        dish = null;
        dishID = null;
        restaurant = name = null;
        rating = 0;
        tags = null;
        restrictions = cuisine = allTagValues = null;

    }
}