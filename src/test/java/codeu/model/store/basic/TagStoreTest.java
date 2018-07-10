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

public class TagStoreTest extends TestFramework {
    /**
     * Checks if:
     * 1.) Tag objects are updated.
     * 2.) Dish has updated userTags as well.
     */
    @Test
    public void testUpdateTags() {
        // setup
        Map<String, Set<String>> newUserTags = new HashMap<>();
        newUserTags.put(Constants.RESTRICTION, new HashSet<>(Arrays.asList(Constants.DAIRYFREE)));
        newUserTags.put(Constants.DISH, new HashSet<>(Arrays.asList(Constants.ENTREE)));

        // run
        tagStore.updateTags(dishIDTwo, newUserTags);

        // verify
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
    }

}