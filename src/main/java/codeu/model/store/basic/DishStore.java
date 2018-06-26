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
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;


/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class DishStore {

  /** Singleton instance of DishStore. */
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

  /** The in-memory store of DishHandler. */
  private DishHandler handler;

  /**
   * This class is a singleton, so its constructor is private. Call getInstance() instead.
   * Initializes a new handler (also a singleton), if this is the first time we're loading DishStore.
   */
  private DishStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    handler = new DishHandler();
  }

  /** Add a new Dish to the current set of dishes known to the application. */
  public void addDish(Dish dish) {
    handler.addDish(dish);
    persistentStorageAgent.writeThrough(handler);
  }

  /** Returns {tagType : {tagValues}} for the given Dish */
  public HashMap<String, Set<String>> getTagsForDish(UUID dishID) {
    return handler.getTagsForDish(dishID);
  }

  /** Sets the List of Conversations stored by this ConversationStore. */
  public void setHandler(DishHandler handler) {
    this.handler = handler;
  }
}
