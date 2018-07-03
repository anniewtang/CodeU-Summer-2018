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

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class TagTest {
    private Tag tag;

    @Before
    public void setup() {
        // creating attributes for the Tag
        String type = "Cuisine";
        HashMap<String, Set<UUID>> dishesByValue = new HashMap<>();
        HashSet<UUID> chineseDishes = new HashSet<>();
        dishesByValue.put("Chinese", chineseDishes);
        Set<String> allTagValues = new HashSet<>();

    }

    /**
     * Checks the construction of a basic Tag initializer.
     * Doesn't use the setup() construction.
     */
    @Test
    public void testInitializeTag() {
        String type = "Tag Type";

        Tag basicTag = new Tag(type);

        Assert.assertEquals(type, basicTag.getTagType());
        Assert.assertTrue(basicTag.getAllDishesByValue().size() == 0);
        Assert.assertTrue(basicTag.getAllTagValues().size() == 0);
    }


}
