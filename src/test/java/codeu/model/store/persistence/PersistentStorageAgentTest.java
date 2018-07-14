package codeu.model.store.persistence;

import codeu.model.data.Dish;
import codeu.model.data.Review;
import codeu.model.data.Tag;
import codeu.model.data.User;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Contains tests of the PersistentStorageAgent class. Currently that class is just a pass-through
 * to PersistentDataStore, so these tests are pretty trivial. If you modify how
 * PersistentStorageAgent writes to PersistentDataStore, or if you swap out the backend to something
 * other than PersistentDataStore, then modify these tests.
 */
public class PersistentStorageAgentTest {

    private PersistentDataStore mockPersistentDataStore;
    private PersistentStorageAgent persistentStorageAgent;

    @Before
    public void setup() {
        mockPersistentDataStore = Mockito.mock(PersistentDataStore.class);
        persistentStorageAgent = PersistentStorageAgent.getTestInstance(mockPersistentDataStore);
    }

    @Test
    public void testLoadUsers() throws PersistentDataStoreException {
        persistentStorageAgent.loadUsers();
        Mockito.verify(mockPersistentDataStore).loadUsers();
    }

    @Test
    public void testLoadTags() throws PersistentDataStoreException {
        persistentStorageAgent.loadTags();
        Mockito.verify(mockPersistentDataStore).loadTags();
    }

    @Test
    public void testLoadDishes() throws PersistentDataStoreException {
        persistentStorageAgent.loadDishes();
        Mockito.verify(mockPersistentDataStore).loadDishes();
    }

    @Test
    public void testLoadReviews() throws PersistentDataStoreException {
        persistentStorageAgent.loadReviews();
        Mockito.verify(mockPersistentDataStore).loadReviews();
    }

    @Test
    public void testWriteThroughUser() {
        User user =
                new User(
                        UUID.randomUUID(),
                        "test_username",
                        "$2a$10$5GNCbSPS1sqqM9.hdiE2hexn1w.vnNoR.CaHIztFEhdAD7h82tqX.",
                        Instant.now(),
                        "My name is my name");
        persistentStorageAgent.writeThrough(user);
        Mockito.verify(mockPersistentDataStore).writeThrough(user);
    }

    @Test
    public void testWriteThroughTag() {
        Tag tag = new Tag("Test tag");
        persistentStorageAgent.writeThrough(tag);
        Mockito.verify(mockPersistentDataStore).writeThrough(tag);
    }

    @Test
    public void testWriteThroughDish() {
        Dish dish =
                new Dish(UUID.randomUUID(), " ", " ", 0,
                        new HashMap<>(), new HashSet<>());
        persistentStorageAgent.writeThrough(dish);
        Mockito.verify(mockPersistentDataStore).writeThrough(dish);
    }

    @Test
    public void testWriteThroughReview() {
        Review review = new Review(UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), 0, " ", new HashMap<>());
        persistentStorageAgent.writeThrough(review);
        Mockito.verify(mockPersistentDataStore).writeThrough(review);
    }
}
