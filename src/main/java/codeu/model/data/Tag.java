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
import java.util.ArrayList;

/** Class representing all the Tags associated with a particualar dish */
public class Tag {
  private final UUID dishID;
  private final HashMap<String, List<String>> tags;
  private final Set<String> allTags;

  /**
   * Constructs a new Tag.
   *
   * @param dishID the ID of the dish
   */
   public Tag(UUID dishID) {
     this.dishID = dishID;
   }

   /** Adds the user tags into the private HashMap */
   public void setTags(HashMap<String, List<String>> tagsFromUser) {
     // take each key, value pair in hashmap and put them into the tags HashMap
   }

   /** Returns the type(s) of the Dish */
   public List<String> getDishType() {
     return map.get("dishType");
   }

   /** Returns the cuisine type(s) of the Dish */
   public List<String> getCuisineType() {
     return map.get("cuisineType");
   }

   /** Returns the dietary restriction(s) of the Dish */
   public List<String> getDietaryRestriction() {
     return map.get("restrictions");
   }

   /** Returns all the tag values associated with this object */
   public Set<String> getAllTags() {
     return allTags;
   }


}
