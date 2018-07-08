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
import codeu.model.store.persistence.PersistentStorageAgent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DishORMTest extends TestFramework {
    @Test
    public void testGetDish() {
        // run + verify
        Dish d = dishORM.getDish(dishID);
        Assert.assertEquals(d, dish);

        Dish d2 = dishORM.getDish(dishIDTwo);
        Assert.assertEquals(d2, dishTwo);
    }

    @Test
    public void testGetAverageRating() {
        // run + verify
        Assert.assertEquals(4, dishORM.getAverageRating(dish.getDishID()));
        Assert.assertEquals(3, dishORM.getAverageRating(dishTwo.getDishID()));
    }

    @Test
    public void testGetNumReviews() {
         Assert.assertEquals(2, dishORM.getNumReviews(dish.getDishID())); // ID: d76a3236-9fc3-452a-aef0-ad94ca7517d5
         Assert.assertEquals(1, dishORM.getNumReviews(dishTwo.getDishID())); // ID: 6b772f25-6b2b-4479-a0b5-31b2fcbf777e
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