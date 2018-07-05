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

package codeu.superclass;

import codeu.model.data.Constants;
import codeu.model.data.Dish;
import org.junit.After;
import org.junit.Before;

import java.util.*;

public class TestConstants {
    public Dish dish;
    public UUID dishID;
    public String name;
    public String restaurant;
    public int rating;
    public Map<String, Set<String>> tags;
    public Set<String> restrictions;
    public Set<String> cuisine;
    public Set<String> allTagValues;

    @Before
    public void setup() {
        dishID = UUID.randomUUID();
        name = "Sesame Noodles";
        restaurant = "Asian Fusion";
        rating = 4;

        tags = new HashMap<>();
        restrictions = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.VEGAN));
        cuisine = new HashSet<>(Arrays.asList(Constants.JAPANESE, Constants.ASIAN));
        tags.put(Constants.RESTRICTION, restrictions);
        tags.put(Constants.CUISINE, cuisine);

        allTagValues = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.VEGAN, Constants.JAPANESE, Constants.ASIAN));

        dish = new Dish(dishID, name, restaurant, rating, tags, allTagValues);
    }

    @After
    public void teardown() {
        dish = null;
        dishID = null;
        restaurant = name = null;
        rating = 0;
        tags = null;
        restrictions = cuisine = allTagValues = null;

    }
}