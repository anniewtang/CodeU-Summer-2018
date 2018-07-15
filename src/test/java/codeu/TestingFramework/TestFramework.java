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

package codeu.TestingFramework;

import codeu.model.data.Constants;
import codeu.model.data.Dish;
import codeu.model.data.Review;
import codeu.model.data.Tag;
import codeu.model.store.basic.DishStore;
import codeu.model.store.basic.ReviewStore;
import codeu.model.store.basic.TagStore;
import codeu.model.store.persistence.PersistentStorageAgent;
import codeu.orm.DishORM;
import codeu.orm.TagORM;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Extendable testing class with Data object constants
 * that can be reused for other Testing classes.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ReviewStore.class, DishStore.class, TagStore.class})
public class TestFramework {
    // Dish Attributes
    public Dish dish;
    public UUID dishID = UUID.randomUUID();
    public String name = "Sesame Noodles";
    public String restaurant = "Asian Fusion";
    public int rating = 4;
    public Set<String> allTagValues;
    // tags for review
    public Map<String, Set<String>> tags;
    public Set<String> restrictions;
    public Set<String> cuisine;
    // tags for reviewOne
    public Map<String, Set<String>> tagsOne;
    public Set<String> restrictionsOne;
    public Set<String> cuisineOne;

    public Dish dishTwo;
    public UUID dishIDTwo = UUID.randomUUID();
    public String nameTwo = "Ramen";
    public String restaurantTwo = "Ramen Shop";
    public int ratingTwo = 2;
    public Set<String> allTagValuesTwo;
    // tags for reviewTwo
    public Map<String, Set<String>> tagsTwo;
    public Set<String> restrictionsTwo;
    public Set<String> cuisineTwo;
    // tags for reviewThree; used for addReview testing
    public Map<String, Set<String>> tagsThree;
    public Set<String> restrictionsThree;
    public Set<String> dishThree;


    // Review Attributes
    public Review review;
    public UUID reviewID = UUID.randomUUID();
    public UUID author = UUID.randomUUID();
    public String desc = "Desc for review 0";
    public int numStars = 3;

    public Review reviewOne;
    public UUID reviewIDOne = UUID.randomUUID();
    public UUID authorOne = UUID.randomUUID();
    public String descOne = "Desc for review 1";
    public int numStarsOne = 5;

    public Review reviewTwo;
    public UUID reviewIDTwo = UUID.randomUUID();
    public UUID authorTwo = UUID.randomUUID();
    public String descTwo = "Desc for review 2";
    public int numStarsTwo = 2;


    public Review reviewThree;
    public UUID reviewIDThree = UUID.randomUUID();
    public UUID authorThree = UUID.randomUUID();
    public String descThree = "Desc for review 3";
    public int numStarsThree = 4; // avgrating for dishtwo is 3


    // Tag Attributes
    public Tag cuisineTag;
    public String cuisineType = Constants.CUISINE;
    public Map<String, Set<UUID>> dishesByValue;
    public Set<UUID> cuisineChineseDishes;
    public Set<UUID> cuisineJapaneseDishes;
    public Set<UUID> cuisineAsianDishes;
    public Set<String> cuisineAllTags;

    public Tag restrictionTag;
    public String restrictionType = Constants.RESTRICTION;
    public Map<String, Set<UUID>> dishesByValueTwo;
    public Set<UUID> restrictionVeganDishes;
    public Set<UUID> restrictionVegetarianDishes;
    public Set<UUID> restrictionGlutenFreeDishes;
    public Set<UUID> restrictionNutFreeDishes;
    public Set<String> restrictionAllTags;

    // Data ORMs
    public TagORM tagORM;
    public DishORM dishORM;

    // Data Stores
    @Mock
    public PersistentStorageAgent mockPersistentStorageAgent;
    public ReviewStore reviewStore;
    public DishStore dishStore;
    public TagStore tagStore;
    public ReviewStore reviewStoreEmpty;
    public DishStore dishStoreEmpty;
    public TagStore tagStoreEmpty;

    @Before
    public void setupDataClasses() {
        // Dish 1
        tags = new HashMap<>();
        restrictions = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.VEGAN));
        cuisine = new HashSet<>(Arrays.asList(Constants.CHINESE, Constants.ASIAN));
        tags.put(Constants.RESTRICTION, restrictions);
        tags.put(Constants.CUISINE, cuisine);

        tagsOne = new HashMap<>();
        restrictionsOne = new HashSet<>(Arrays.asList(Constants.GLUTENFREE, Constants.VEGAN));
        cuisineOne = new HashSet<>(Arrays.asList(Constants.ASIAN));
        tagsOne.put(Constants.RESTRICTION, restrictionsOne);
        tagsOne.put(Constants.CUISINE, cuisineOne);
        tags.get(Constants.RESTRICTION).addAll(restrictionsOne);
        tags.get(Constants.CUISINE).addAll(cuisineOne);

        allTagValues = new HashSet<>(Arrays.asList(Constants.GLUTENFREE, Constants.VEGETARIAN, Constants.VEGAN, Constants.CHINESE, Constants.ASIAN));
        dish = new Dish(dishID, name, restaurant, rating, tags, allTagValues);

        // Dish 2
        tagsTwo = new HashMap<>();
        restrictionsTwo = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.NUTFREE));
        cuisineTwo = new HashSet<>(Arrays.asList(Constants.JAPANESE, Constants.ASIAN));
        tagsTwo.put(Constants.RESTRICTION, restrictionsTwo);
        tagsTwo.put(Constants.CUISINE, cuisineTwo);
        allTagValuesTwo = new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.JAPANESE, Constants.ASIAN));
        dishTwo = new Dish(dishIDTwo, nameTwo, restaurantTwo, ratingTwo, tagsTwo, allTagValuesTwo);

        tagsThree = new HashMap<>();
        restrictionsThree = new HashSet<>(Arrays.asList(Constants.DAIRYFREE));
        dishThree = new HashSet<>(Arrays.asList(Constants.ENTREE));
        tagsThree.put(Constants.RESTRICTION, restrictionsThree);
        tagsThree.put(Constants.DISH, dishThree);

        // Reviews
        review = new Review(reviewID, author, dishID, numStars, desc, tags);
        reviewOne = new Review(reviewIDOne, authorOne, dishID, numStarsOne, descOne, tagsOne);

        reviewTwo = new Review(reviewIDTwo, authorTwo, dishIDTwo, numStarsTwo, descTwo, tagsTwo);
        reviewThree = new Review(reviewIDThree, authorThree, dishIDTwo, numStarsThree, descThree, tagsThree);

        // Tag: Cuisine
        cuisineChineseDishes = new HashSet<>(Arrays.asList(dishID));
        cuisineJapaneseDishes = new HashSet<>(Arrays.asList(dishIDTwo));
        cuisineAsianDishes = new HashSet<>(Arrays.asList(dishID, dishIDTwo));
        dishesByValue = new HashMap<>();
        dishesByValue.put(Constants.CHINESE, cuisineChineseDishes);
        dishesByValue.put(Constants.JAPANESE, cuisineJapaneseDishes);
        dishesByValue.put(Constants.ASIAN, cuisineAsianDishes);
        cuisineAllTags = new HashSet<>(Arrays.asList(Constants.CHINESE, Constants.JAPANESE, Constants.ASIAN));
        cuisineTag = new Tag(cuisineType, dishesByValue, cuisineAllTags);

        // Tag: Restriction
        restrictionVeganDishes = new HashSet<>(Arrays.asList(dishID));
        restrictionVegetarianDishes = new HashSet<>(Arrays.asList(dishID, dishIDTwo));
        restrictionGlutenFreeDishes = new HashSet<>(Arrays.asList(dishID));
        restrictionNutFreeDishes = new HashSet<>(Arrays.asList(dishIDTwo));
        restrictionAllTags = new HashSet<>(Arrays.asList(Constants.VEGAN, Constants.VEGETARIAN, Constants.GLUTENFREE, Constants.NUTFREE));
        dishesByValueTwo = new HashMap<>();
        dishesByValueTwo.put(Constants.VEGAN, restrictionVeganDishes);
        dishesByValueTwo.put(Constants.VEGETARIAN, restrictionVegetarianDishes);
        dishesByValueTwo.put(Constants.GLUTENFREE, restrictionGlutenFreeDishes);
        dishesByValueTwo.put(Constants.NUTFREE, restrictionNutFreeDishes);
        restrictionAllTags = new HashSet<>(Arrays.asList(Constants.VEGAN, Constants.VEGETARIAN, Constants.GLUTENFREE, Constants.NUTFREE));
        restrictionTag = new Tag(restrictionType, dishesByValueTwo, restrictionAllTags);

        // Tag ORM
        Map<String, Tag> tagsByType = new HashMap<>();
        tagsByType.put(Constants.RESTRICTION, restrictionTag);
        tagsByType.put(Constants.CUISINE, cuisineTag);
        tagORM = new TagORM(tagsByType);

        // Dish Store
        Map<UUID, Dish> dishMap = new HashMap<>();
        dishMap.put(dishID, dish);
        dishMap.put(dishIDTwo, dishTwo);
        Map<UUID, Integer> ratingMap = new HashMap<>();
        ratingMap.put(dishID, rating);
        ratingMap.put(dishIDTwo, ratingTwo);
        dishORM = new DishORM(dishMap, ratingMap);

        // Review Store
        reviewStore = ReviewStore.getTestInstance(mockPersistentStorageAgent);
        Map<UUID, Set<Review>> reviewsByDish = new HashMap<>();
        reviewsByDish.put(dishID, new HashSet<>(Arrays.asList(review, reviewOne)));
        reviewsByDish.put(dishIDTwo, new HashSet<>(Arrays.asList(reviewTwo)));
        reviewStore.setReviews(reviewsByDish);

        reviewStoreEmpty = ReviewStore.getTestInstance(mockPersistentStorageAgent);
        reviewStoreEmpty.setReviews(new HashMap<>());

        PowerMockito.mockStatic(ReviewStore.class);
        when(ReviewStore.getInstance()).thenReturn(reviewStore);
        when(ReviewStore.getTestInstance(mockPersistentStorageAgent)).thenReturn(reviewStoreEmpty);

        // Dish Store
        dishStore = DishStore.getTestInstance(mockPersistentStorageAgent);
        dishStore.setDishes(dishORM);

        dishStoreEmpty = DishStore.getTestInstance(mockPersistentStorageAgent);
        dishStoreEmpty.setDishes(new DishORM(new HashMap<>(), new HashMap<>()));

        PowerMockito.mockStatic(DishStore.class);
        when(DishStore.getInstance()).thenReturn(dishStore);
        when(DishStore.getTestInstance(mockPersistentStorageAgent)).thenReturn(dishStoreEmpty);

        // Tag Store
        tagStore = TagStore.getTestInstance(mockPersistentStorageAgent);
        tagStore.setTags(tagORM);

        tagStoreEmpty = TagStore.getTestInstance(mockPersistentStorageAgent);
        tagStoreEmpty.setTags(new TagORM(new HashMap<>()));

        PowerMockito.mockStatic(TagStore.class);
        when(TagStore.getInstance()).thenReturn(tagStore);
        when(TagStore.getTestInstance(mockPersistentStorageAgent)).thenReturn(tagStoreEmpty);
    }

    @Test
    public void setup() {
        // to be overridden by subclasses
    }
}
