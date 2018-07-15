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

import codeu.model.data.Constants;
import codeu.model.data.Dish;
import codeu.model.data.Review;
import codeu.model.data.Tag;

import java.util.*;

import static java.util.stream.Collectors.toSet;

/**
 * Wrapper class with public methods meant to make adding and accessing
 * the backend data (i.e. app content: Dishes, Reviews, Tags) easier.
 */
public class ContentManager {
    private static DishStore dishStore      = DishStore.getInstance();
    private static ReviewStore reviewStore  = ReviewStore.getInstance();
    private static TagStore tagStore        = TagStore.getInstance();

    /* =========================================================
    Methods for @cohanale to use when users want to Rate Dishes.
    ========================================================= */
    /**
     * Used when user adds a NEW Review for a NEW Dish.
     * Creates both a new Dish object and a Review object within our appstore.
     * @param dish first instance that this Dish appears
     * @param review first review for this Dish
     */
    public static void addNewDishAndFirstReview(Dish dish, Review review) {
        DishStore.getInstance().addDish(dish);
        addReviewForExistingDish(review);
    }

    /**
     * Used when user adds a NEW Review for an EXISTING Dish.
     * Creates a new Review object to be associated with an existing Dish object.
     * @param review user-written review
     */
    public static void addReviewForExistingDish(Review review) {
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
    public static Set<Dish> queryByTags(Map<String, Set<String>> queryTags) {
        Set<UUID> queriedDishes = new HashSet<>();

        for (Map.Entry<String, Set<String>> pair : queryTags.entrySet()) {
            String tagType = pair.getKey();
            Tag tag = tagStore.getTagForType(tagType);
            for (String tagValue : pair.getValue()) {
                if (queriedDishes.size() == 0) {
                    queriedDishes = new HashSet<>(tagStore.getDishesByValue(tag, tagValue));
                } else {
                    queriedDishes.retainAll(tagStore.getDishesByValue(tag, tagValue));
                }
            }
        }

        return queriedDishes.stream().map(dishStore::getDish).collect(toSet());
    }

    /**
     * QUERY BASED ON RATING ONLY.
     * ==========================
     * Allows users to search for a set of Dishes of certain RATINGs only.
     * @param queryRatings set of dish rating values user wants for Dish results.
     * @return Set of Dishes that satisfy the rating requirements.
     */
    public static Set<Dish> queryByRatings(Set<Integer> queryRatings) {
        Set<Dish> results = new HashSet<>();
        for (int rating : queryRatings) {
            Set<UUID> dishes = dishStore.getDishesOfRating(rating);
            results.addAll(dishes.stream().map(dishStore::getDish).collect(toSet()));
        }
        return results;
    }

    /**
     * SORT ALL DISHES BY RATING ONLY.
     * ==============================
     * Provides ALL the dishes in the Dish Store sorted by rating, based on user's preference.
     * @param highestToLow boolean value telling us which way users want the sort
     * @return sorted List of ALL Dishes for the user
     */
    public static List<Dish> sortAllByRating(boolean highestToLow) {
        Collection allDishes = DishStore.getInstance().getAllDishes();
        // should return a Collection of all Dish objects
        // use a TreeSet or PriorityQueue to automatically sort dishes, based on the rating attribute
        return null;
    }

    /**
     * QUERY BY BOTH TAGS AND RATINGS.
     * ===============================
     * Allows user to query Dishes based on their preferences (tags + rating values)
     * @param queryTags map of user requirements for Dish tags {tagType : {tagValues}}
     * @param ratings set of ratings user wants to see Dishes for
     * @return set of Dish objects that match all these requirements
     */
    public static Set<Dish> queryByTagsAndRatings(Map<String, Set<String>> queryTags, Set<Integer> ratings) {
        // select dishes from Tag restrictions first; somehow get overlapping sets
        // filter based on the rating of the dish?
        // return a TreeSet so queryAndSort can use it lel
        return null;
    }

    /**
     * QUERY BY BOTH TAGS AND RATINGS && SORT RESULTS AS WELL.
     * =======================================================
     * Allows user to query based on preferences (tags + rating values)
     * AND have everything be returned in sorted order
     * @param queryTags (same as above)
     * @param ratings (same as above)
     * @param highestToLow boolean to indicate users' preference for high to low rating
     * @return sorted set of Dish objects that match all the requirements
     */
    public static Set<Dish> queryAndSort(Map<String, Set<String>> queryTags, Set<Integer> ratings, boolean highestToLow) {
        // call the previous method
        // take the returned dish set as a TreeSet with custom comparator
        return null;
    }



}
