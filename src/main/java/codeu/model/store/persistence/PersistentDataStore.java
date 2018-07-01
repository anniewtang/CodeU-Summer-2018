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

import codeu.model.data.Dish;
import codeu.model.data.Review;
import codeu.model.data.Tag;
import codeu.model.data.User;
import codeu.orm.DishORM;
import codeu.orm.TagORM;
import com.google.appengine.api.datastore.*;

import java.time.Instant;
import java.util.*;

/**
 * This class handles all interactions with Google App Engine's Datastore service. On startup it
 * sets the state of the applications's data objects from the current contents of its Datastore. It
 * also performs writes of new or modified objects back to the Datastore.
 */
public class PersistentDataStore {

    // Handle to Google AppEngine's Datastore service.
    private DatastoreService datastore;

    /**
     * Constructs a new PersistentDataStore and sets up its state to begin loading objects from the
     * Datastore service.
     */
    public PersistentDataStore() {
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    /**
     * Loads all User objects from the Datastore service and returns them in a List.
     *
     * @throws PersistentDataStoreException if an error was detected during the load from the
     *                                      Datastore service
     */
    public List<User> loadUsers() throws PersistentDataStoreException {

        List<User> users = new ArrayList<>();

        // Retrieve all users from the datastore.
        Query query = new Query("chat-users");
        PreparedQuery results = datastore.prepare(query);

        for (Entity entity : results.asIterable()) {
            try {
                UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
                String userName = (String) entity.getProperty("username");
                String passwordHash = (String) entity.getProperty("password_hash");
                Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
                User user = new User(uuid, userName, passwordHash, creationTime);
                users.add(user);
            } catch (Exception e) {
                // In a production environment, errors should be very rare. Errors which may
                // occur include network errors, Datastore service errors, authorization errors,
                // database entity definition mismatches, or service mismatches.
                throw new PersistentDataStoreException(e);
            }
        }

        return users;
    }

    /**
     * Loads all Dish objects from the Datastore service and returns them in a DishORM
     * with the appropriate mappings.
     *
     * @throws PersistentDataStoreException if an error was detected during the load from the
     *                                      Datastore service
     */
    public DishORM loadDishes() throws PersistentDataStoreException {
//      public DishORM(HashMap<UUID, Dish> dishMap, HashMap<UUID, Integer> ratingMap)
        HashMap<UUID, Dish> dishMap = new HashMap<>();
        HashMap<UUID, Integer> ratingMap = new HashMap<>();

        // Retrieve all Dishes from DataStore
        Query query = new Query("dishes");


//      // Retrieve all users from the datastore.
//      Query query = new Query("chat-users");
//      PreparedQuery results = datastore.prepare(query);
//
//      for (Entity entity : results.asIterable()) {
//          try {
//              UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
//              String userName = (String) entity.getProperty("username");
//              String passwordHash = (String) entity.getProperty("password_hash");
//              Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
//              User user = new User(uuid, userName, passwordHash, creationTime);
//              users.add(user);
//          } catch (Exception e) {
//              // In a production environment, errors should be very rare. Errors which may
//              // occur include network errors, Datastore service errors, authorization errors,
//              // database entity definition mismatches, or service mismatches.
//              throw new PersistentDataStoreException(e);
//          }
//      }
//
//      return users;

        return null;
    }

    /**
     * Loads all Review objects from the Datastore service and returns them in
     * a Map that organizes all the Reviews by dish, i.e. {dishID : {Reviews for that dish}}
     *
     * @throws PersistentDataStoreException if an error was detected during the load from the
     *                                      Datastore service
     */
    public HashMap<UUID, Set<Review>> loadReviews() throws PersistentDataStoreException {
        return null;
    }

    /**
     * Loads all Tag objects from the Datastore service and returns them in
     * a TagORM instance.
     *
     * @throws PersistentDataStoreException if an error was detected during the load from the
     *                                      Datastore service
     */
    public TagORM loadTags() throws PersistentDataStoreException {
        return null;
    }

    /**
     * Write a User object to the Datastore service.
     */
    public void writeThrough(User user) {
        Entity userEntity = new Entity("chat-users", user.getId().toString());
        userEntity.setProperty("uuid", user.getId().toString());
        userEntity.setProperty("username", user.getName());
        userEntity.setProperty("password_hash", user.getPasswordHash());
        userEntity.setProperty("creation_time", user.getCreationTime().toString());
        datastore.put(userEntity);
    }

    /**
     * Write a Dish object to the Datastore service.
     */
    public void writeThrough(Dish dish) {
        Entity dishEntity = new Entity("dishes", dish.getDishID().toString());
        // TODO: ask why convert everything to string?
        dishEntity.setProperty("dish_id", dish.getDishID().toString());
        dishEntity.setProperty("dish_name", dish.getDishName());
        dishEntity.setProperty("restaurant", dish.getRestaurant());
        dishEntity.setProperty("rating", dish.getRestaurant().toString());
        dishEntity.setProperty("tags", dish.getTags());
        dishEntity.setProperty("all_tags", dish.getAllTags());
//        dishEntity.setProperty("creation_time", dish.getCreationTime().toString());
        datastore.put(dishEntity);
    }

    /**
     * Write a Tag object to the Datastore service.
     */
    public void writeThrough(Tag tag) {
        Entity tagEntity = new Entity("tags", tag.getTagType());
        tagEntity.setProperty("tag_type", tag.getTagType());
        tagEntity.setProperty("dishes_by_value", tag.getAllDishesByValue());
        tagEntity.setProperty("all_tag_values", tag.getAllTagValues());
        datastore.put(tagEntity);
    }

    /**
     * Write a Review object to the Datastore service.
     */
    public void writeThrough(Review review) {
        Entity reviewEntity = new Entity("reviews", review.getReviewID().toString());
        reviewEntity.setProperty("review_id", review.getReviewID().toString());
        reviewEntity.setProperty("author", review.getAuthor());
        reviewEntity.setProperty("dish_id", review.getDishID());
        reviewEntity.setProperty("num_stars", review.getStarRating());
        reviewEntity.setProperty("desc", review.getDescription());
        datastore.put(reviewEntity);
    }
}
