<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<!DOCTYPE html>
<%@ page import="codeu.model.data.Results" %>
<%@ page import="codeu.model.data.Dish" %>

<html>
<head>
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

  <%
    String userEntry = (String) request.getSession().getAttribute("entry");
  %>
  <h1>Results for: <%=userEntry%></h1>
  <%
    Results searchResults = new Results(userEntry);
    searchResults.sortByRatings();

    for (int i = 0; i < searchResults.getResultsCount(); i++) {
      Dish currDish = searchResults.getResult(i);
  %>
      <!-- format each result TODO: make nicer -->
      <h3><%=currDish.getRestaurant()%>'s <%=currDish.getDishName()/*link this to the dish's page*/%></h3>
      <% for (int j = 0; j < currDish.getRating(); j++) {%>
        <img src="star.png" width="20" height="20"/>
      <%}%>
      <!-- TODO: preview highest rated review here-->
      <hr>
  <%
    }
  %>

</body>
</html>
