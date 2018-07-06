package codeu.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.time.Instant;
import codeu.model.data.User;

public class ProfileServletTest{

    private ProfileServlet profileServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private RequestDispatcher mockRequestDispatcher;

    @Before
    public void setup() {
        profileServlet = new ProfileServlet();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp"))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGet() throws IOException, ServletException {
        User user =
        new User(
            UUID.randomUUID(),
            "test username",
            "$2a$10$.e.4EEfngEXmxAO085XnYOmDntkqod0C384jOR9oagwxMnPNHaGLa",
            Instant.now(),
            "This is content about me.");

        Mockito.when(mockRequest.getParameter("username")).thenReturn(user.getName());
        profileServlet.doGet(mockRequest, mockResponse);
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
}