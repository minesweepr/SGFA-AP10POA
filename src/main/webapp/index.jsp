<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ page import="conectores.*" %>

<%


    // Verifica se o usuário NÃO tem a sessão ativa------------------
    if (session.getAttribute("alunoAtivo") == null) {
        response.sendRedirect("login.jsp");
        return; // O return é vital para o servidor parar de carregar o resto da página!
    }


    // Pega o objeto Aluno que o LoginServlet guardou na sessão
    model.Aluno alunoSessao = (model.Aluno) session.getAttribute("alunoAtivo");

    // Extrai a matrícula dinâmica daquele aluno
    String matriculaLogada = alunoSessao.getMatricula();


    // Instanciando os conectores (DAOs) da pasta 'conectores'
    AlunoConector alunoDAO = new AlunoConector();
    DisciplinaConector disciplinaDAO = new DisciplinaConector();
    AulaDisciplinaConector aulaDAO = new AulaDisciplinaConector();
    // HorarioDiaConector horarioDAO = new HorarioDiaConector();

    // Busca o aluno no banco de dados
    Aluno aluno = alunoDAO.buscarPorMatricula(matriculaLogada);
    String nomeExibicao = (aluno != null && aluno.getNome() != null) ? aluno.getNome() : "Não Identificado";

    // Busca a lista de disciplinas nas quais o aluno está matriculado
    List<Disciplina> disciplinas = disciplinaDAO.listarPorAluno(matriculaLogada);
    if(disciplinas == null) disciplinas = new ArrayList<>();

    // Processamento de Estatísticas Iniciais
    int atencaoCount = 0;

    // matriz vazia
    // Linhas (0 a 5) = Horários | Colunas (0 a 4) = Seg a Sex
    String[][] gradeMatriz = new String[6][5];
    for(int i=0; i<6; i++) {
        for(int j=0; j<5; j++) {
            gradeMatriz[i][j] = "";
        }
    }

    // Chama o conector para preencher a tabela com os dados reais do banco
    
        GradeSemanalConector gradeDAO = new GradeSemanalConector();
        gradeDAO.preencherGrade(matriculaLogada, gradeMatriz);

%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SGFA - Monitor de faltas</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/styleindex.css">
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
            <button class="btn-dropdown-yellow" onclick="abrirModalFalta()">
                <i class="fas fa-plus"></i> Registrar falta
            </button>
            <button id="btn-configuracao" onclick="window.location.href='gerenciargrade.jsp'"><i class="fas fa-cog"></i> Configuração</button>
            <button id="btn-sair"><i class="fas fa-sign-out-alt"></i> Sair</button>
        </div>
    </header>

    <main>
        <div class="top-actions">
            <button class="btn btn-yellow" onclick="abrirModalFalta()" id="btn-registrar-falta-topo">
                <i class="fas fa-plus"></i> Registrar falta
            </button>
            <button class="btn btn-outline" id="btn-gerenciar-grade-topo" onclick="window.location.href='gerenciargrade.jsp'">
                <i class="fas fa-cog"></i> Gerenciar grade
            </button>
        </div>

        <%
            // Loop rápido só para calcular as matérias "em atenção" antes de imprimir as estatísticas
            for(Disciplina d : disciplinas) {
                int faltasContadas = aulaDAO.contarFaltasDoAluno(matriculaLogada, d.getCodigo());
                double percParaBarra = (faltasContadas * 100.0) / d.getLimiteFaltas();
                if(percParaBarra >= 50 && percParaBarra < 85) {
                    atencaoCount++;
                }
            }
        %>

        <div class="stats-grid">
            <div class="stat-card">
                <h3>Disciplinas na grade</h3>
                <div class="value" id="quantidade-total-disciplinas"><%= disciplinas.size() %></div>
            </div>
            <div class="stat-card">
                <h3>Atenção</h3>
                <div class="value" id="quantidade-disciplinas-atencao"><%= atencaoCount %></div>
                <p>disciplinas próximo ao limite de 25%</p>
            </div>
            <div class="stat-card">
                <h3>Situação atual</h3>
                <div class="value success" id="status-situacao-aluno">Regular</div>
            </div>
        </div>

        <h2 class="section-title">Monitor de faltas</h2>
        <div class="courses-grid" id="container-lista-disciplinas">

            <%--GERA UM CARD PARA CADA DISCIPLINA --%>
            <%
                for(Disciplina d : disciplinas) {

                    // Busca as faltas reais no banco usando o código da disciplina
                    int faltasAtuais = aulaDAO.contarFaltasDoAluno(matriculaLogada, d.getCodigo());
                    int limite = d.getLimiteFaltas();

                    // Instanciando o seu Controlador de Faltas
                    ControladorFaltas ctrl = new ControladorFaltas();
                    ctrl.setTotalFaltas(faltasAtuais);
                    int saldoRestante = ctrl.getSaldoSeguranca(d);

                    // Calculo da porcentagem da barra visual (0 a 100%)
                    double porcentagemBarra = 0;
                    if(limite > 0) {
                        porcentagemBarra = (faltasAtuais * 100.0) / limite;
                    }
                    if(porcentagemBarra > 100) porcentagemBarra = 100;

                    String classeCorBarra = "bg-green";
                    String classeCorCirculo = "color-green";

                    if (porcentagemBarra >= 85) {
                        classeCorBarra = "bg-red";
                        classeCorCirculo = "color-red";
                    } else if (porcentagemBarra >= 50) {
                        classeCorBarra = "bg-yellow";
                        classeCorCirculo = "color-yellow";
                    }
            %>

            <div class="course-card" id="card-disciplina-<%= d.getCodigo() %>">
                <div class="course-header" onclick="alternarDetalhesCard('card-disciplina-<%= d.getCodigo() %>')">
                    <h4><%= d.getCodigo() %></h4>
                    <span style="font-size:12px;"><%= d.getNome() %></span>
                </div>

                <div class="course-summary" id="resumo-disciplina-<%= d.getCodigo() %>" onclick="alternarDetalhesCard('card-disciplina-<%= d.getCodigo() %>')">
                    <div class="progress-bar-container">
                        <div class="progress-bar <%= classeCorBarra %>" style="width: <%= String.format(Locale.US, "%.1f", porcentagemBarra) %>%;"></div>
                    </div>
                    <i class="fas fa-chevron-down toggle-icon"></i>
                </div>

                <div class="course-details" id="detalhes-disciplina-<%= d.getCodigo() %>">
                    <div class="detalhes-header">
                        <div class="progress-bar-container-large">
                            <div class="progress-bar <%= classeCorBarra %>" style="width: <%= String.format(Locale.US, "%.1f", porcentagemBarra) %>%;"></div>
                        </div>
                        <div class="circular-progress <%= classeCorCirculo %>">
                            <span><%= String.format(Locale.US, "%.1f", (ctrl.getPercentualFaltas(d) * 100)) %>%</span>
                        </div>
                    </div>

                    <div class="detalhes-textos">
                        <p>Faltas registradas: <span><%= faltasAtuais %>/<%= limite %></span></p>
                        <p class="destaque">Você ainda pode faltar <span><%= saldoRestante %></span> vezes</p>
                    </div>

                    <hr class="divisor-card">

                    <div class="detalhes-acoes">

                    </div>
                </div>
            </div>
            <% }  %>

        </div>

        <br><br>

        <h2 class="section-title">Grade de Horários</h2>
        <div class="table-responsive">
            <table class="tabela-horarios" id="tabela-grade-horarios">
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
                <tbody id="corpo-tabela-horarios">
                    <%
                        String[] rotulosTempos = {"7:10-8:00", "8:00-8:50", "8:50-9:40", "9:50-10:40", "10:40-11:30", "11:30-12:20"};

                        // Desenha a tabela baseada na matriz que foi preenchida lá no topo
                        for(int linha = 0; linha < 6; linha++) {
                    %>
                        <tr>
                            <td class="col-horario"><%= rotulosTempos[linha] %></td>
                            <% for(int coluna = 0; coluna < 5; coluna++) {
                                String sigla = gradeMatriz[linha][coluna];

                            %>
                                <td><%= sigla %></td>
                            <% } %>
                        </tr>
                    <% } %>
                </tbody>
            </table>
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

    <div class="modal-overlay" id="modal-falta-background">
        <div class="modal-content">
            <div class="modal-header">Registrar ausência</div>
            <div class="modal-body">

           <form action="<%= request.getContextPath() %>/RegistrarFaltaServlet" method="POST">

                    <input type="hidden" name="matriculaAluno" value="<%= matriculaLogada %>">

                    <div class="input-group">
                        <label>Disciplina:</label>
                        <select name="codigoDisciplina" required>
                            <option value="">Selecione...</option>
                            <%-- Preenche o Select dinamicamente via Java --%>
                            <% for(Disciplina d : disciplinas) { %>
                                <option value="<%= d.getCodigo() %>"><%= d.getCodigo() %> - <%= d.getNome() %></option>
                            <% } %>
                        </select>
                    </div>
                    <div class="input-group">
                        <label>Data</label>
                        <input type="date" name="dataFalta" required>
                    </div>
                    <div class="counter-group">
                        <label style="font-size: 12px; font-weight: bold; color: var(--primary-dark);">NÚMERO DE AULAS</label>
                        <div class="counter-controls">
                            <button type="button" class="btn-counter" onclick="alterarAulas(-1)">-</button>
                            <span class="counter-value" id="texto-quantidade-aulas">2</span>
                            <button type="button" class="btn-counter" onclick="alterarAulas(1)">+</button>
                        </div>
                        <input type="hidden" id="input-hidden-aulas" name="quantidadeTempos" value="2">
                    </div>
                    <div class="modal-actions">
                        <button type="button" class="btn-cancel" onclick="fecharModalFalta()">CANCELAR</button>
                        <button type="submit" class="btn-confirm">CONFIRMAR</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="JS/scriptindex.js"></script>
</body>
</html>