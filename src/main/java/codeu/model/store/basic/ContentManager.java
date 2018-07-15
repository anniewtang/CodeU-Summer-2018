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

package codeu.model.store.basic;

import codeu.model.data.Dish;
import codeu.model.data.Review;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Wrapper class with public methods meant to make adding and accessing
 * the backend data (i.e. app content: Dishes, Reviews, Tags) easier.
 */
public class ContentManager {
    private DishStore dishStore;
    private ReviewStore reviewStore;
    private TagStore tagStore;


    /**
     * Singleton instance of ContentManager.
     */
    private static ContentManager instance;

    /**
     * Returns the singleton instance of ContentManager
     * that should be shared between all servlet classes.
     */
    public static ContentManager getInstance() {
        if (instance == null) {
            instance = new ContentManager();
        }
        return instance;
    }

    private ContentManager() {
        this.dishStore = DishStore.getInstance();
        this.reviewStore = ReviewStore.getInstance();
        this.tagStore = TagStore.getInstance();
    }

    /* =========================================================
    Methods for @cohanale to use when users want to Rate Dishes.
    ========================================================= */
    /**
     * Used when user adds a NEW Review for a NEW Dish.
     * Creates both a new Dish object and a Review object within our appstore.
     * @param dish first instance that this Dish appears
     * @param review first review for this Dish
     */
    public void addNewDishAndFirstReview(Dish dish, Review review) {
        DishStore.getInstance().addDish(dish);
        addReviewForExistingDish(review);
    }

    /**
     * Used when user adds a NEW Review for an EXISTING Dish.
     * Creates a new Review object to be associated with an existing Dish object.
     * @param review user-written review
     */
    public void addReviewForExistingDish(Review review) {
        ReviewStore.getInstance().addReview(review);
    }

    /* =============================================================
    Methods for @helarabawy to use when querying for search results.
    ============================================================== */

    /**
     * QUERY BASED ON TAGS ONLY.
     * ========================
     * Allows users to search for a set of Dishes that satisfy their requirements.
     * Requirements are based on ONLY given user tag preferences:
     * 1.) Cuisine Type
     * 2.) Dish Type
     * 3.) Dietary Restrictions
     * @param queryTags map of user requirements for Dish tags {tagType : {tagValues}}
     * @return Set of Dishes that satisfy requirements
     */
    public Set<Dish> queryByTags(Map<String, Set<String>> queryTags) {
        return null;
    }

    /**
     * QUERY BASED ON RATING ONLY.
     * ==========================
     * Allows users to search for a set of Dishes of certain RATINGs only.
     * @param queryRatings set of dish rating values user wants for Dish results.
     * @return Set of Dishes that satisfy the rating requirements.
     */
    public Set<Dish> queryByRatings(Set<Integer> queryRatings) {
        return null;
    }

    /**
     * SORT ALL DISHES BY RATING ONLY.
     * ==============================
     * @param highestToLow boolean value telling us which way users want the sort
     * @return sorted List of ALL Dishes for the user
     */
    public List<Dish> sortAllByRating(boolean highestToLow) {
        // use DishStore.getInstance().getAllDishes()
        // should return a Collection of all Dish objects
        // use a TreeSet or PriorityQueue to automatically sort dishes, based on the rating attribute
        return null;
    }

    public Set<Dish> queryByTagsAndRatings(Map<String, Set<String>> queryTags, Set<Integer> ratings) {
        // select dishes from Tag restrictions first; somehow get overlapping sets
        // filter based on the rating of the dish?
        // return a TreeSet so queryAndSort can use it lel
        return null;
    }

    public Set<Dish> queryAndSort(Map<String, Set<String>> queryTags, Set<Integer> ratings, boolean highestToLow) {
        // call the previous method
        // take the returned dish set as a TreeSet with custom comparator
        return null;
    }



}
