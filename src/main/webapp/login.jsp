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

    <style>
        :root {
            --primary-dark: #1b365d;
            --accent-yellow: #ffb600;
            --bg-color: #e6f0fa;
            --grid-color: rgba(173, 216, 230, 0.4);
        }

        body {
            margin: 0;
            background-color: var(--bg-color);
            background-image:
                    linear-gradient(var(--grid-color) 1px, transparent 1px),
                    linear-gradient(90deg, var(--grid-color) 1px, transparent 1px);
            background-size: 40px 40px;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        main {
            flex: 1;
            width: 100%;
            display: flex;
            flex-direction: column;
            justify-content: center; /* Centro vertical */
            align-items: center; /* Centro horizontal */
            gap: 30px;
        }

        form {
            background-color: white;
            border-radius: 12px;
            padding: 40px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 350px;


    /*Imagem e texto------------------------*/
        figure {
            margin: 0 0 30px 0;
            display: flex;
            flex-direction: column;
            align-items: center; /* Centraliza a logo e o texto */
            gap: 15px;
        }

        figure img {
            width: 80px;
            height: auto;
        }

        figure h2 {
            margin: 0;
            font-size: 18px;
            color: var(--primary-dark);
            text-align: center;
        }


        /* Estilo para as labels (Email e Senha)-------------- */
        label {
            display: block; /* Faz com que a label fique numa linha própria, acima da caixa */
            font-size: 14px;
            font-weight: bold;
            color: var(--primary-dark);
            margin-bottom: 8px;
        }

        /* Estilo para as caixas de texto (inputs)----------------------------- */
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 15px;
            margin-bottom: 25px;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-sizing: border-box; /* Garante que o padding não estraga os 100% de largura */
            font-size: 14px;
        }

        /* Estilo para o botão de Entrar------------------------- */
        input[type="submit"] {
            width: 100%;
            padding: 15px;
            background-color: var(--accent-yellow);
            color: var(--primary-dark);
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: 0.2s;

        input[type="submit"]:hover {
            filter: brightness(0.9); /* Escurece um pouco o botão*/
        }
        /* FOOTER ------------------------------------------------------------------*/
        footer {
            background-color: #1b365d;
            color: white;
            padding: 20px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: auto;
        }

        footer a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            font-size: 14px;
        }

        footer a:hover {
            text-decoration: underline;
        }

        .footer-left span {
            font-size: 14px;
            font-weight: 500;
        }
    </style>
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
