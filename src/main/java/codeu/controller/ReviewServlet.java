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
import codeu.model.store.basic.ReviewStore;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.orm.TagORM;
import codeu.model.data.Constants;
import java.util.*;

public class ReviewServlet extends HttpServlet {
    private User user;
    private ReviewStore store;

    /**
     * Set up state for handling registration-related requests. This method is only called when
     * running in a server, not when running in a test.
     */
    @Override
    public void init() throws ServletException {
        super.init();
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

                UUID newID = UUID.randomUUID();
                UUID authorID = user.getId();
                UUID dishID = UUID.randomUUID();
                int numStars = (int) request.getSession().getAttribute("rate");
                String desc = (String) request.getSession().getAttribute("Description");

                HashMap<String, Set<String>> tags = new HashMap<>();
                tags.put(Constants.RESTRICTION, new HashSet<>(Arrays.asList(Constants.VEGETARIAN, Constants.VEGAN)));
                tags.put(Constants.CUISINE, new HashSet<>(Arrays.asList(Constants.JAPANESE, Constants.ASIAN)));


                Review review = new Review(newID, authorID, dishID, numStars, desc, tags);
                store.addReview(review);

                response.sendRedirect("/results");
    }
}
