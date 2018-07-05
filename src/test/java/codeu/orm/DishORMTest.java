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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        dishMap.put(dish.getDishID(), dish);
        avgRatingMap.put(dish.getDishID(), dish.getRating());
        orm = new DishORM(dishMap, avgRatingMap);
    }

    @Test
    public void testGetDish() {
        // run + verify
        Dish d = orm.getDish(dish.getDishID());
        Assert.assertEquals(d, dish);
    }

    @Test
    public void testGetAverageRating() {
        // run + verify
        Assert.assertEquals(4, orm.getAverageRating(dish.getDishID()));
    }

    @Test
    public void testGetNumReviews() {

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