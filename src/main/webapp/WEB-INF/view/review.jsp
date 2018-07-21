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
<%@ page import="java.util.UUID"%>

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

  <script>
    function fillStars(currStar) {
      currStar.src = "star.png";
      for (i = 1; i <= parseInt(currStar.id); i++) {
        document.getElementById(i.toString()).src = "star.png";
      }
      for (i = parseInt(currStar.id) + 1; i <= 5; i++) {
        document.getElementById(i.toString()).src = "uf-star.png";
      }
      document.getElementById("star-count").value = currStar.id;
    }

    function saveCTags(element) {
       if (element.checked) {
          document.getElementById("c-tags").value = document.getElementById("c-tags").value + element.name + ",";
       } else {
          document.getElementById("c-tags").value = (document.getElementById("c-tags").value).replace(element.name + ",", "");
       }
    }

    function saveDTags(element) {
       if (element.checked) {
          document.getElementById("d-tags").value = document.getElementById("d-tags").value + element.name + ",";
       } else {
          document.getElementById("d-tags").value = (document.getElementById("d-tags").value).replace(element.name + ",", "");
       }
    }

    function saveRTags(element) {
       if (element.checked) {
          document.getElementById("r-tags").value = document.getElementById("r-tags").value + element.name + ",";
       } else {
          document.getElementById("r-tags").value = (document.getElementById("r-tags").value).replace(element.name + ",", "");
       }
    }

    function pickDish(element) {
      if (element.id == "0") {
            document.getElementById("is-new").value = 1;
            document.getElementById("dish-ID").value = "<%=UUID.randomUUID().toString()%>";
      } else {
         if (element.checked) {
            document.getElementById("is-new").value = 0;
            document.getElementById("dish-ID").value = element.id;
         } else {
            document.getElementById("dish-ID").value = "";
         }
      }

    }
  </script>


<form action="/" method="POST">

    <div id="container" margin-left="30px">

        <div class="review-entry" id="dish-entry">
          <button id="dish-review" class="review-option">I am reviewing <i class="fa fa-angle-down"></i></button>
                <div class="list">
                    <input type="radio" id="0" name="dishname">
                      <div style="margin-left: 25px;margin-top: -25px;margin-bottom: -25px;">
                        <input name="new-dish-name"class="new-info" type="text" placeholder="New dish name..">
                        <input name="new-rest-name" class="new-info" type="text" placeholder="New restaurant name..">
                      </div>
                    </input>

                    <br>
                    <%
                      for (Dish dish : dishes) { %>
                        <input id="<%=dish.getDishID().toString()%>" type="radio" name="dishname" onchange="pickDish(this)"><%=dish.getDishName()%></input>
                        <br>
                    <%
                      } %>
                    <input name="dish-ID" id="dish-ID" type="hidden" value="">
                    <input name="is-new" id="is-new" type="hidden" value="">
                </div>
          </div>

        <div class="review-entry">
          <button class="review-option">Cuisine(s) <i class="fa fa-angle-down"></i></button>
          <div class="list">
              <%
                for (String cuisine : cuisines) { %>
                  <input type="checkbox" name="<%=cuisine%>" onchange="saveCTags(this)"><%=cuisine%><br>
              <%  } %>
              <input name="c-tags" id="c-tags" type="hidden" value="">
          </div>
        </div>

        <div class="review-entry">
          <button class="review-option">Dish Type(s) <i class="fa fa-angle-down"></i></button>
          <div class="list">
            <%
              for (String dishType : dishTypes) { %>
                <input type="checkbox" name="<%=dishType%>" onchange="saveDTags(this)"><%=dishType%><br>
            <%  } %>
            <input name="d-tags" id="d-tags" type="hidden" value="">
          </div>
        </div>

        <div class="review-entry">
          <button class="review-option">Dietary Restriction(s) <i class="fa fa-angle-down"></i></button>
          <div class="list">
            <%
              for (String restriction : restrictions) { %>
                <input type="checkbox" name="<%=restriction%>" onchange="saveRTags(this)"><%=restriction%><br>
            <%  } %>
            <input name="r-tags" id="r-tags" type="hidden" value="">
          </div>
        </div>
      </div>

      <div id="desc">
        <h3>Dishcuss below:</h3>
        <textarea id="user-desc" name="user-desc" rows="10" cols="60"></textarea>
      </div>

      <div id="rating">
        <h3>Rating:</h3>
        <%  for (int j = 1; j <= 5; j++) { %>
              <img class="uf-star" src="uf-star.png" width="50" height="50" id="<%=j%>" onclick="fillStars(this)"/>
        <%  } %>
        <input name="star-count" id="star-count" type="hidden" value="0">
      </div>

      <input id="submit" type="submit" value="Submit">
</form>

</body>
</html>
