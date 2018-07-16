package codeu.model.store.basic;

import codeu.TestingFramework.TestFramework;
import codeu.model.data.Constants;
import codeu.model.data.Dish;
import codeu.model.data.Review;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class ContentManagerTest extends TestFramework {

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
    public void testQueryByRatings() {
        // set up
        Map<String, Set<String>> queryTags = new HashMap<>();
        queryTags.put(Constants.RESTRICTION,
                new HashSet<>(Arrays.asList(Constants.VEGETARIAN)));
        queryTags.put(Constants.CUISINE,
                new HashSet<>(Arrays.asList(Constants.ASIAN)));

        Map<String, Set<String>> queryTagsTwo = new HashMap<>();
        queryTagsTwo.put(Constants.RESTRICTION,
                new HashSet<>(Arrays.asList(Constants.VEGETARIAN)));
        queryTagsTwo.put(Constants.CUISINE,
                new HashSet<>(Arrays.asList(Constants.CHINESE)));

        // run
        Set<Dish> results = ContentManager.queryByTags(queryTags);
        Set<Dish> resultsTwo = ContentManager.queryByTags(queryTagsTwo);

        // verify
        Assert.assertEquals(new HashSet<>(Arrays.asList(dish, dishTwo)), results);
        Assert.assertEquals(new HashSet<>(Collections.singletonList(dish)), resultsTwo);
    }

    @Test
    public void testSortAllByRating() {
        // set up
        Dish d1 = new Dish(UUID.randomUUID(), "d1", "", 10, new HashMap<>(), new HashSet<>());
        Review r1 = new Review(UUID.randomUUID(), UUID.randomUUID(), d1.getDishID(), 10, "", new HashMap<>());

        Dish d2 = new Dish(UUID.randomUUID(), "d2", "", 6, new HashMap<>(), new HashSet<>());
        Review r2 = new Review(UUID.randomUUID(), UUID.randomUUID(), d2.getDishID(), 6, "", new HashMap<>());

        Dish d3 = new Dish(UUID.randomUUID(), "d3", "", 1, new HashMap<>(), new HashSet<>());
        Review r3 = new Review(UUID.randomUUID(), UUID.randomUUID(), d3.getDishID(), 1, "", new HashMap<>());

        ContentManager.addNewDishAndFirstReview(d1, r1);
        ContentManager.addNewDishAndFirstReview(d2, r2);
        ContentManager.addNewDishAndFirstReview(d3, r3);

        // run
        Set<Dish> highToLow = ContentManager.sortAllByRating(true);
        Set<Dish> lowToHigh = ContentManager.sortAllByRating(false);

        // verify
        Assert.assertEquals(new HashSet<>(Arrays.asList(d1, d2, dishTwo, dish, d3)), highToLow);
        Assert.assertEquals(new HashSet<>(Arrays.asList(d3, dish, dishTwo, d2, d1)), lowToHigh);
    }

    @Test
    public void testQueryByTagsAndRatings() {

    }

    @Test
    public void testQueryAndSort() {

    }
}
