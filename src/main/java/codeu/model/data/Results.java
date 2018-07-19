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

import java.util.*;

import codeu.model.store.basic.ContentManager;
import codeu.model.data.Review;
import codeu.model.data.Dish;

/** Class representing search results. */
public class Results {

  private ContentManager cm;
  private Set<Dish> myResults;
  private Iterator<Dish> itr;
  private Map<String, Set<String>> queryTags;

  /**
   * Constructs a new Feed.
   * @param entries what the user put in search bar
   */
  public Results(String entries) {
    cm = new ContentManager();
    myResults = new HashSet<Dish>();
    queryTags = new HashMap<String, Set<String>>();
    processEntry(entries);
    fillResults();
  }

  /** Break down user input into useful key words */
  public void processEntry(String entries) {
    // TODO: make sure no duplicates
    String[] partitionedEntries = entries.split(", ");
    for (String entry : partitionedEntries) {
        switch (entry.charAt(0)) {
          case 'C':
            if (!queryTags.containsKey("Cuisine")) {
                queryTags.put("Cuisine", new HashSet<String>());
            }
            queryTags.get("Cuisine").add(entry.substring(2));
            break;

          case 'D':
            if (!queryTags.containsKey("Dish")) {
                queryTags.put("Dish", new HashSet<String>());
            }
            queryTags.get("Dish").add(entry.substring(2));
            break;

          case 'R':
            if (!queryTags.containsKey("Restriction")) {
                queryTags.put("Restriction", new HashSet<String>());
            }
            queryTags.get("Restriction").add(entry.substring(2));
            break;
        }
    }
  }

  /** Find and include all matching results. */
  public void fillResults() {
      createFakeDishes();
      Set<Integer> ratings = new HashSet<Integer>();
      ratings.add(3);
      ratings.add(4);
      ratings.add(5);
      myResults = cm.queryByTags(queryTags);
      itr = myResults.iterator();
  }

/* TODO: REMOVE */
public void createFakeDishes() {
    Map<String, Set<String>> tags = new HashMap<>();
    tags.put(Constants.RESTRICTION, new HashSet<>(Arrays.asList(Constants.DAIRYFREE)));
    tags.put(Constants.CUISINE, new HashSet<>(Arrays.asList(Constants.JAPANESE)));

    Set<String> allTags = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.VEGAN, Constants.GLUTENFREE, Constants.CHINESE, Constants.JAPANESE, Constants.ASIAN, Constants.DAIRYFREE));

    Dish dish1 = new Dish(UUID.randomUUID(), "California Roll", "Katana Sushi", 4, tags, allTags);
    Dish dish2 = new Dish(UUID.randomUUID(), "Beef Barg", "Shamshiri Grill", 3, tags, allTags);

    Review review1 = new Review(UUID.randomUUID(), UUID.randomUUID(), dish1.getDishID(), 4, "OMG the cilantro roll is AMAZING!!", tags);
    Review review2 = new Review(UUID.randomUUID(), UUID.randomUUID(), dish2.getDishID(), 4, "Juciest meat ever! Get as medium rare!", tags);

    cm.addNewDishAndFirstReview(dish1, review1);
    cm.addNewDishAndFirstReview(dish2, review2);

    myResults.add(dish1);
    myResults.add(dish2);
}

  /** Return result by index. */
  public Dish getNextResult() {
    return itr.next();
  }

  /** Access event type. */
  public int getResultsCount() {
    return myResults.size();
  }

}
