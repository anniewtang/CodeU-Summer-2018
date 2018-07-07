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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** Class representing search results. */
public class Results {

  private ArrayList<Dish> myResults;
  private String entry;
  
  /**
   * Constructs a new Feed.
   * @param userEntry what the user put in search bar
   */
  public Results(String userEntry) {
    entry = userEntry;
    myResults = new ArrayList<Dish>();
    fillResults();
  }

  /** Find and include all matching results. */
  public void fillResults() {
    // here we will generate all possible matches thru TagStore and DishStore and call addDishToResults
    // TODO: higher priority is matching dish name then tags
  }

  /** Add dish to results. */
  public void addDishToResults(/** Not sure how to structure this yet **/) {
    // TODO: might be useful to capture order nuances - prob eliminating this function
  }

  /** Return result by index. */
  public Dish getResult(int index) {
    return myResults.get(index);
  }

  /** Access event type. */
  public int getResultsCount() {
    return myResults.size();
  }

  /** Sort results by ratings **/
  public void sortByRatings() {
    // TODO: implement
  }

}
