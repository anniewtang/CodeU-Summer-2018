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
            String aboutMe = (String) entity.getProperty("aboutMe");
            User user = new User(uuid, userName, passwordHash, creationTime, aboutMe);
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
     * Loads all Dish objects from the Datastore service
     * Returns the Dishes in a DishORM via the appropriate mappings.
     *
     * @throws PersistentDataStoreException if an error was detected during the load from the
     *                                      Datastore service
     */
    public DishORM loadDishes() throws PersistentDataStoreException {
        // TODO: change this up so we initialize the DishORM first and then use its provided methods to add in dishes/ratings?

        // Setting up Data Structures to load information into
        Map<UUID, Dish> dishMap = new HashMap<>();
        Map<UUID, Integer> ratingMap = new HashMap<>();

        // Retrieve all Dishes from DataStore
        Query query = new Query("dishes");
        PreparedQuery results = datastore.prepare(query);

        for (Entity entity : results.asIterable()) {
            try {
                UUID dishID = UUID.fromString((String) entity.getProperty("dish_id"));
                String dishName = (String) entity.getProperty("dish_name");
                String restaurant = (String) entity.getProperty("restaurant");
                int rating = Integer.parseInt((String) entity.getProperty("rating"));
                Map<String, Set<String>> tags = decompressMap((EmbeddedEntity) entity.getProperty("tags"));
                Set<String> allTagValues = decompressSet((EmbeddedEntity) entity.getProperty("all_tag_values"));

                Dish dish = new Dish(dishID, dishName, restaurant, rating, tags, allTagValues);

                dishMap.put(dishID, dish);
                ratingMap.put(dishID, rating);
            } catch (Exception e) {
                // In a production environment, errors should be very rare. Errors which may
                // occur include network errors, Datastore service errors, authorization errors,
                // database entity definition mismatches, or service mismatches.
                throw new PersistentDataStoreException(e);
            }
        }
        return new DishORM(dishMap, ratingMap);
    }

    /**
     * Loads all Tag objects from the Datastore service and returns them in
     * a TagORM instance.
     *
     * @throws PersistentDataStoreException if an error was detected during the load from the
     *                                      Datastore service
     */
    public TagORM loadTags() throws PersistentDataStoreException {
        // Setting up Data Structures to load Tag information into
        Map<String, Tag> tagsByType = new HashMap<>();

        // Retrieve all Tags from DataStore
        Query query = new Query("tags");
        PreparedQuery results = datastore.prepare(query);

        for (Entity entity : results.asIterable()) {
            try {
                String tagType = (String) entity.getProperty("tag_type");
                Map<String, Set<UUID>> dishesByValue = decompressMap((EmbeddedEntity) entity.getProperty("dishes_by_value"));
                Set<String> allTagValues = decompressSet((EmbeddedEntity) entity.getProperty("all_tag_values"));

                Tag tag = new Tag(tagType, dishesByValue, allTagValues);
                tagsByType.put(tagType, tag);
            } catch (Exception e) {
                // In a production environment, errors should be very rare. Errors which may
                // occur include network errors, Datastore service errors, authorization errors,
                // database entity definition mismatches, or service mismatches.
                throw new PersistentDataStoreException(e);
            }
        }
        return new TagORM(tagsByType);
    }

    /**
     * Loads all Review objects from the Datastore service and returns them in
     * a Map that organizes all the Reviews by dish, i.e. {dishID : {Reviews for that dish}}
     *
     * @throws PersistentDataStoreException if an error was detected during the load from the
     *                                      Datastore service
     */
    public Map<UUID, Set<Review>> loadReviews() throws PersistentDataStoreException {
        // Setting up Data Structures to load Review information into
        Map<UUID, Set<Review>> reviewsByDish = new HashMap<>();

        // Retrieve all Reviews from DataStore
        Query query = new Query("reviews");
        PreparedQuery results = datastore.prepare(query);

        for (Entity entity : results.asIterable()) {
            try {
                UUID reviewID = UUID.fromString((String) entity.getProperty("review_id"));
                UUID author = UUID.fromString((String) entity.getProperty("author"));
                UUID dishID = UUID.fromString((String) entity.getProperty("dish_id"));
                Integer numStars = (Integer) entity.getProperty("num_stars");
                String desc = (String) entity.getProperty("desc");
                Map<String, Set<String>> tags = decompressMap((EmbeddedEntity) entity.getProperty("tags"));

                Review review = new Review(reviewID, author, dishID, numStars, desc, tags);
                reviewsByDish.computeIfAbsent(dishID, id -> new HashSet<>()).add(review);
            } catch (Exception e) {
                // In a production environment, errors should be very rare. Errors which may
                // occur include network errors, Datastore service errors, authorization errors,
                // database entity definition mismatches, or service mismatches.
                throw new PersistentDataStoreException(e);
            }
        }
        return reviewsByDish;
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
        userEntity.setProperty("aboutMe", user.getAboutMe().toString());
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
        dishEntity.setProperty("tags", compressMap(dish.getTags()));
        dishEntity.setProperty("all_tag_values", compressSet(dish.getAllTagValues()));

        datastore.put(dishEntity);
    }

    /**
     * Write a Tag object to the Datastore service.
     */
    public void writeThrough(Tag tag) {
        Entity tagEntity = new Entity("tags", tag.getTagType());
        tagEntity.setProperty("tag_type", tag.getTagType());
        tagEntity.setProperty("dishes_by_value", compressMap(tag.getAllDishesByValue()));
        tagEntity.setProperty("all_tag_values", compressSet(tag.getAllTagValues()));

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
        reviewEntity.setProperty("tags", compressMap(review.getTags()));

        datastore.put(reviewEntity);
    }

    /**
     * Compresses/flattens Maps into a supported Property type,
     * so we can push them to Google App Engine.
     *
     * @return a nested EmbeddedEntity (i.e. a nested "hashmap" basically)
     * {key : EmbeddedEntity} <=> {key : Set<V>}
     *
     */
    private <V> EmbeddedEntity compressMap(Map<String, Set<V>> map) {
        EmbeddedEntity e = new EmbeddedEntity();
        for (String k : map.keySet()) e.setProperty(k, compressSet(map.get(k)));
        return e;
    }

    /**
     * Compresses/flattens Sets into a supported Property type,
     * so we can push them to Google App Engine.
     *
     * @return an EmbeddedEntity with a placeholder "key".
     */
    private <V> EmbeddedEntity compressSet(Set<V> set) {
        EmbeddedEntity e = new EmbeddedEntity();
        for (V value : set) e.setProperty("Value: ", value);
        return e;
    }

    private <V> Map<String, Set<V>> decompressMap(EmbeddedEntity e) {
        Map<String, Set<V>> map = new HashMap<>();
        Map<String, Object> entityMap = e.getProperties();
        for (String k : entityMap.keySet()) {
            EmbeddedEntity setEntity = (EmbeddedEntity) entityMap.get(k);
            map.put(k, decompressSet(setEntity));
        }
        return map;
    }

    private <V> Set<V> decompressSet(EmbeddedEntity e) {
        Set<V> set = new HashSet<>();
        Map<String, Object> entityMap = e.getProperties();
        set.addAll((Collection<V>) entityMap.values());
        return set;
    }
}
