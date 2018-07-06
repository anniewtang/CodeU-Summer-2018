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

import codeu.TestingFramework.TestFramework;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TagTest extends TestFramework {
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
        Assert.assertEquals(Constants.CUISINE, cuisineTag.getTagType());
        Assert.assertEquals(dishesByValue, cuisineTag.getAllDishesByValue());
        Assert.assertEquals(cuisineAllTags, cuisineTag.getAllTagValues());
        Assert.assertEquals(cuisineChineseDishes, cuisineTag.getDishesByValue(Constants.CHINESE));
        Assert.assertEquals(cuisineAsianDishes, cuisineTag.getDishesByValue(Constants.ASIAN));
    }

    @Test
    public void testAddDishToTagValue() {
        // setup
        UUID dishIDThree = UUID.randomUUID();

        Set<String> tagValues = new HashSet<>();
        tagValues.addAll(Arrays.asList(Constants.ASIAN, Constants.JAPANESE));

        cuisineAllTags.addAll(tagValues);

        // run
        cuisineTag.addDishToTagValue(tagValues, dishIDThree);

        // verify
        Set<UUID> asian = cuisineTag.getDishesByValue(Constants.ASIAN);
        Assert.assertTrue(asian.contains(dishID));
        Assert.assertTrue(asian.contains(dishIDTwo));
        Assert.assertTrue(asian.contains(dishIDThree));

        Set<UUID> japanese = cuisineTag.getDishesByValue(Constants.JAPANESE);
        Assert.assertTrue(japanese.contains(dishIDTwo));
        Assert.assertTrue(japanese.contains(dishIDThree));

        Set<UUID> chinese = cuisineTag.getDishesByValue(Constants.CHINESE);
        Assert.assertEquals(chinese, cuisineChineseDishes);

        Assert.assertTrue(asian.size() == 3);
        Assert.assertTrue(chinese.size() == 1);
        Assert.assertTrue(japanese.size() == 2);

        Assert.assertEquals(cuisineAllTags, cuisineTag.getAllTagValues());
    }
}
