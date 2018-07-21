<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.data.User" %>
<%
UserStore userStore = UserStore.getInstance();
User user = userStore.getUser((String)request.getSession().getAttribute("user"));
%>

<!DOCTYPE html>
<html>
<head>
  <title>Profile</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a href="/">Homepage</a>
    <% if (request.getSession().getAttribute("user") != null) { %>
    <a href="/profile"><%= request.getSession().getAttribute("user") %>'s Profile</a>
    <a href="/review">Review</a>
    <% } else { %>
    <a href="/login">Login</a>
    <% } %>

    <% if (request.getSession().getAttribute("user") != null) { %>
    <a href="/" >Logout</a>
    <% } %>
  </nav>

  <div id="container">
    <h1>Profile</h1>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <form action ="/profile" method="POST">
        <textarea
            name="About Me" rows="10" col="40">
        </textarea>
        <br>
        <input type="Submit">
       </form>
       <p> <%= user.getAboutMe() %> </p>
    <% } %>
  </div>
</body>
</html>
