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
import codeu.model.store.persistence.PersistentStorageAgent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class DishStoreTest extends TestFramework {
    @Mock private PersistentStorageAgent mockPersistentStorageAgent;
    private DishStore dishStore;

    @Before
    public void setup() {
        dishStore = DishStore.getTestInstance(mockPersistentStorageAgent);
    }

    @Test
    public void testAddDish() {
        dishStore.addDish(dish);
        Mockito.verify(dishStore.orm).addDish(dishID, dish);
        Mockito.verify(mockPersistentStorageAgent).writeThrough(dish);

        Assert.assertEquals(dish, dishStore.orm.getDish(dishID));

    }

    @Test
    public void testGetTagsForDish() {

    }

    @Test
    public void testUpdateRating() {

    }

    @Test
    public void testUpdateDishTags() {

    }

    @Test
    public void testSetDishes() {

    }



}