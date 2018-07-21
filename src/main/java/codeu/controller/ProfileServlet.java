package codeu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

public class ProfileServlet extends HttpServlet {
  private User user;

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
     // Checks to see if the user is logged into his or her page
     String username = request.getParameter("username");
     if (username != null) {
      request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
      return;
    }
    else {
      request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
      return;
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        String username = (String) request.getSession().getAttribute("user");
        user = UserStore.getInstance().getUser(username);

        if (username != null) {
            String aboutMe = request.getParameter("About Me");
            user.setAboutMe(aboutMe);
            UserStore.getInstance().updateUser(user);
            response.sendRedirect("/profile");
            }
    }
}
