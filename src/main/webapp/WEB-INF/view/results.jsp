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
<%@ page import="codeu.model.store.basic.ReviewStore" %>
<%@ page import="codeu.model.data.Review" %>

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

<div id="searchResults">
  <%
    String userEntry = (String) request.getSession().getAttribute("entry");
    userEntry = userEntry.substring(0, userEntry.length() - 2);

    Results searchResults = new Results(userEntry);

  %>
    <h1>Results for: <%=userEntry%></h1>
  <%

    for (int i = 0; i < searchResults.getResultsCount(); i++) {
      Dish currDish = searchResults.getResult(i);
  %>
      <h3><%=currDish.getRestaurant()%>'s <%=currDish.getDishName()/*link this to the dish's page*/%></h3>

  <%
      ReviewStore reviewStore =  ReviewStore.getInstance();

      for (int j = 0; j < currDish.getRating(); j++) { %>
        <img src="star.png" width="20" height="20"/>
  <%  }

      Review bestReview = reviewStore.getBestReviewForDish(currDish.getDishID());

      if (bestReview == null) { %>
        <p>No written descriptions.</p>
  <%  } else { %>
        <p>"<%=bestReview.getDescription()%>"</p>
  <%  } %>
      <hr>

  <%
    }
  %>
</div>

</body>
</html>
