
<%
    // Verifica se o usuário NÃO tem a sessão ativa
    if (session.getAttribute("alunoAtivo") == null) {

        response.sendRedirect("login.jsp");
        return; // O return é vital para o servidor parar de carregar o resto da página!
    }
%>


<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Grade - SGFA</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/styleindex.css">
    <link rel="stylesheet" href="CSS/stylegerenciargrade.css">
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
            <span id="nome-usuario-logado">Carregando...</span>
            <i class="fas fa-bars" style="font-size: 24px; margin-left: 10px;"></i>
        </div>

        <div class="dropdown" id="menu-opcoes-usuario">
            <button class="btn-dropdown-yellow" onclick="window.location.href='index.html'">
                <i class="fas fa-home"></i> Tela Inicial
            </button>
            <button id="btn-configuracao"><i class="fas fa-cog"></i> Configuração</button>
            <button id="btn-sair"><i class="fas fa-sign-out-alt"></i> Sair</button>
        </div>
    </header>

    <main>
        <a href="index.jsp" class="titulo-voltar">
            <i class="fas fa-chevron-left"></i> Gerenciar minha grade
        </a>

        <h2 class="section-title">Matérias disponíveis</h2>
        
        <div class="barra-busca-filtro">
            <div class="input-busca-container">
                <input type="text" id="input-busca-materia" placeholder="Pesquisar matéria...">
                <i class="fas fa-search" style="color: #aaa;"></i>
            </div>
            <button class="btn-filtro">
                Filtrar por ... <i class="fas fa-filter"></i>
            </button>
        </div>

        <div class="lista-materias" id="container-materias-disponiveis">
            </div>

        <div class="cabecalho-secao-inline">
            <h2 class="section-title" style="margin-bottom: 0;">Minha grade</h2>
            <button class="btn-editar-grade">editar grade</button>
        </div>

        <div class="table-responsive">
            <table class="tabela-horarios" id="tabela-minha-grade">
                <thead>
                    <tr>
                        <th id="th-horarios">Horários</th>
                        <th id="th-segunda">Segunda</th>
                        <th id="th-terca">Terça</th>
                        <th id="th-quarta">Quarta</th>
                        <th id="th-quinta">Quinta</th>
                        <th id="th-sexta">Sexta</th>
                    </tr>
                </thead>
                <tbody id="corpo-tabela-minha-grade"></tbody>
            </table>
        </div>

        <div class="barra-acoes-finais">
            <div class="total-disciplinas-box">
                Total de disciplinas: <span id="contador-disciplinas-selecionadas">0</span>
            </div>
            
            <div class="botoes-finais">
                <button class="btn-descartar" onclick="window.location.reload()">Descartar mudanças</button>
                <button class="btn-salvar" onclick="salvarNovaGrade()">Salvar grade</button>
            </div>
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

    <script src="js/scriptgerenciargrade.js"></script>
</body>
</html>