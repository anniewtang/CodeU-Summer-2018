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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class TagTest {
    private Tag tag;
    private String type = Constants.CUISINE;
    private HashMap<String, Set<UUID>> dishesByValue;
    private HashSet<UUID> chineseDishes;
    private Set<String> allTagValues;


    @Before
    public void setup() {
        // setup
        dishesByValue = new HashMap<>();
        chineseDishes = new HashSet<>();
        chineseDishes.addAll(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        dishesByValue.put(Constants.CHINESE, chineseDishes);
        Set<UUID> asianDishes = new HashSet<>();
        asianDishes.addAll(Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));
        dishesByValue.put(Constants.ASIAN, asianDishes);

        allTagValues = new HashSet<>();
        allTagValues.addAll(Arrays.asList(Constants.CHINESE, Constants.ASIAN));

        // initializing the Tag object
        tag = new Tag(type, dishesByValue, allTagValues);
    }

    /**
     * Checks the construction of a basic Tag initializer.
     * Doesn't use setup()
     */
    @Test
    public void testSkeletonTag() {
        // setup
        String type = "Tag Type";
        Tag basicTag = new Tag(type);

        // verify
        Assert.assertEquals(type, basicTag.getTagType());
        Assert.assertTrue(basicTag.getAllDishesByValue().size() == 0);
        Assert.assertTrue(basicTag.getAllTagValues().size() == 0);
    }

    @Test
    public void testFilledTag() {
        // verify
        Assert.assertEquals(Constants.CUISINE, tag.getTagType());
        Assert.assertEquals(dishesByValue, tag.getAllDishesByValue());
        Assert.assertEquals(allTagValues, tag.getAllTagValues());
        Assert.assertEquals(chineseDishes, tag.getDishesByValue(Constants.CHINESE));
    }

    @Test
    public void testAddDishToTagValue() {
        // setup
        UUID dishID = UUID.randomUUID();

        Set<String> tagValues = new HashSet<>();
        tagValues.addAll(Arrays.asList(Constants.ASIAN, Constants.JAPANESE));

        this.allTagValues.addAll(tagValues);

        // run
        tag.addDishToTagValue(tagValues, dishID);

        // verify
        Set<UUID> asian = tag.getDishesByValue(Constants.ASIAN);
        Assert.assertTrue(asian.contains(dishID));

        Set<UUID> japanese = tag.getDishesByValue(Constants.JAPANESE);
        Assert.assertTrue(japanese.contains(dishID));

        Set<UUID> chinese = tag.getDishesByValue(Constants.CHINESE);
        Assert.assertEquals(chinese, chineseDishes);

        Assert.assertTrue(asian.size() == 4);
        Assert.assertTrue(chinese.size() == 2);
        Assert.assertTrue(japanese.size() == 1);

        Assert.assertEquals(allTagValues, tag.getAllTagValues());
    }
}
