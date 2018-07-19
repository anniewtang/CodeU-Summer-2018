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
<%@ page import="codeu.model.data.Constants"%>
<%@ page import="java.util.Set"%>

<html>
<head>
    <title>CodeU Chat App</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>

<nav>
    <a href="/">Homepage</a>
    <% if (request.getSession().getAttribute("user") != null) { %>
    <a href="/profile"><%= request.getSession().getAttribute("user") %>'s Profile</a>
    <% } else { %>
    <a href="/login">Login</a>
    <% } %>
    <!--a href="/conversations">Conversations</a-->
    <a href="/about.jsp">About</a>
</nav>

<div id="container">

      <img src="title.png" id="appTitle"/>
      <br>
        <button id="craving">What are you craving?</button>
        <div id="checklist">
          <form action="/results" method="POST">
            <%
              Constants options = new Constants();
              Set<String> cuisines = options.getCuisineConstants();
              Set<String> dishes = options.getDishConstants();
              Set<String> restrictions = options.getRestrictionConstants();
            %>

              <div id="cuisines" class="column">
                <p align="center"><b>Cuisines</b></p><hr>
            <%
                for (String cuisine : cuisines) { %>
                  <input type="checkbox" name="<%=cuisine%>" ><%=cuisine%><br>
            <%  } %>

              </div>

              <div id="dishes" class="column">
                <p align="center"><b>Dish Types</b></p><hr>
            <%
                for (String dish : dishes) { %>
                  <input type="checkbox" name="<%=dish%>" ><%=dish%><br>
            <%  } %>
              </div>

              <div id="restrictions" class="column">
                <p align="center"><b>Restrictions</b></p><hr>
            <%
                for (String restriction : restrictions) { %>
                  <input type="checkbox" name="<%=restriction%>" ><%=restriction%><br>
            <%  } %>
              </div>

              <input id="go" type="submit" name="go" class="button" value="Go">
          </form>
        </div>


        <!-- ADD REVIEW BUTTON -->
        <form action="/review" method="get">
            <input id="add-review" type="submit" class="button" value="Add Review">
        </form>

        <script>
        var craving = document.getElementById("craving");
        craving.addEventListener("click", function() {
            var content = document.getElementById("checklist");
            var go = document.getElementById("go");
            var review = document.getElementById("add-review")
            if (content.style.display === "block") {
                content.style.display = "none";
                go.style.display = "none";
                review.style.top = "350px";
            } else {
                content.style.display = "block";
                go.style.display = "block";
                review.style.top = "550px";
            }
        });

        </script>


      <!-- IF user is signed in... use recommendations based on history & current location -->
      <!-- IF user is not signed in... use recommendations based on current location -->

</div>
</body>
</html>
