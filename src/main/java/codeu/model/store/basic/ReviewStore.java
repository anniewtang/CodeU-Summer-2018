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

import codeu.model.data.Review;
import codeu.model.data.Tag;
import codeu.model.data.Dish;
import codeu.model.store.persistence.PersistentStorageAgent;
import codeu.model.store.basic.TagStore;
import codeu.model.store.basic.DishStore;
import codeu.model.store.basic.ReviewStore;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.Iterator;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class ReviewStore {

    /**
     * Singleton instance of ReviewStore.
     */
    private static ReviewStore instance; // singleton instance of ReviewStore

    /**
     * Returns the singleton instance of ReviewStore that should be shared between all servlet
     * classes. Do not call this function from a test; use getTestInstance() instead.
     */
    public static ReviewStore getInstance() {
        if (instance == null) {
            instance = new ReviewStore(PersistentStorageAgent.getInstance());
        }
        return instance;
    }

    /**
     * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
     *
     * @param persistentStorageAgent a mock used for testing
     */
    public static ReviewStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
        return new ReviewStore(persistentStorageAgent);
    }

    /**
     * The PersistentStorageAgent responsible for loading Reviews from and saving Reviews to
     * Datastore.
     */
    private PersistentStorageAgent persistentStorageAgent;

    /**
     * The in-memory mapping of dishIDs to Reviews
     */
    private Map<UUID, Set<Review>> reviewsByDish;

    /**
     * This class is a singleton, so its constructor is private. Call getInstance() instead.
     */
    private ReviewStore(PersistentStorageAgent persistentStorageAgent) {
        this.persistentStorageAgent = persistentStorageAgent;
    }

    /**
     * Can be used for the first time we're adding a Dish (i.e. by "addReview"),
     * or for when the dish has already been added (therefore associated w/at least 1 review).
     * @param dishID
     * @return the set of Reviews associated with a Dish.
     */
    public Set<Review> getReviewsForDish(UUID dishID) {
        return reviewsByDish.computeIfAbsent(dishID, id -> new HashSet<>());
    }

    /**
     * Can be used for the first time we're adding a Dish (i.e. by "addReview"),
     * or for when the dish has already been added (therefore associated w/at least 1 review).
     * @param dishID
     * @return the set of Reviews associated with a Dish.
     */
    public Review getBestReviewForDish(UUID dishID) {
        Set<Review> reviewList = getReviewsForDish(dishID);
        Review bestReview = null;
        int currMax = 0;

        for (Review currReview : reviewList) {
          if (currReview.getStarRating() > currMax) {
            currMax = currReview.getStarRating();
            bestReview = currReview;
          }
        }
        return bestReview;
    }

    /**
     * Should always be at least one.
     * @param dishID
     * @return total number of reviews associated with Dish.
     */
    public int getNumReviews(UUID dishID) {
        return getReviewsForDish(dishID).size();
    }

    /**
     * Incorporate the new user review into our app by:
     * 1.) Updating the TagStore & Dish object with this review's given user tags.
     * 2.) Updating the Average Star Rating associated with its dish.
     * 3.) Add the new review into our in-memory & persistent storage
     *
     * @param review
     */
    // TODO || Dependency: make sure addReview is called AFTER DishORM.addDish
    // TODO || it relies on the DishORM already having the Dish.
    public void addReview(Review review) {
        updateTags(review);
        updateRating(review);
        getReviewsForDish(review.getDishID()).add(review);
        persistentStorageAgent.writeThrough(review);
    }

    /**
     * Incorporating the review's given user tags by:
     * 1.) Updating the TagStore (aggregated tags by type)
     * 2.) Updating the Dish object's tags via DishStore.
     * @param review
     */
    private void updateTags(Review review) {
        UUID id = review.getDishID();
        Map<String, Set<String>> tags = review.getTags();

        TagStore.getInstance().updateTags(id, tags);
        DishStore.getInstance().updateDishTags(id, tags);
    }

    /**
     * Update the DishStore's avg rating for this dish, given the new review
     */
    private void updateRating(Review review) {
        DishStore store = DishStore.getInstance();
        store.updateRating(review.getDishID(), review.getStarRating());
    }

    /**
     * Sets the mapping of {dishID : Review} stored by this ReviewStore.
     */
    public void setReviews(Map<UUID, Set<Review>> reviewsByDish) {
        this.reviewsByDish = reviewsByDish;
    }
}
