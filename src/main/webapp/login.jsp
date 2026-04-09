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
    <link rel="stylesheet" href="CSS/styleLogin.css">

</head>
<body>

<main>


<form action="LoginServlet" method="post">
    <figure>
        <img src="assets/logofaeterj.png" alt="Logo FAETERJ">
        <h2>SGFA - GERENCIADOR DE FREQUÊNCIA</h2>
    </figure>

    <label>Email:</label>
    <input type="text" name="emailAluno" required>

    <label>Senha:</label>
    <input type="password" name="senhaAluno" required>

    <input type="submit" value="Entrar">

</form>

</main>

<footer id="rodape-pagina">
    <div class="footer-left">
        <span id="texto-copyright">©2026 - SGFA</span>
    </div>
    <div class="footer-right">
        <a href="https://faeterj-rio.edu.br/central/index.php?a=add&category=2" id="link-suporte">Suporte</a>
        <a href="https://www.faeterj-rio.edu.br/" id="link-site-institucional">Site institucional</a>
    </div>
</footer>

</body>
</html>
