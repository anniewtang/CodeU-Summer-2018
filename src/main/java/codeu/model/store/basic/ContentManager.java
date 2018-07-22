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
import codeu.model.data.Tag;

import java.util.*;

import static java.util.stream.Collectors.toSet;

/**
 * Wrapper class with public methods meant to make adding and accessing
 * the backend data (i.e. app content: Dishes, Reviews, Tags) easier.
 */
public class ContentManager {
    /* =========================================================
    Methods for @cohanele to use when users want to Rate Dishes.
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
            Tag tag = TagStore.getInstance().getTagForType(tagType);
            for (String tagValue : pair.getValue()) {
                queriedDishes.addAll(TagStore.getInstance().getDishesByValue(tag, tagValue));
            }
        }
        return queriedDishes.stream().map(DishStore.getInstance()::getDish).collect(toSet());
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
            Set<UUID> dishes = DishStore.getInstance().getDishesOfRating(rating);
            if (dishes != null && !dishes.isEmpty()) {
                results.addAll(dishes.stream().map(DishStore.getInstance()::getDish).collect(toSet()));
            }
        }
        return results;
    }

    /**
     * SORT ALL DISHES BY RATING ONLY.
     * ==============================
     * Provides ALL the dishes in the Dish Store sorted by rating, based on user's preference.
     * @param highestToLow boolean value telling us which way users want the sort
     * @return sorted set of ALL Dishes for the user
     */
    public static Set<Dish> sortAllByRating(boolean highestToLow) {
        Collection<Dish> allDishes = DishStore.getInstance().getAllDishes();
        return sortDishes(allDishes, highestToLow);
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
        Set<Dish> results = queryByTags(queryTags);
        results.retainAll(queryByRatings(ratings));
        return results;
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
        Collection<Dish> results = queryByTagsAndRatings(queryTags, ratings);
        return sortDishes(results, highestToLow);
    }

    /**
     * Private helper method meant to help sort dishes by rating,
     * based on user preference (highest to low, or lowest to high).
     * Uses implemented Dish.compareTo() method for lowest to high,
     * OR custom Comparator for highest to low.
     * @param dishes set of dishes we want to sort
     * @param highestToLow user preference for sort order
     * @return TreeSet of sorted Dish objects
     */
    private static Set<Dish> sortDishes(Collection<Dish> dishes, boolean highestToLow) {
        TreeSet<Dish> sorted = new TreeSet<>();

        if (dishes == null || dishes.isEmpty()) {
            return sorted;
        }

        if (highestToLow) {
            sorted = new TreeSet<>((Dish d1, Dish d2) -> -1 * Integer.compare(d1.getRating(), d2.getRating()));
        }
        sorted.addAll(dishes);
        return sorted;
    }

    /* ================================================
    Methods for to use when pulling data for webapp UI.
    ===================================================*/
    /**
     * Can be used to get Dish name from Dish UUID.
     * @param id of the dish
     * @return Dish name
     */
    public static String getDishName(UUID id) {
        return DishStore.getInstance().getDish(id).getDishName();
    }

    /**
     * Can be used to display all the reviews associated with a given Dish.
     * @param id of the dish
     * @return set of Review objects for Dish.
     */
    public static Set<Review> getReviewsForDish(UUID id) {
        return ReviewStore.getInstance().getReviewsForDish(id);
    }

    /**
     * Can be used to display all the user tags associated with a given Dish,
     * returned in an organized map by each tag category.
     * @param id of the dish
     * @return map of {tagType : {tagValues}}
     */
    public static Map<String, Set<String>> getTagMapForDish(UUID id) {
        return DishStore.getInstance().getTagsForDish(id);
    }

    /**
     * Can be used to display all the user tags associated with a given Dish,
     * returned in no particular order (just a random collection).
     * @param id of the dish
     * @return unordered set of {tagValues}
     */
    public static Set<String> getAllTagsForDish(UUID id) {
        return DishStore.getInstance().getAllTagsForDish(id);
    }

    /**
     * Used to display all dishes when user is choosing what Dish they want to review for.
     * @return collection of all dishes in DishStore.
     */
    public static Collection<Dish> getAllDishes() {
        return new TreeSet<>(DishStore.getInstance().getAllDishes());
    }
}
