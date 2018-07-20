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
<%@ page import="codeu.model.data.User" %>

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
  %>

  <nav>
    <a href="/">Homepage</a>
    <% if (request.getSession().getAttribute("user") != null) { %>
    <a href="/profile"><%= request.getSession().getAttribute("user") %>'s Profile</a>
    <a href="/review">Review</a>
    <% } else { %>
    <a href="/login">Login</a>
    <% } %>
  </nav>

  <div id="container" margin-left="30px">

    <button id="dish-review" class="review-option">I am reviewing <i class="fa fa-angle-down"></i></button>

    <button class="review-option">Cuisine(s) <i class="fa fa-angle-down"></i></button>

    <button class="review-option">Dish Type(s) <i class="fa fa-angle-down"></i></button>

    <button class="review-option">Dietary Restriction(s) <i class="fa fa-angle-down"></i></button>



  </div>
</body>
</html>
