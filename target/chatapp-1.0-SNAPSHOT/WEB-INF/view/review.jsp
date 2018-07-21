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
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.store.basic.ContentManager"%>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Dish" %>
<%@ page import="codeu.model.data.Constants"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Collection"%>


<html>
<head>
  <title>Review</title>
  <link rel="stylesheet" href="/css/main.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

  <%
  UserStore userStore = UserStore.getInstance();
  User user = userStore.getUser((String)request.getSession().getAttribute("user"));
  ContentManager cm = new ContentManager();
  %>

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

  <%
    Constants options = new Constants();
    Set<String> cuisines = options.getCuisineConstants();
    Set<String> dishTypes = options.getDishConstants();
    Set<String> restrictions = options.getRestrictionConstants();
    Collection<Dish> dishes = cm.getAllDishes();
  %>

  <div id="container" margin-left="30px">

    <div class="review-entry" id="dish-entry">
      <button id="dish-review" class="review-option">I am reviewing <i class="fa fa-angle-down"></i></button>
            <div class="list">
                <%
                  for (Dish dish : dishes) { %>
                    <input type="radio" name="dishname"><%=dish.getDishName()%></input>
                    <br>
                <%  } %>
            </div>
      </div>

    <div class="review-entry">
      <button class="review-option">Cuisine(s) <i class="fa fa-angle-down"></i></button>
      <div class="list">
          <%
            for (String cuisine : cuisines) { %>
              <input type="checkbox" name="C:<%=cuisine%>" onchange="saveTag(this)"><%=cuisine%><br>
          <%  } %>
      </div>
    </div>

    <div class="review-entry">
      <button class="review-option">Dish Type(s) <i class="fa fa-angle-down"></i></button>
      <div class="list">
        <%
          for (String dishType : dishTypes) { %>
            <input type="checkbox" name="D:<%=dishType%>" onchange="saveTag(this)"><%=dishType%><br>
        <%  } %>
      </div>
    </div>

    <div class="review-entry">
      <button class="review-option">Dietary Restriction(s) <i class="fa fa-angle-down"></i></button>
      <div class="list">
        <%
          for (String restriction : restrictions) { %>
            <input type="checkbox" name="R:<%=restriction%>" onchange="saveTag(this)"><%=restriction%><br>
        <%  } %>
      </div>
    </div>
  </div>

  <div id="desc">
    <h3>Dishcuss below:</h3>
    <textarea id="user-desc" rows="10" cols="70"></textarea>
  </div>

  <div id="rating">
    <h3>Rating:</h3>
    <%
        for (int j = 0; j < 5; j++) { %>
          <img src="star.png" width="50" height="50"/>
    <%  } %>
  </div>
</body>
</html>
