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

package codeu.model.data.query;

import codeu.model.data.Dish;
import java.util.UUID;

public class DishQuery {
  private HashMap<UUID, Dish> dishMap; // maps dishIDs to Dish objects
  private HashMap<UUID, int> ratingMap; // maps dishIDs to average star ratings

  public DishQuery(HashMap<UUID, Dish> dishMap, HashMap<UUID, int> ratingMap) {
    this.dishMap = dishMap;
    this.ratingMap = ratingMap;
  }

  /** Adds a new Dish into our dishMap*/
  public void addDish(UUID id, Dish dish) {
    this.dishMap.put(id, dish);
  }

  /** Adds/updates the average rating for this dish */
  public void addRating(UUID id, int rate) {
    int oldRating = 0;
    if (dishExists(id)) {
      oldRating = getRating(id);
    }
    return (oldRating + rate) / 2;
  }

  /** Returns ture if this dish exists in our map */
  public boolean dishExists(UUID id) {
    returns this.dishMap.containsKey(id);
  }

  /** Returns DishMap */
  public HashMap<UUID, Dish> getDishMap {
    return this.dishMap;
  }

  /** Returns ratingMap */
  public HashMap<UUID, int> getRatingMap {
    return this.ratingMap;
  }

  /** Get rating of a particular dish.
    * Only called under the conditions that ID exists in our dishMap */
  public int getRating(UUID id) {
    int rating = this.ratingMap.get(id);
    return rating;
  }

}
