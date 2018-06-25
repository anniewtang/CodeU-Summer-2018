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

import java.time.Instant;
import java.util.UUID;

/** Class representing a Dish. Dish objects can be updated, as more user tags are added. */
public class Dish {

  private final UUID dishID;
  private final String dishName;
  private final String restuarant;
  private final Location location;
  private final HashMap<String, Set<String>> tags;

  // maps dishes to all their tags, organized by the tag category/type
  private HashMap<UUID, HashMap<String, Set<String>>> tagsByDish;

  /**
   * Constructs a new Dish object.
   *
   * @param id the ID of this dish
   * @param name the name of the dish
   * @param restaurant the name of the restaurant where this dish came from
   * @param loc the location of the dish (/restaurant)
   * // TODO: MAKE IT SUCH THAT USER TAGS ARE ADDED IN **SEPARATELY** AFTER INITILIAZATION
   */
   public Dish(UUID id, String name, String restaurant, Location loc) {
     this.dishID = id;
     this.dishName = name;
     this.restaurant = restaurant;
     this.location = loc;
   }

   /** Returns id of the dish */
   public String getDishID() {
     return this.dishID;
   }

   /** Returns name of the dish */
   public String getDishName() {
     return this.dishName;
   }

   /** Returns name of dish restaurant */
   public String getResturant() {
     return this.restuarant;
   }

   /** Returns location of the dish */
   public Location getLocation() {
     // potentially don't just return the Location object itself
     // depends on how we're representing location (via Yelp API?)
     // if we're using a wrapper class or not
     return this.location;
   }

   /** Return the number of reviews Dish has*/
   public int getNumReviews() {
    // pull from Dish store
   }

   /** Returns all the Reviews Dish has */
   public List<Review> getReviews() {
     // pull from Dish store
   }

   /** Returns all the Tags Dish has */
   // TODO: may have to delete, to ensure that we're using handler properly
   public List<String> getTags() {
     return tags.getAllTags();
   }
