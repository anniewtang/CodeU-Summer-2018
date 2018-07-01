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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class ReviewStore {

  /** Singleton instance of ReviewStore. */
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

  /** The in-memory mapping of dishIDs to Reviews */
  private HashMap<UUID, Set<Review>> reviewsByDish;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private ReviewStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
  }

  /** Add a new review to the current set of reviews known to the application. */
  public void addReview(Review review) {
    Set<Review> reviews = reviewsByDish.get(review.getDishID());
    if (reviews == null) {
        reviews = new HashSet<>();
        reviewsByDish.put(review.getDishID(), reviews);
    }
    reviews.add(review);
    updateTags(review);
    updateRating(review);
    persistentStorageAgent.writeThrough(review);
  }

  /** Update the TagStore's tags for querying, given the new review */
  private void updateTags(Review review) {
    TagStore store = TagStore.getInstance();
    store.updateTags(review.getDishID(), review.getTags());
  }

  /** Update the DishStore's avg rating for this dish, given the new review */
  private void updateRating(Review review) {
    DishStore store = DishStore.getInstance();
    store.updateRating(review.getDishID(), review.getStarRating());
  }
  
  /** Sets the mapping of {dishID : Review} stored by this ReviewStore. */
  public void setReviews(HashMap<UUID, Set<Review>> reviewsByDish) {
    this.reviewsByDish = reviewsByDish;
  }
}
