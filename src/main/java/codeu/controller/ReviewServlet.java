package codeu.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import codeu.model.data.Review;
import codeu.model.data.Dish;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ContentManager;
import codeu.model.data.Constants;
import java.util.*;
import java.util.stream.Collectors;

public class ReviewServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/view/review.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
         String username = (String) request.getSession().getAttribute("user");
         User user = UserStore.getInstance().getUser(username);
         UUID reviewID = UUID.randomUUID();
         UUID authorID = user.getId();
         UUID dishID = UUID.fromString(request.getParameter("dish-ID"));
         int numStars = Integer.parseInt(request.getParameter("star-count"));
         String desc = request.getParameter("user-desc");
         Integer isNewDish = Integer.parseInt(request.getParameter("is-new"));
         String dishName = request.getParameter("new-dish-name");
         String restName = request.getParameter("new-rest-name");

         Set<String> Cset = new HashSet<>(Arrays.asList(request.getParameter("c-tags").split(",")));
         Set<String> Dset = new HashSet<>(Arrays.asList(request.getParameter("d-tags").split(",")));
         Set<String> Rset = new HashSet<>(Arrays.asList(request.getParameter("r-tags").split(",")));

         Set<String> allTags = new HashSet<>();
         allTags.addAll(Cset);
         allTags.addAll(Dset);
         allTags.addAll(Rset);

         HashMap<String, Set<String>> tags = new HashMap<>();
         tags.put(Constants.CUISINE, Cset);
         tags.put(Constants.DISH, Dset);
         tags.put(Constants.RESTRICTION, Rset);

        if (isNewDish == 1) {
            ContentManager.addNewDishAndFirstReview(new Dish(dishID, dishName, restName, numStars, tags, allTags),
                                         new Review(reviewID, authorID, dishID, numStars, desc, tags));
        } else  {
            ContentManager.addReviewForExistingDish(new Review(reviewID, authorID, dishID, numStars, desc, tags));
        }

        response.sendRedirect("/");
    }
}
