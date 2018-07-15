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

import codeu.TestingFramework.TestFramework;
import codeu.model.data.Constants;
import codeu.model.data.Tag;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class ReviewStoreTest extends TestFramework {
    @Test
    public void testGetReviewsForDish() {
        Assert.assertEquals(new HashSet<>(Arrays.asList(review, reviewOne)), reviewStore.getReviewsForDish(dishID));
        Assert.assertEquals(new HashSet<>(Arrays.asList(reviewTwo)), reviewStore.getReviewsForDish(dishIDTwo));
        Assert.assertEquals(new HashSet<>(), reviewStore.getReviewsForDish(UUID.randomUUID()));
    }

    /**
     * Things to check for addReview:
     * 1.) Are the dish's tags updated
     * 2.) Are the Tags are updated
     * 3.) Is the avg rating for the dish updated?
     * 4.) Is the avg rating in the DishStore updated?
     * 5.) Is it in the reviewsForDish map?
     * 6.)
     */
    @Test
    public void testAddReview() {
        // run
        reviewStore.addReview(reviewThree);

        // verify
        // check Dish's tag map
        HashSet<String> correctRestrictions = new HashSet<>();
        correctRestrictions.addAll(restrictionsTwo);
        correctRestrictions.addAll(restrictionsThree);

        HashSet<String> correctCuisine = new HashSet<>();
        correctCuisine.addAll(cuisineTwo);

        HashSet<String> correctDish = new HashSet<>();
        correctDish.addAll(dishThree);

        HashMap<String, Set<String>> correctTags = new HashMap<>();
        correctTags.put(Constants.RESTRICTION, correctRestrictions);
        correctTags.put(Constants.CUISINE, correctCuisine);
        correctTags.put(Constants.DISH, correctDish);

        Assert.assertEquals(correctTags, dishStore.getTagsForDish(dishIDTwo));

        // check Dish's allTags
        HashSet<String> correctAllTags = new HashSet<>();
        correctAllTags.addAll(allTagValuesTwo);
        correctAllTags.addAll(restrictionsThree);
        correctAllTags.addAll(dishThree);

        Assert.assertEquals(correctAllTags, dishStore.getAllTagsForDish(dishIDTwo));

        // check all affected Tags: DishTag, RestrictionsTag
        HashSet<UUID> restrictionVeganDishes = new HashSet<>(Arrays.asList(dishID));
        HashSet<UUID> restrictionVegetarianDishes = new HashSet<>(Arrays.asList(dishID, dishIDTwo));
        HashSet<UUID> restrictionGlutenFreeDishes = new HashSet<>(Arrays.asList(dishID));
        HashSet<UUID> restrictionNutFreeDishes = new HashSet<>(Arrays.asList(dishIDTwo));
        HashSet<UUID> restrictionDairyFreeDishes = new HashSet<>(Arrays.asList(dishIDTwo));
        HashSet<String> restrictionAllTags = new HashSet<>(Arrays.asList(Constants.VEGAN, Constants.VEGETARIAN, Constants.GLUTENFREE, Constants.NUTFREE, Constants.DAIRYFREE));
        HashMap<String, Set<UUID>> dishesByValueTwo = new HashMap<>();
        dishesByValueTwo.put(Constants.VEGAN, restrictionVeganDishes);
        dishesByValueTwo.put(Constants.VEGETARIAN, restrictionVegetarianDishes);
        dishesByValueTwo.put(Constants.GLUTENFREE, restrictionGlutenFreeDishes);
        dishesByValueTwo.put(Constants.NUTFREE, restrictionNutFreeDishes);
        dishesByValueTwo.put(Constants.DAIRYFREE, restrictionDairyFreeDishes);
        Tag correctRestrictionsTag = new Tag(restrictionType, dishesByValueTwo, restrictionAllTags);


        HashMap<String, Set<UUID>> dishesByValueThree = new HashMap<>();
        dishesByValueThree.put(Constants.ENTREE, new HashSet<>(Arrays.asList(dishIDTwo)));
        HashSet<String> dishesAllTags = new HashSet<>(Arrays.asList(Constants.ENTREE));
        Tag correctDishTag = new Tag(Constants.DISH, dishesByValueThree, dishesAllTags);

        Assert.assertEquals(correctRestrictionsTag, tagStore.getTagForType(Constants.RESTRICTION));
        Assert.assertEquals(correctDishTag, tagStore.getTagForType(Constants.DISH));
        Assert.assertEquals(cuisineTag, tagStore.getTagForType(Constants.CUISINE));

        // check Dish's average rating
        Assert.assertEquals(3, dishStore.getDish(dishIDTwo).getRating());

        // check DishStore's avg rating map
        Assert.assertEquals(3, dishStore.getAverageRating(dishIDTwo));

        // check reviewsForDish map
        Assert.assertEquals(new HashSet<>(Arrays.asList(reviewThree, reviewTwo)), reviewStore.getReviewsForDish(dishIDTwo));
    }

    /**
     * Add a review for a NEW dish.
     * Check to see that it was appropriately added into reviewsForDish.
     */
    @Test
    public void testAddReviewForNewDish() {
        // run + verify
        reviewStoreEmpty.addReview(review);
        Assert.assertEquals(new HashSet<>(Arrays.asList(review)), reviewStoreEmpty.getReviewsForDish(dishID));
    }
}