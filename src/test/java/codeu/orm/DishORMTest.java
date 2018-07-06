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

package codeu.orm;

import codeu.model.data.Dish;
import codeu.model.data.Review;
import codeu.TestingFramework.TestFramework;
import codeu.model.store.basic.ReviewStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DishORMTest extends TestFramework {
    private Map<UUID, Dish> dishMap;
    private Map<UUID, Integer> avgRatingMap;
    private DishORM orm;

    private Set<Review> reviews;

    @Before
    public void setup() {
        dishMap = new HashMap<>();
        dishMap.put(dishID, dish);
        dishMap.put(dishIDTwo, dishTwo);

        avgRatingMap = new HashMap<>();
        avgRatingMap.put(dishID, dish.getRating());
        avgRatingMap.put(dishIDTwo, dishTwo.getRating());
        orm = new DishORM(dishMap, avgRatingMap);
    }

    @Test
    public void testGetDish() {
        // run + verify
        Dish d = orm.getDish(dishID);
        Assert.assertEquals(d, dish);

        Dish d2 = orm.getDish(dishIDTwo);
        Assert.assertEquals(d2, dishTwo);
    }

    @Test
    public void testGetAverageRating() {
        // run + verify
        Assert.assertEquals(4, orm.getAverageRating(dish.getDishID()));
        Assert.assertEquals(3, orm.getAverageRating(dishTwo.getDishID()));
    }

    @Test
    public void testGetNumReviews() {
        // TODO: Mock ReviewStore
        Assert.assertEquals(2, orm.getNumReviews(dish.getDishID()));
        Assert.assertEquals(1, orm.getNumReviews(dishTwo.getDishID()));
    }

    @Test
    public void testGetTagsForDish() {

    }

    @Test
    public void testAddDish() {

    }

    @Test
    public void testUpdateRating() {

    }

    @Test
    public void testUpdateDishTags() {

    }
}