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
<%@ page import="java.util.UUID" %>
<%@ page import="java.util.Set" %>
<%@ page import="codeu.model.store.basic.ContentManager" %>
<%@ page import="codeu.model.data.Review" %>
<%@ page import="codeu.model.data.Dish" %>

<html>
<head>
    <title>Register</title>
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
</nav>

<div id="container">

  <%
    UUID dish_id = UUID.fromString((String) request.getSession().getAttribute("dish-id"));
    ContentManager cm = new ContentManager();
    Set<Review> reviews = cm.getReviewsForDish(dish_id);
    Set<String> tags = cm.getAllTagsForDish(dish_id);
  %>

  <h3 id="dish-page-rest"><%=cm.getRestaurant(dish_id)%>'s</h3>
  <h1 id="dish-page-title"><%=cm.getDishName(dish_id)%></h1>

  <div id="stars">
    <% for (int j = 0; j < cm.getRating(dish_id); j++) { %>
        <img src="star.png" width="4%" height="4%"/>
    <% }
    for (int j = cm.getRating(dish_id) + 1; j <= 5; j++) { %>
      <img src="uf-star.png" width="4%" height="4%"/>
  <% } %>
  </div>

  <form action="/review" method="get">
      <input id="add-review2" type="submit" class="add-review" value="Add Review">
  </form>
  
  </div>

  <div id="tags">
    <h3>Relevant Tags</h3>
    <%
      String allTags = "";
      for (String tag : tags) {
        allTags += tag + ", ";
      }

      allTags = allTags.substring(0, allTags.length() - 2) + ".";
      %>
      <span><%=allTags%></span>
  </div>

  <div id="reviews">
    <h3>Reviews</h3>
    <hr>
    <%
      for (Review review : reviews) {
          for (int j = 0; j < review.getStarRating(); j++) { %>
            <img src="star.png" width="20" height="20"/>
      <%  }

          for (int j = review.getStarRating() + 1; j <= 5; j++) { %>
          <img src="uf-star.png" width="20" height="20"/>
      <%  }

          if (review == null) { %>
            <p>No written descriptions.</p>
      <%  } else { %>
            <p><%=cm.getUsername(review.getAuthor())%> says: "<%=review.getDescription()%>"</p>
      <%  } %>

          <hr>
    <% } %>
  </div>

</div>
</body>
</html>
