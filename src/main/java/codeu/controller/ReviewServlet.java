package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import codeu.model.data.Review;
import codeu.model.data.Dish;
import codeu.model.store.basic.ReviewStore;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ContentManager;
import codeu.orm.TagORM;
import codeu.model.data.Constants;
import java.util.*;

public class ReviewServlet extends HttpServlet {
    private User user;
    private ContentManager cm;

    /**
     * Set up state for handling registration-related requests. This method is only called when
     * running in a server, not when running in a test.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        cm = new ContentManager();
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/view/review.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

           String username = (String) request.getSession().getAttribute("user");
           user = UserStore.getInstance().getUser(username);

           UUID reviewID = UUID.randomUUID();
           UUID authorID = user.getId();
           UUID dishID = UUID.fromString(request.getParameter("dish-ID"));
           int numStars = Integer.parseInt(request.getParameter("star-count"));
           String desc = request.getParameter("user-desc");
           Integer isNewDish = Integer.parseInt(request.getParameter("is-new"));
           String dishName = request.getParameter("new-dish-name");
           String restName = request.getParameter("new-rest-name");

           String Ctags = request.getParameter("c-tags");
           String Dtags = request.getParameter("d-tags");
           String Rtags = request.getParameter("r-tags");

           StringTokenizer stC = new StringTokenizer(Ctags, ",");
           StringTokenizer stD = new StringTokenizer(Dtags, ",");
           StringTokenizer stR = new StringTokenizer(Rtags, ",");

           Set<String> Cset = new HashSet<String>();
           Set<String> Dset = new HashSet<String>();
           Set<String> Rset = new HashSet<String>();

           Set<String> allTags = new HashSet<String>();

           while (stC.hasMoreTokens()) {
             String temp = stC.nextToken();
             Cset.add(temp);
             allTags.add(temp);
           }

           while (stD.hasMoreTokens()) {
             String temp = stD.nextToken();
             Dset.add(temp);
             allTags.add(temp);
           }

           while (stR.hasMoreTokens()) {
             String temp = stR.nextToken();
             Rset.add(temp);
             allTags.add(temp);
           }

           HashMap<String, Set<String>> tags = new HashMap<>();

           tags.put(Constants.CUISINE, Cset);
           tags.put(Constants.DISH, Dset);
           tags.put(Constants.RESTRICTION, Rset);

          if (isNewDish) {
              cm.addNewDishAndFirstReview(new Dish(UUID.randomUUID(), dishName, restName, numStars, tags, allTags),
                                          new Review(reviewID, authorID, dishID, numStars, desc, tags));
          } else  {
              cm.addReviewForExistingDish(new Review(reviewID, authorID, dishID, numStars, desc, tags));
          }
    }
}
