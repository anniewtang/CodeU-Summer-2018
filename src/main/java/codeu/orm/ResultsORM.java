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

package codeu.orm;

import java.util.*;

import codeu.model.data.Constants;
import codeu.model.store.basic.ContentManager;
import codeu.model.data.Review;
import codeu.model.data.Dish;

/**
 * Class representing search results.
 */
public class ResultsORM {
    /**
     * Used for results.jsp to query search results based on user preferences.
     * @param entries string containing user's requirements
     * @return Set of Dishes that match the user's requirements
     */
    public static Set<Dish> getResults(String entries) {
        Map<String, Set<String>> queryTags = extractQueryTags(entries);

        // TODO: replace new HashSet<>() with extractRatings(entries) when there's time
        Set<Integer> ratings = new HashSet<>();
        return ContentManager.queryAndSort(queryTags, ratings, true);
    }

    /**
     * Helper method used by {@link #getResults}
     * @param entries user preferences
     * @return map form of user preferences (queryTags) for
     */
    private static Map<String, Set<String>> extractQueryTags(String entries) {
        Map<String, Set<String>> results = new HashMap<>();
        if (entries.length() < 3) {
            return results;
        }

        String[] partitionedEntries = entries.split(", ");
        for (String entry : partitionedEntries) {
            switch (entry.charAt(0)) {
                case 'C':
                    if (!results.containsKey("Cuisine")) {
                        results.put("Cuisine", new HashSet<>());
                    }
                    results.get("Cuisine").add(entry.substring(2));
                    break;

                case 'D':
                    if (!results.containsKey("Dish")) {
                        results.put("Dish", new HashSet<>());
                    }
                    results.get("Dish").add(entry.substring(2));
                    break;

                case 'R':
                    if (!results.containsKey("Restriction")) {
                        results.put("Restriction", new HashSet<>());
                    }
                    results.get("Restriction").add(entry.substring(2));
                    break;
            }
        }
        return results;
    }

}
