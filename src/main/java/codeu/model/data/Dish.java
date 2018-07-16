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

import java.util.*;
import java.util.Map.Entry;

/**
 * Data class representing a Dish.
 * Dish objects will be dynamically updated, as user adds more tags and reviews.
 */
public class Dish implements Comparable<Dish> {
    private final UUID dishID;
    private final String dishName;
    private final String restaurant;
    private int rating;
    private Map<String, Set<String>> tags; // {tagType : {tagValues}}
    private Set<String> allTagValues;

    /**
     * Used when the user FIRST rates a new Dish
     * i.e. first time ever this Dish enters our system.
     *
     * @param id         the ID of this dish
     * @param name       the name of the dish
     * @param restaurant the name of the restaurant where this dish came from
     */
    public Dish(UUID id, String name, String restaurant) {
        this(id, name, restaurant, 0, new HashMap<>(), new HashSet<>());
    }

    /**
     * Used while loading Dish for PDS.
     *
     * @param id           the ID of this dish
     * @param name         the name of the dish
     * @param restaurant   the name of the restaurant where this dish came from
     * @param tags         the tag values for this dish, organized by tag TYPE
     * @param allTagValues the collection of ALL tag values associated for dish
     */
    public Dish(UUID id, String name, String restaurant, int rating, Map<String, Set<String>> tags, Set<String> allTagValues) {
        this.dishID = id;
        this.dishName = name;
        this.restaurant = restaurant;
        this.rating = rating;
        this.tags = tags;
        this.allTagValues = allTagValues;
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
    public Map<String, Set<String>> getTags() {
        return this.tags;
    }

    /**
     * Returns all the Tags Dish has
     */
    public Set<String> getAllTagValues() {
        return this.allTagValues;
    }

    /**
     * Updates the average star rating a Dish has, after more users rate it.
     *
     * @param rating updated average star rating
     * @method updateRating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Update previously existing tags (if any) with newly given user-tags.
     * Also updates collection of All Tags associated with this Dish.
     *
     * @param userTags new user-given tags in the form: {tagType : {tagValues}}
     * @method addUserTags
     */
    public void addUserTags(Map<String, Set<String>> userTags) {
        for (Entry<String, Set<String>> pair : userTags.entrySet()) {
            String type = pair.getKey();
            Set<String> tags = pair.getValue();

            updateTagsForType(type, tags);
            this.allTagValues.addAll(tags);
        }
    }

    private void updateTagsForType(String type, Set<String> userTagsForTagType) {
        Set<String> values = tags.computeIfAbsent(type, k -> new HashSet<>());
        values.addAll(userTagsForTagType);
    }

    /**
     * Private helper method meant to help initialize this.allTagValues,
     * during the basic initialization when we create the first apperance
     * of this particular Dish (i.e. never rated by a user before).
     * @param tags mapping of the given user tags {tagType : {tagValues}}
     * @return a set with all the tag values
     */
    private static Set<String> aggregateAllTagValues(Map<String, Set<String>> tags) {
        Set<String> allTagValues = new HashSet<>();
        for (Entry<String, Set<String>> pair : tags.entrySet()) {
            allTagValues.addAll(pair.getValue());
        }
        return allTagValues;
    }

    /**
     * An equals method for Testing.
     */
    @Override
    public boolean equals(Object o) {
        Dish d = (Dish) o;
        return d.getDishID().equals(this.dishID)
                && d.getDishName().equals(this.dishName)
                && d.getRestaurant().equals(this.restaurant)
                && d.getRating() == (this.rating)
                && d.getTags().equals(this.tags)
                && d.getAllTagValues().equals(this.allTagValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishID, dishName, restaurant, rating, tags, allTagValues);
    }

    /**
     * Returns:
     * a negative integer if this < d
     * zero if this == d
     * positive integer if this > d
     */
    @Override
    public int compareTo(Dish d) {
        return Integer.compare(this.getRating(), d.getRating());
    }

}
