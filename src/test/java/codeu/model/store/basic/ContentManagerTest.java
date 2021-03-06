package codeu.model.store.basic;

import codeu.TestingFramework.TestFramework;
import codeu.model.data.Constants;
import codeu.model.data.Dish;
import codeu.model.data.Review;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ContentManagerTest extends TestFramework {
    private Map<String, Set<String>> queryTags;
    private Map<String, Set<String>> queryTagsTwo;

    private Dish d1;
    private Dish d2;
    private Dish d3;

    private Set<Integer> queryRatings;

    @Before
    public void setup() {
        queryTags = new HashMap<>();
        queryTags.put(Constants.RESTRICTION,
                new HashSet<>(Arrays.asList(Constants.VEGETARIAN)));
        queryTags.put(Constants.CUISINE,
                new HashSet<>(Arrays.asList(Constants.ASIAN)));

        queryTagsTwo = new HashMap<>();
        queryTagsTwo.put(Constants.RESTRICTION,
                new HashSet<>(Arrays.asList(Constants.VEGETARIAN)));
        queryTagsTwo.put(Constants.CUISINE,
                new HashSet<>(Arrays.asList(Constants.CHINESE)));

        d1 = new Dish(UUID.randomUUID(), "d1", "");
        Review r1 = new Review(UUID.randomUUID(), UUID.randomUUID(), d1.getDishID(), 10, "", new HashMap<>());

        d2 = new Dish(UUID.randomUUID(), "d2", "");
        Review r2 = new Review(UUID.randomUUID(), UUID.randomUUID(), d2.getDishID(), 6, "", new HashMap<>());

        d3 = new Dish(UUID.randomUUID(), "d3", "");
        Review r3 = new Review(UUID.randomUUID(), UUID.randomUUID(), d3.getDishID(), 1, "", new HashMap<>());

        ContentManager.addNewDishAndFirstReview(d1, r1);
        ContentManager.addNewDishAndFirstReview(d2, r2);
        ContentManager.addNewDishAndFirstReview(d3, r3);

        queryRatings = new HashSet<>();
        queryRatings.addAll(Arrays.asList(1, 6, 4));
    }

    @Test
    public void testAddNewDishAndFirstReview() {
        // setup
        Dish d = new Dish(UUID.randomUUID(), "d", "rest");
        Review r = new Review(UUID.randomUUID(), UUID.randomUUID(), d.getDishID(), 5, "", new HashMap<>());

        // run
        ContentManager.addNewDishAndFirstReview(d, r);

        // verify
        Assert.assertEquals(new HashSet<>(Collections.singletonList(d.getDishID())),
                dishStore.getDishesOfRating(5));
        Assert.assertEquals(5, d.getRating());
    }

    @Test
    public void testQueryAndSort() {
        // run
        Set<Dish> results = ContentManager.queryAndSort(queryTags, queryRatings, true);
        Set<Dish> resultsTwo = ContentManager.queryAndSort(queryTagsTwo, new HashSet<>(), true);
        Set<Dish> resultsThree = ContentManager.queryAndSort(new HashMap<>(), new HashSet<>(), true);

        // verify
        Assert.assertEquals(new HashSet<>(Arrays.asList(dish)), results);
        Assert.assertEquals(new HashSet<>(Arrays.asList(dish)), resultsTwo);
        Assert.assertEquals(new HashSet<>(Arrays.asList(d1, d2, d3, dish, dishTwo)), resultsThree);
    }
}
