<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.data.User" %>
<%
UserStore userStore = UserStore.getInstance();
User user = userStore.getUser((String)request.getSession().getAttribute("user"));
%>

<!DOCTYPE html>
<html>
<head>
  <title>Review</title>
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
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">
    <h1>Rate A Dish</h1>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <form action ="/review" method="POST">
        <label for="Desription">Describe A Dish Here </label>
        <textarea 
            name="Desription" rows="10" col="40">
        </textarea>
        <br>
        <label for="rate">Rate Dish(out of 5) </label>
        <input type="text" name="rate">
        <br/>
        <input type="Submit">
       </form>   
    <% } %>
  </div>
</body>
</html>