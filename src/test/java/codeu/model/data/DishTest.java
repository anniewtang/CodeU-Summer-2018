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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;


public class DishTest {
    private Dish dish;
    private UUID dishID;
    private String name;
    private String restaurant;
    private int rating;
    private HashMap<String, Set<String>> tags;
    private Set<String> restrictions;
    private Set<String> cuisine;
    private Set<String> allTagValues;


    @Before
    public void setup() {
        dishID = UUID.randomUUID();
        name = "Shitake Onigiri";
        restaurant = "Onigilly";
        rating = 4;
        tags = new HashMap<>();

        restrictions = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.VEGAN));
        cuisine = new HashSet<>(Arrays.asList(Constants.JAPANESE, Constants.ASIAN));

        tags.put(Constants.RESTRICTION, restrictions);
        tags.put(Constants.CUISINE, cuisine);

        allTagValues = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.VEGAN, Constants.JAPANESE, Constants.ASIAN));
    }

    @Test
    public void createBasicDish() {
        dish = new Dish(dishID, name, restaurant, rating, tags);
        testGetMethods();
    }

    @Test
    public void createFilledDish() {
        dish = new Dish(dishID, name, restaurant, rating, tags, allTagValues);
        testGetMethods();
    }

    private void testGetMethods() {
        Assert.assertEquals(dishID, dish.getDishID());
        Assert.assertEquals(name, dish.getDishName());
        Assert.assertEquals(restaurant, dish.getRestaurant());
        Assert.assertEquals(rating, dish.getRating());
        Assert.assertEquals(tags, dish.getTags());
        Assert.assertEquals(allTagValues, dish.getAllTagValues());
    }

    @Test
    public void testUpdateRating() {

    }

    @Test
    public testAddUserTags() {

    }
}