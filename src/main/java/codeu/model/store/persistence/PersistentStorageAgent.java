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

package codeu.model.store.persistence;

import codeu.model.data.Review;
import codeu.model.data.User;
import codeu.model.store.basic.DishStore;
import codeu.model.store.basic.ReviewStore;
import codeu.model.store.basic.TagStore;
import codeu.orm.DishORM;
import codeu.model.store.persistence.PersistentDataStore;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This class is the interface between the application and PersistentDataStore, which handles
 * interactions with Google App Engine's Datastore service. Currently this class simply passes
 * function calls through to PersistentDataStore, but this could be modified to make asynchronous
 * calls or to point to a different backend storage system.
 *
 * <p>This is a singleton; the single instance is accessed through getInstance().
 */
public class PersistentStorageAgent {

  private static PersistentStorageAgent instance;

  private final PersistentDataStore persistentDataStore;

  /**
   * Access the persistent storage agent, in order to perform object-level loads and/or stores. Do
   * not call this function from a test; use getTestInstance() instead.
   */
  public static PersistentStorageAgent getInstance() {
    if (instance == null) {
      instance = new PersistentStorageAgent(new PersistentDataStore());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentDataStore.
   *
   * @param mockPersistentDataStore a mock used for testing
   */
  static PersistentStorageAgent getTestInstance(PersistentDataStore mockPersistentDataStore) {
    return new PersistentStorageAgent(mockPersistentDataStore);
  }

  // Private constructor, accessible only through singleton interface
  private PersistentStorageAgent(PersistentDataStore persistentDataStore) {
    this.persistentDataStore = persistentDataStore;
  }

  /**
   * Retrieve all User objects from the Datastore service. The returned list may be empty.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<User> loadUsers() throws PersistentDataStoreException {
    return persistentDataStore.loadUsers();
  }

  DishORM dishORM = PersistentStorageAgent.getInstance().loadDishes();
  DishStore.getInstance().setDishes(dishORM);

  codeu.model.data.query.TagORM tagORM = PersistentStorageAgent.getInstance().loadTags();
  TagStore.getInstance().setTags(tagORM);

  HashMap<UUID, Set<Review>> reviewsByDish = PersistentStorageAgent.getInstance().loadReviews();
  ReviewStore.getInstance().setReviews(reviewsByDish);

  /**
   * Retrieve all Dishes from the Datastore service in the form of a DishORM.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public DishORM loadDishes() throws PersistentDataStoreException {
    return persistentDataStore.loadDishes();
  }

  /**
   * Retrieve all Reviews from the Datastore service in the form of a map to its DishID.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public HashMap<UUID, Set<Review>> loadReviews() throws PersistentDataStoreException {
    return persistentDataStore.loadReviews();
  }

  /**
   * Retrieve all Tag objects from the Datastore service in the form of a TagORM.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public TagORM loadTags() throws PersistentDataStoreException {
    return persistentDataStore.loadTags();
  }

  /** Write a User object to the Datastore service. */
  public void writeThrough(User user) {
    persistentDataStore.writeThrough(user);
  }

  /** Write a Dish object to the Datastore service. */
  public void writeThrough(Dish dish) {
    persistentDataStore.writeThrough(dish);
  }

  /** Write a Tag object to the Datastore service. */
  public void writeThrough(Tag tag) {
    persistentDataStore.writeThrough(tag);
  }

  /** Write a Review object to the Datastore service. */
  public void writeThrough(Review review) {
    persistentDataStore.writeThrough(review);

}
