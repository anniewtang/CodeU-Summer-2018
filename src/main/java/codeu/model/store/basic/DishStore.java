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
import codeu.orm.DishORM;

import codeu.model.store.persistence.PersistentStorageAgent;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.UUID;


/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class DishStore {

    /**
     * Singleton instance of DishStore.
     */
    private static DishStore instance;

    /**
     * Returns the singleton instance of DishStore that should be shared between all servlet
     * classes. Do not call this function from a test; use getTestInstance() instead.
     */
    public static DishStore getInstance() {
        if (instance == null) {
            instance = new DishStore(PersistentStorageAgent.getInstance());
        }
        return instance;
    }

    /**
     * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
     *
     * @param persistentStorageAgent a mock used for testing
     */
    public static DishStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
        return new DishStore(persistentStorageAgent);
    }

    /**
     * The PersistentStorageAgent responsible for loading Dishes from and saving Dishes
     * to Datastore.
     */
    private PersistentStorageAgent persistentStorageAgent;

    /**
     * The in-memory store of DishORM.
     */
    private DishORM orm;

    /**
     * This class is a singleton, so its constructor is private. Call getInstance() instead.
     * Initializes a new orm (also a singleton), if this is the first time we're loading DishStore.
     */
    private DishStore(PersistentStorageAgent persistentStorageAgent) {
        this.persistentStorageAgent = persistentStorageAgent;
    }

    public Dish getDish(UUID id) {
        return orm.getDish(id);
    }

    public int getAverageRating(UUID id) {
        return orm.getAverageRating(id);
    }

    public int getNumReviews(UUID id) {
        return orm.getNumReviews(id);
    }

    /**
     * Returns {tagType : {tagValues}} for the given Dish
     */
    public Map<String, Set<String>> getTagsForDish(UUID dishID) {
        return orm.getTagsForDish(dishID);
    }

    /**
     * Add a new Dish to the current set of dishes known to the application.
     */
    public void addDish(Dish dish) {
        orm.addDish(dish);
        persistentStorageAgent.writeThrough(dish);
    }

    /**
     * Updates the average rating of a dish, given a NEW rating from user.
     * Also write this updated Dish into storage.
     *
     * @param id   id of the dish we're updating
     * @param rate rate (# stars) new user gave this Dish
     * @method updateRating
     */
    public void updateRating(UUID id, int rate) {
        Dish updatedDish = orm.updateRating(id, rate);
        persistentStorageAgent.writeThrough(updatedDish);
    }

    /**
     * Updates the tags belong to a dish after a NEW user rates it.
     * Also writes this updated Dish into storage.
     *
     * @param id       id of the dish we're updating
     * @param userTags the tags the new user assigns to this dish
     * @method updateDishTags
     */
    public void updateDishTags(UUID id, Map<String, Set<String>> userTags) {
        Dish updatedDish = orm.updateDishTags(id, userTags);
        persistentStorageAgent.writeThrough(updatedDish);
    }

    /**
     * Sets the Handler object (Dishes + Querying/Setting methods) in the DishStore.
     */
    public void setDishes(DishORM dishORM) {
        this.orm = dishORM;
    }
}
