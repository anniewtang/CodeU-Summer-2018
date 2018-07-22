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
<%@ page import="codeu.orm.ResultsORM" %>
<%@ page import="codeu.model.data.Dish" %>
<%@ page import="codeu.model.store.basic.ReviewStore" %>
<%@ page import="codeu.model.store.basic.ContentManager" %>
<%@ page import="codeu.model.data.Review" %>
<%@ page import="java.util.Set" %>

<html>
<head>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
      <% if (request.getSession().getAttribute("user") != null) { %>
        <a style="font-weight:bold;">Hello <%= request.getSession().getAttribute("user") %>!</a>
      <% } %>
        <a href="/">Homepage</a>
      <% if (request.getSession().getAttribute("user") == null) { %>
          <a class="log-in-out" href="/login">Login</a>
      <% } else { %>
          <a href="/review">Review</a>
          <a class="log-in-out" href="/logout" >Logout</a>
    <% } %>
  </nav>

<div id="searchResults">
  <%
    String userEntry = (String) request.getSession().getAttribute("entry");
    if (userEntry.length() > 2)
      userEntry = userEntry.substring(0, userEntry.length() - 2);

    Set<Dish> searchResults = ResultsORM.getResults(userEntry);

    userEntry = userEntry.replace("C:", "");
    userEntry = userEntry.replace("D:", "");
    userEntry = userEntry.replace("R:", "");

  %>
    <h1>Results for: <%=userEntry%></h1>
  <%

    for (Dish currDish : searchResults) {
  %>
    <form id="dish-form" action="/dish" method="POST">
        <h3><%=currDish.getRestaurant()%>'s
            <a class="links" onclick="document.getElementById('dish-form').submit();">
                <u><%=currDish.getDishName()%></u>
            </a>
        </h3>
        <input name="dish-id" id="dish-id" type="hidden" value="<%=currDish.getDishID().toString()%>">
    </form>

  <%
      ReviewStore reviewStore =  ReviewStore.getInstance();

      for (int j = 1; j <= currDish.getRating(); j++) { %>
        <img src="star.png" width="20" height="20"/>
  <%  }

      for (int j = currDish.getRating() + 1; j <= 5; j++) { %>
      <img src="uf-star.png" width="20" height="20"/>
  <%  }

      Review bestReview = reviewStore.getBestReviewForDish(currDish.getDishID());

      if (bestReview == null) { %>
        <p>No written descriptions.</p>
  <%  } else { %>
        <p><%=ContentManager.getUsername(bestReview.getAuthor())%> says: "<%=bestReview.getDescription()%>"</p>
  <%  } %>
      <hr>

  <%
    }
  %>
    <p>Number of results: <%=searchResults.size()%></p>
</div>

</body>
</html>
