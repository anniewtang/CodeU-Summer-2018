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
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DishStoreTest extends TestFramework {
    HashMap<String, Set<String>> correctTags;

    @Test
    public void testGetterMethods() {
        Assert.assertEquals(dish, dishStore.getDish(dishID));
        Assert.assertTrue(dishStore.getDishesOfRating(4).contains(dishID));
        Assert.assertTrue(dishStore.getDishesOfRating(2).contains(dishIDTwo));
    }


    @Test
    public void testGetTagsForDish() {
        HashSet<String> correctRestrictions = new HashSet<>();
        correctRestrictions.addAll(restrictions);
        correctRestrictions.addAll(restrictionsOne);

        HashSet<String> correctCuisine = new HashSet<>();
        correctCuisine.addAll(cuisine);
        correctCuisine.addAll(cuisineOne);

        correctTags = new HashMap<>();
        correctTags.put(Constants.RESTRICTION, correctRestrictions);
        correctTags.put(Constants.CUISINE, correctCuisine);

        Assert.assertEquals(correctTags, dishStore.getTagsForDish(dishID));
        Assert.assertEquals(tagsTwo, dishStore.getTagsForDish(dishIDTwo));
    }

    @Test
    public void testAddDish() {
        DishStore dishStore = DishStore.getTestInstance(mockPersistentStorageAgent);

        dishStore.addDish(dish);
        dishStore.addDish(dishTwo);

        Assert.assertEquals(dish, dishStore.getDish(dishID));
        Assert.assertEquals(dishTwo, dishStore.getDish(dishIDTwo));
    }

    @Test
    public void testUpdateRating() {
        dishStore.updateRating(dishIDTwo, 7);
        Assert.assertTrue(dishStore.getDishesOfRating(4).contains(dishIDTwo));
        Assert.assertTrue(!dishStore.getDishesOfRating(2).contains(dishIDTwo));

        dishStore.updateRating(dishID, 7);
        Assert.assertTrue(dishStore.getDishesOfRating(5).contains(dishID));
        Assert.assertTrue(!dishStore.getDishesOfRating(4).contains(dishID));
    }

    @Test
    public void testSetDishes() {
        // setup
        HashSet<String> correctRestrictions = new HashSet<>();
        correctRestrictions.addAll(restrictions);
        correctRestrictions.addAll(restrictionsOne);

        HashSet<String> correctCuisine = new HashSet<>();
        correctCuisine.addAll(cuisine);
        correctCuisine.addAll(cuisineOne);

        correctTags = new HashMap<>();
        correctTags.put(Constants.RESTRICTION, correctRestrictions);
        correctTags.put(Constants.CUISINE, correctCuisine);

        HashMap<String, Set<String>> newTags = new HashMap<>();
        newTags.put(Constants.DISH, new HashSet<>(Arrays.asList(Constants.NOODLE, Constants.ENTREE)));
        correctTags.putAll(newTags);

        // run + verify
        dishStore.updateDishTags(dishID, newTags);
        Assert.assertEquals(correctTags, dishStore.getTagsForDish(dishID));
    }
}