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
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
      <a href="/">Homepage</a>
      <a class="log-in-out" href="/login">Login</a>
  </nav>

<div id="container">
    <% if (request.getAttribute("error") != null) { %>
    <h2 style="color:red"><%= request.getAttribute("error") %>
    </h2>
    <% } %>

    <form action="/register" method="POST" style="text-align: center;position:absolute;margin-left:43%;margin-right: 40%;">
        <label for="username">Username: </label>
        <br/>
        <input type="text" name="username" id="username">
        <br/><br/>
        <label for="password">Password: </label>
        <br/>
        <input type="password" name="password" id="password">
        <br/><br/>
        <button type="submit" style="width: 100%;text-align:  center;">Register</button>
    </form>

</div>
</body>
</html>
