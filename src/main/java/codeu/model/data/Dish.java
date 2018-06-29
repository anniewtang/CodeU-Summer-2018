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

/** Class representing a Dish. Dish objects are static and finalized during initialization */
public class Dish {

  private final UUID dishID;
  private final String dishName;
  private final String restuarant;
  private final Location location;
  private final Tag tags;

  /**
   * Constructs a new Dish object.
   *
   * @param id the ID of this dish
   * @param name the name of the dish
   * @param restaurant the name of the restaurant where this dish came from
   * @param loc the location of the dish (/restaurant)
   * @param userTags the first set of tags the user provides during the first review
   */
   public Dish(UUID id, String name, String restaurant, Location loc, HashMap<String, List<String>> userTags) {
     this.dishID = id;
     this.dishName = name;
     this.restaurant = restaurant;
     this.location = loc;
     this.tags = new Tag(id);
     tags.setTags(userTags);
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
   public List<String> getTags() {
     return tags.getAllTags();
   }
