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
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Each Tag object is identified by its tag type/category.
 * Each Tag object contains all tag values associated with this tag type/category,
 * as well as all the Dishes that have that tag value.
 */
public class Tag {
  private final String tagType; // name of tag type/category
  private final HashMap<String, Set<UUID>> dishesByValue; // {tagValue : {dishIDs}}
  private final Set<String> allTags; // {tagValues}

  /**
   * Constructs a new Tag.
   *
   * @param dishID the ID of the dish
   */
   public Tag(String type) {
     this.tagType = type;
   }

   /**
    * Associates a Dish with a particular tag value, for querying.
    * @method addDishToTag
    * @param  tagValue     the user tag given to the Dish
    * @param  dishID       id of the dish we're associating
    */
   public void addDishToTag(String tagValue, UUID dishID) {
     Set<UUID> dishes = getDishesByValue(tagValue);
     dishes.put(UUID);
   }

   /**
    * Retrieves the dishes that have been tagged with a certain value,
    * under this particular tag type/category.
    * @method getDishesByValue
    * @param  value            the tag value we're querying dishes for
    * @return                  a set of all dishIDs tagged with this value
    */
   public Set<UUID> getDishesByValue(String value) {
     Set<UUID> dishes = this.dishesByValue.get(value);
     if (dishes == null) {
       dishes = new HashSet<>();
     }
     return dishes;
   }

   // /** Adds the user tags into the private HashMap */
   // public void setTags(HashMap<String, List<String>> tagsFromUser) {
   //   // take each key, value pair in hashmap and put them into the tags HashMap
   // }
   //
   // /** Returns the type(s) of the Dish */
   // public List<String> getDishType() {
   //   return map.get("dishType");
   // }
   //
   // /** Returns the cuisine type(s) of the Dish */
   // public List<String> getCuisineType() {
   //   return map.get("cuisineType");
   // }
   //
   // /** Returns the dietary restriction(s) of the Dish */
   // public List<String> getDietaryRestriction() {
   //   return map.get("restrictions");
   // }

   /** Returns all the tag values associated with this object */
   public Set<String> getAllTags() {
     return allTags;
   }


}
