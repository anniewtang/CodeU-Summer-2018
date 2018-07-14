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

}
