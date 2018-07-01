package codeu.controller;

import codeu.model.data.Review;
import codeu.model.data.User;

import codeu.model.store.basic.DishStore;
import codeu.model.store.basic.ReviewStore;
import codeu.model.store.basic.TagStore;
import codeu.model.store.basic.UserStore;

import codeu.orm.DishORM;
import codeu.orm.TagORM;

import codeu.model.store.persistence.PersistentDataStoreException;
import codeu.model.store.persistence.PersistentStorageAgent;

import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener class that fires when the server first starts up, before any servlet classes are
 * instantiated.
 */
public class ServerStartupListener implements ServletContextListener {

  /** Loads data from Datastore. */
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    try {
      List<User> users = PersistentStorageAgent.getInstance().loadUsers();
      UserStore.getInstance().setUsers(users);

      DishORM dishORM = PersistentStorageAgent.getInstance().loadDishes();
      DishStore.getInstance().setDishes(dishORM);

      TagORM tagORM = PersistentStorageAgent.getInstance().loadTags();
      TagStore.getInstance().setTags(tagORM);

      HashMap<UUID, Set<Review>> reviewsByDish = PersistentStorageAgent.getInstance().loadReviews();
      ReviewStore.getInstance().setReviews(reviewsByDish);

    } catch (PersistentDataStoreException e) {
      System.err.println("Server didn't start correctly. An error occurred during Datastore load!");
      System.err.println("This is usually caused by loading data that's in an invalid format.");
      System.err.println("Check the stack trace to see exactly what went wrong.");
      throw new RuntimeException(e);
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {}
}
