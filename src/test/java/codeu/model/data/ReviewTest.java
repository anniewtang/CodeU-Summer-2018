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

public class ReviewTest {

    @Test
    public void testCreateReview() {
        // setup
        UUID id = UUID.randomUUID();
        UUID author = UUID.randomUUID();
        UUID dishID = UUID.randomUUID();
        int numStars = 4;
        String desc = "This is a review.";

        Map<String, Set<String>> tags = new HashMap<>();
        tags.put(Constants.RESTRICTION, new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.VEGAN)));
        tags.put(Constants.CUISINE, new HashSet<>(Arrays.asList(Constants.JAPANESE, Constants.ASIAN)));

        // run
        Review review = new Review(id, author,dishID, numStars, desc, tags);

        // verify
        Assert.assertEquals(id, review.getReviewID());
        Assert.assertEquals(author, review.getAuthor());
        Assert.assertEquals(dishID, review.getDishID());
        Assert.assertEquals(numStars, review.getStarRating());
        Assert.assertEquals(desc, review.getDescription());
        Assert.assertEquals(tags, review.getTags());
    }
}