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

package codeu.model.data;

import codeu.model.data.Review;

import java.time.Instant;
import java.util.UUID;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Map.Entry;

/**
 * Data class representing a Dish.
 * Dish objects will be dynamically updated, as user adds more tags and reviews.
 */
public class Dish {
    private final UUID dishID;
    private final String dishName;
    private final String restaurant;
    private int rating;
    private HashMap<String, Set<String>> tags; // {tagType : {tagValues}}
    private Set<String> allTagValues;

    /**
     * Constructs a new Dish object.
     *
     * @param id         the ID of this dish
     * @param name       the name of the dish
     * @param restaurant the name of the restaurant where this dish came from
     * @param tags       the tag values for this dish, organized by tag TYPE
     */
    public Dish(UUID id, String name, String restaurant, int rating, HashMap<String, Set<String>> tags) {
        this.dishID = id;
        this.dishName = name;
        this.restaurant = restaurant;
        this.rating = rating;
        this.tags = tags;
        this.allTagValues = new HashSet<>();
    }

    /**
     * Overloaded Dish constructor.
     * Used while loading Dish for PDS.
     *
     * @param id           the ID of this dish
     * @param name         the name of the dish
     * @param restaurant   the name of the restaurant where this dish came from
     * @param tags         the tag values for this dish, organized by tag TYPE
     * @param allTagValues the collection of ALL tag values associated for dish
     */
    public Dish(UUID id, String name, String restaurant, int rating, HashMap<String, Set<String>> tags, Set<String> allTagValues) {
        this.dishID = id;
        this.dishName = name;
        this.restaurant = restaurant;
        this.rating = rating;
        this.tags = tags;
        this.allTagValues = new HashSet<>();
    }

    /**
     * Returns id of the dish
     */
    public UUID getDishID() {
        return this.dishID;
    }

    /**
     * Returns name of the dish
     */
    public String getDishName() {
        return this.dishName;
    }

    /**
     * Returns name of dish restaurant
     */
    public String getRestaurant() {
        return this.restaurant;
    }

    /**
     * Returns avg rating of this dish
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * Returns all the tags for this dish in the format {tagType: {tagValues}}
     */
    public HashMap<String, Set<String>> getTags() {
        return this.tags;
    }

    /**
     * Return the number of reviews Dish has
     */
    public int getNumReviews() {
        // TODO: pull from Dish store
        return 0;
    }

    /**
     * Returns all the Reviews Dish has
     */
    public Set<Review> getReviews() {
        // TODO: pull from Dish store
        return null;
    }


    /**
     * Updates the average star rating a Dish has, after more users rate it.
     *
     * @param rating updated average star rating
     * @method setRating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Update previously existing tags (if any) with newly given user-tags.
     * Also updates collection of All Tags associated with this Dish.
     *
     * @param userTags new user-given tags in the form: {tagType : {tagValues}}
     * @method setUserTags
     */
    public void setUserTags(HashMap<String, Set<String>> userTags) {
        for (Entry<String, Set<String>> pair : userTags.entrySet()) {
            String type = pair.getKey();
            Set<String> tags = pair.getValue();
            updateTagsForType(type, tags);
            updateAllTagValues(tags);
        }
    }

    private void updateTagsForType(String type, Set<String> tags) {
        Set<String> values = getValuesOfType(type);
        values.addAll(tags);
    }

    private Set<String> getValuesOfType(String type) {
        Set<String> values = this.tags.get(type);
        if (values == null) {
            values = new HashSet<>();
        }
        return values;
    }

    private void updateAllTagValues(Set<String> tags) {
        this.allTagValues.addAll(tags);
    }

    /**
     * Returns all the Tags Dish has
     */
    public Set<String> getAllTagValues() {
        return this.allTagValues;
    }
}
