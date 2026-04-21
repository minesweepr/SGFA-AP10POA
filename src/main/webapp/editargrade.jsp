<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ page import="conectores.*" %>

<%
    if (session.getAttribute("alunoAtivo") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    model.Aluno alunoSessao = (model.Aluno) session.getAttribute("alunoAtivo");
    String matriculaLogada = alunoSessao.getMatricula();

    // Instanciando os conectores (DAOs) da pasta 'conectores'
    AlunoConector alunoDAO = new AlunoConector();
    DisciplinaConector disciplinaDAO = new DisciplinaConector();
    AulaDisciplinaConector aulaDAO = new AulaDisciplinaConector();

    Aluno aluno = alunoDAO.buscarPorMatricula(matriculaLogada);
    String nomeExibicao = (aluno != null && aluno.getNome() != null) ? aluno.getNome() : "Não Identificado";

    // Busca a lista de disciplinas nas quais o aluno está matriculado
    List<Disciplina> disciplinas = disciplinaDAO.listarPorAluno(matriculaLogada);
    if(disciplinas == null) disciplinas = new ArrayList<>();
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SGFA - Monitor de faltas</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/styleindex.css">
    <link rel="stylesheet" href="CSS/styleeditargrade.css">
</head>
<body>

<header>
    <div class="logo-container">
        <div class="logo-icon">
            <img src="assets/logofaeterj.png" alt="Logo FAETERJ">
        </div>
        SGFA
    </div>
    <div class="user-menu" onclick="toggleMenu()">
        <i class="fas fa-user-circle" style="font-size: 28px;"></i>
        <span id="nome-usuario-logado">Olá, <%= nomeExibicao %>!</span>
        <i class="fas fa-bars" style="font-size: 24px; margin-left: 10px;"></i>
    </div>

    <div class="dropdown" id="menu-opcoes-usuario">
        <button id="btn-configuracao" onclick="window.location.href='gerenciargrade.jsp'"><i class="fas fa-cog"></i> Configuração</button>
        <button id="btn-sair" onclick="window.location.href='LogoutServlet'" ><i class="fas fa-sign-out-alt"></i> Sair</button>
    </div>
</header>

<main>
    <a href="gerenciargrade.jsp" class="titulo-voltar">
        <i class="fas fa-chevron-left"></i> Editar minha grade
    </a>

    <div class="courses-grid" id="container-lista-disciplinas">

        <%
            for(Disciplina d : disciplinas) {
        %>

        <div class="course-card"
             id="card-disciplina-<%= d.getCodigo() %>">
            <div class="course-header">
                <h4><%= d.getCodigo() %></h4>
                <span style="font-size:12px;"><%= d.getNome() %></span>
            </div>

            <div class="course-details" id="detalhes-disciplina-<%= d.getCodigo() %>" style="display: block;">
                <div class="detalhes-acoes">
                    <button class="btn-remover-disciplina"
                            onclick="event.stopPropagation(); toggleSelecao('<%= d.getCodigo() %>')">
                        <span>Remover</span>
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </div>
        </div>
        <% }  %>

    </div>

    <div class="acoes-grade">
        <button onclick="descartarAlteracoes()">Descartar mudanças</button>

        <button onclick="salvarAlteracoes()">Salvar grade</button>
    </div>
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

<div class="modal-overlay" id="modal-remover-background">
    <div class="modal-content">
        <div class="modal-header">Remover matéria</div>

        <div class="modal-body">
            <form id="form-remover"
                  action="<%= request.getContextPath() %>/RemoverDisciplinaServlet"
                  method="POST">

                <input type="hidden" name="matriculaAluno" value="<%= matriculaLogada %>">

                <div class="input-group">
                    <label>Disciplina selecionada:</label>
                    <div id="modal-remover-lista"
                         style="max-height:150px; overflow-y:auto; border:1px solid #ccc; padding:8px; border-radius:4px;">
                    </div>
                </div>

                <p style="margin-top:10px; font-size:13px;">
                    Tem certeza que deseja remover esta(s) disciplina(s)?
                </p>

                <div class="modal-actions">
                    <button type="button" class="btn-cancel" onclick="fecharModalRemover()">CANCELAR</button>

                    <button type="button" onclick="confirmarRemocao()" class="btn-confirm">
                        REMOVER
                    </button>
                </div>

            </form>
        </div>
    </div>
</div>

<script src="JS/scripteditargrade.js"></script>
</body>
</html>