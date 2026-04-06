<%--
  Created by IntelliJ IDEA.
  User: souza
  Date: 05/04/2026
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bem-Vindo!</title>
</head>
<body>
<form action="LoginServlet" method="post">

    <label>Email:</label>
    <input type="text" name="emailAluno" required> <br><br>

    <label>Senha:</label>
    <input type="password" name="senhaAluno" required> <br><br>

    <input type="submit" value="Entrar">

</form>


</body>
</html>
