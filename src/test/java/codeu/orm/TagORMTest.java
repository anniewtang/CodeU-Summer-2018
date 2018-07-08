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

import codeu.TestingFramework.TestFramework;
import codeu.model.data.Constants;
import codeu.model.data.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;


public class TagORMTest extends TestFramework {
    private HashMap<String, Set<String>> correctTags;
    private HashMap<String, Set<String>> newTags;

    @Before
    public void setup() {
        HashSet<String> correctRestrictions = new HashSet<>();
        correctRestrictions.addAll(restrictions);
        correctRestrictions.addAll(restrictionsOne);

        HashSet<String> correctCuisine = new HashSet<>();
        correctCuisine.addAll(cuisine);
        correctCuisine.addAll(cuisineOne);

        correctTags = new HashMap<>();
        correctTags.put(Constants.RESTRICTION, correctRestrictions);
        correctTags.put(Constants.CUISINE, correctCuisine);

        newTags = new HashMap<>();
        newTags.put(Constants.DISH, new HashSet<>(Arrays.asList(Constants.NOODLE, Constants.ENTREE)));
        correctTags.putAll(newTags);
    }

    @Test
    public void testGetTagForType() {
        Assert.assertEquals(cuisineTag, tagORM.getTagForType(Constants.CUISINE));
        Assert.assertEquals(restrictionTag, tagORM.getTagForType(Constants.RESTRICTION));

        Tag newDishTag = tagORM.getTagForType(Constants.DISH);
        Assert.assertEquals(Constants.DISH, newDishTag.getTagType());
        Assert.assertEquals(0, newDishTag.getAllDishesByValue().size());
        Assert.assertEquals(0, newDishTag.getAllTagValues().size());
    }

    @Test
    public void testUpdateTags() {
        // run
        Set<Tag> updatedTags = tagORM.updateTags(dishID, newTags);

        // verify
        Set<Tag> correctUpdatedTags = new HashSet<>(Arrays.asList(tagORM.getTagForType(Constants.DISH)));
        Assert.assertEquals(updatedTags, correctUpdatedTags);

        // verify tag objects affected
        Tag dishTag = tagORM.getTagForType(Constants.DISH);
        Assert.assertEquals(new HashSet<>(Arrays.asList(Constants.NOODLE, Constants.ENTREE)), dishTag.getAllTagValues());
        Assert.assertEquals(new HashSet<>(Arrays.asList(dishID)), dishTag.getDishesByValue(Constants.NOODLE));
        Assert.assertEquals(new HashSet<>(Arrays.asList(dishID)), dishTag.getDishesByValue(Constants.ENTREE));

        HashMap<String, Set<UUID>> correctDishesByValue = new HashMap<>();
        correctDishesByValue.put(Constants.NOODLE, new HashSet<>(Arrays.asList(dishID)));
        correctDishesByValue.put(Constants.ENTREE, new HashSet<>(Arrays.asList(dishID)));
        Assert.assertEquals(correctDishesByValue, dishTag.getAllDishesByValue());
    }
}