package codeu.orm;// Copyright 2017 Google Inc.
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

import codeu.model.data.Dish;
import codeu.model.store.basic.ReviewStore;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Wrapper class that loads information from Data Store,
 * handles the setting & getting of information for Dishes,
 * and abstracts the process of querying.
 */
public class DishORM {
    private HashMap<UUID, Dish> dishMap; // maps dishIDs to Dish objects
    private HashMap<UUID, Integer> ratingMap; // maps dishIDs to average star ratings

    public DishORM(HashMap<UUID, Dish> dishMap, HashMap<UUID, Integer> ratingMap) {
        this.dishMap = dishMap;
        this.ratingMap = ratingMap;
    }

    /**
     * Adds a new Dish into our dishMap
     */
    public void addDish(UUID id, Dish dish) {
        this.dishMap.put(id, dish);
    }

    /**
     * Returns true if this dish exists in our map
     */
    public boolean dishExists(UUID id) {
        return this.dishMap.containsKey(id);
    }

    /**
     * Returns Dish
     * Should only be called if Dish exists in our map
     */
    public Dish getDish(UUID id) {
        return this.dishMap.get(id);
    }

    /**
     * Get rating of a particular dish.
     * Only called under the conditions that ID exists in our dishMap
     */
    public int getRating(UUID id) {
        int rating = this.ratingMap.get(id);
        return rating;
    }

    public int getNumReviews(UUID id) {
        return ReviewStore.getInstance().getNumReviews(id);
    }

    /**
     * Retrieves all the tags for a particular Dish.
     * Good for UI display (i.e. showing _all_ tags for one dish for the user)
     *
     * @param id id of the dish we want all tags of
     * @return returns {tag type : {tag values}}
     * @method getTagsForDish
     */
    public HashMap<String, Set<String>> getTagsForDish(UUID id) {
        Dish dish = getDish(id);
        return dish.getTags();
    }

    /**
     * Updates the AVERAGE rating for this dish in orm
     */
    // TODO: SEE IF REVIEWSTORE IS UPDATED BEFORE THIS OR NOT? may need to change prevNumReviews depending on that.
    public Dish updateRating(UUID id, int rate) {
        Dish updatedDish = getDish(id);
        int oldRating = 0;
        if (dishExists(id)) {
            oldRating = getRating(id);
        }
        int prevNumReviews = getNumReviews(id);
        updatedDish.setRating((oldRating * prevNumReviews + rate) / (prevNumReviews + 1));
        return updatedDish;
    }

    /**
     * After user tags a particular Dish, DishORM will update that Dish's
     * tag values within the Dish object via this Handler class.
     *
     * @param id       id of the Dish that was just rated/tagged
     * @param userTags all the userTags of the form {tagType : {tagValues}}
     * @return the Dish object that was updated to be rewritten into DataStore
     * @method updateDishTags
     */
    public Dish updateDishTags(UUID id, HashMap<String, Set<String>> userTags) {
        Dish dish = getDish(id);
        dish.addUserTags(userTags);
        return dish;
    }
}
