<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ page import="conectores.*" %>

<%
    // Pega o objeto Aluno que o LoginServlet guardou na sessão
    model.Aluno alunoSessao = (model.Aluno) session.getAttribute("alunoAtivo");

    // Extrai a matrícula dinâmica daquele aluno
    String matriculaLogada = alunoSessao.getMatricula();

    // Instanciando os conectores (DAOs) da pasta 'conectores'
    AlunoConector alunoDAO = new AlunoConector();
    DisciplinaConector disciplinaDAO = new DisciplinaConector();
    GradeSemanalConector gradeDAO=new GradeSemanalConector();

    // Busca o aluno no banco de dados
    Aluno aluno = alunoDAO.buscarPorMatricula(matriculaLogada);
    String nomeExibicao = (aluno != null && aluno.getNome() != null) ? aluno.getNome() : "Não Identificado";

    // Busca a lista de todas as disciplinas
    List<Disciplina> todasDisciplinas = disciplinaDAO.listarDisciplinas();
    if(todasDisciplinas == null) todasDisciplinas = new ArrayList<>();

    // Busca a lista de disciplinas nas quais o aluno está matriculado
    List<Disciplina> disciplinasDoAluno=disciplinaDAO.listarPorAluno(matriculaLogada);
    if(disciplinasDoAluno==null) disciplinasDoAluno=new ArrayList<>();

    // matriz vazia
    // Linhas (0 a 5) = Horários | Colunas (0 a 4) = Seg a Sex
    String[][] gradeMatriz = new String[6][5];
    for(int i=0; i<6; i++) {
        for(int j=0; j<5; j++) {
            gradeMatriz[i][j] = "";
        }
    }
    gradeDAO.preencherGrade(matriculaLogada, gradeMatriz);
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
            <span id="nome-usuario-logado">Olá, <%= nomeExibicao %>!</span>
            <i class="fas fa-bars" style="font-size: 24px; margin-left: 10px;"></i>
        </div>

        <div class="dropdown" id="menu-opcoes-usuario">
            <button class="btn-dropdown-yellow" onclick="window.location.href='index.jsp'">
                <i class="fas fa-home"></i> Tela Inicial
            </button>
            <button id="btn-configuracao" onclick="window.location.href='gerenciargrade.jsp'"><i class="fas fa-cog"></i> Configuração</button>
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
                <i class="fas fa-search" style="color: #aaa; cursor: pointer;"></i>
            </div>
            <button class="btn-filtro" onclick="toggleMenuFiltro()">
                Filtrar por ... <i class="fas fa-filter"></i>
            </button>

            <div class="dropdown-filtro" id="menu-opcoes-filtro">
                <label><input type="checkbox" class="check-periodo" value="1">1º Período</label>
                <label><input type="checkbox" class="check-periodo" value="2">2º Período</label>
                <label><input type="checkbox" class="check-periodo" value="3">3º Período</label>
                <label><input type="checkbox" class="check-periodo" value="4">4º Período</label>
                <label><input type="checkbox" class="check-periodo" value="5">5º Período</label>
            </div>
        </div>

        <div class="lista-materias" id="container-materias-disponiveis">
            <%
                for(Disciplina d:todasDisciplinas){
                    String horario=disciplinaDAO.buscarHorarioFormatado(d.getCodigo(), matriculaLogada);
                    //logica simples pra saber se uma materia foi adicionada a quantidade
                    //maxima de vezes para garantir a possibilidade da mesma materia em dias diferentes
                    int totalTempos=(d.getCargaHorariaTotal()==80)?4:2;
                    int temposNaGrade=gradeDAO.contarTemposDaDisciplina(d.getCodigo(), matriculaLogada);
                    boolean materiaCompleta=(temposNaGrade>=totalTempos);//ou seja, se a quant q vc adicionou condiz com a carga

                    String styleAdicional=materiaCompleta?"style='background-color: #808080; cursor: default;'" : "";
                    String textoBotao=materiaCompleta?"Selecionado" : "Selecionar";
                    String eventoClick=materiaCompleta?"" : "abrirModalAdicionar('" + d.getCodigo() + "', '" + d.getNome() + "')";
            %>
                    <div class="linha-materia">
                        <span class="col-nome"><strong><%= d.getNome() %></strong></span>
                        <span class="col-horario"><%= horario %></span>
                        <span class="col-codigo"><%= d.getCodigo() %></span>
                        <span class="col-carga"><%= d.getCargaHorariaTotal() %>h</span>
                        <button class="btn-selecionar"<%= styleAdicional %> onclick="<%= eventoClick %>"> <%= textoBotao %> </button>
                    </div>
                <% } %>
        </div>

        <div class="barra-acoes-finais">
            <div class="cabecalho-secao-inline" style="margin-bottom: 0;">
                <h2 class="section-title" style="margin-bottom: 0;">Minha grade</h2>
                <button class="btn-editar-grade" onclick="window.location.href='editargrade.jsp'">editar grade</button>
            </div>
            <div class="total-disciplinas-box">
                Total de disciplinas: <span id="contador-disciplinas-selecionadas"><%= disciplinasDoAluno.size() %></span>
            </div>
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
                <tbody id="corpo-tabela-minha-grade">
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

    <div class="modal-overlay" id="modal-adicionar-background">
        <div class="modal-content">
            <div class="modal-header">Adicionar matéria</div>
            <div class="modal-body">
                <form action="<%= request.getContextPath() %>/AdicionarAulaServlet" method="POST">
                    <input type="hidden" name="matriculaAluno" value="<%= matriculaLogada %>">
                    <input type="hidden" name="codigo" id="modal-codigo-disciplina">

                    <div class="input-group">
                        <label>Disciplina selecionada:</label>
                        <input type="text" id="modal-nome-disciplina" readonly
                        style="border: 1px solid #ccc; padding: 8px; border-radius: 4px; cursor: not-allowed;">
                    </div>

                    <div class="input-group" style="display:flex; justify-content:space-between; gap: 15px;">
                        <div class="input-group" style="flex:1">
                            <label>Dia da Semana:</label>
                            <select name="diaAula" required>
                                <option value="Segunda">Segunda-feira</option>
                                <option value="Terça">Terça-feira</option>
                                <option value="Quarta">Quarta-feira</option>
                                <option value="Quinta">Quinta-feira</option>
                                <option value="Sexta">Sexta-feira</option>
                            </select>
                        </div>
                        <div class="input-group">
                            <label>Horário de Início:</label>
                            <select name="inicioAula" required>
                                <option value="1">07:10</option>
                                <option value="2">08:00</option>
                                <option value="3">08:50</option>
                                <option value="4">09:50</option>
                                <option value="5">10:40</option>
                                <option value="6">11:30</option>
                            </select>
                        </div>
                    </div>

                    <div class="counter-group">
                        <label style="font-size: 12px; font-weight: bold; color: var(--primary-dark);">QUANTIDADE DE TEMPOS</label>
                        <div class="counter-controls">
                            <button type="button" class="btn-counter" onclick="alterarAulasAdicionar(-1)">-</button>
                            <span class="counter-value" id="texto-quantidade-adicionar">2</span>
                            <button type="button" class="btn-counter" onclick="alterarAulasAdicionar(1)">+</button>
                        </div>
                        <input type="hidden" id="input-hidden-adicionar" name="qtdAula" value="2">
                    </div>

                    <div class="modal-actions">
                        <button type="button" class="btn-cancel" onclick="fecharModalAdicionar()">CANCELAR</button>
                        <button type="submit" class="btn-confirm">CONFIRMAR</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="JS/scriptgerenciargrade.js"></script>
</body>
</html>