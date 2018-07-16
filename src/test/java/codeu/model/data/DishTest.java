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

package codeu.model.data;

import codeu.TestingFramework.TestFramework;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;


public class DishTest extends TestFramework {
    @Test
    public void testCreateDish() {
        // run
        Dish basicDish = new Dish(dishID, name, restaurant);

        // verify
        Assert.assertEquals(dishID, basicDish.getDishID());
        Assert.assertEquals(name, basicDish.getDishName());
        Assert.assertEquals(restaurant, basicDish.getRestaurant());
        Assert.assertEquals(0, basicDish.getRating());
        Assert.assertEquals(new HashMap<>(), basicDish.getTags());
        Assert.assertEquals(new HashSet<>(), basicDish.getAllTagValues());
    }

    @Test
    public void testSetRating() {
        // run
        dish.setRating(2);

        // verify
        Assert.assertEquals(2, dish.getRating());
    }

    @Test
    public void testAddUserTags() {
        // setup
        Map<String, Set<String>> newUserTags = new HashMap<>();
        newUserTags.put(Constants.RESTRICTION, new HashSet<>(Arrays.asList(Constants.DAIRYFREE)));
        newUserTags.put(Constants.CUISINE, new HashSet<>(Arrays.asList(Constants.JAPANESE)));

        // run
        dish.addUserTags(newUserTags);

        // verify
        Map<String, Set<String>> correctUpdatedUserTags = new HashMap<>();
        correctUpdatedUserTags.put(Constants.RESTRICTION, new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.VEGAN, Constants.DAIRYFREE, Constants.GLUTENFREE)));
        correctUpdatedUserTags.put(Constants.CUISINE, new HashSet<>(Arrays.asList(Constants.JAPANESE, Constants.CHINESE, Constants.ASIAN)));

        Set<String> correctUpdatedAllTags = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.VEGAN, Constants.GLUTENFREE, Constants.CHINESE, Constants.JAPANESE, Constants.ASIAN, Constants.DAIRYFREE));

        Assert.assertEquals(correctUpdatedUserTags, dish.getTags());
        Assert.assertEquals(correctUpdatedAllTags, dish.getAllTagValues());
    }
}