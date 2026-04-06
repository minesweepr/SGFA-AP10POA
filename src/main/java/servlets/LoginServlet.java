package servlets;

import conectores.AlunoConector;
import model.Aluno;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/LoginServlet")  // "rota" da página. Ela liga o formulário HTML a este código Java.
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Pega o que o usuário digitou nas caixinhas do login.jsp
        String email = request.getParameter("emailAluno");
        String senha = request.getParameter("senhaAluno");

        //  Cria o conector para procurar esse usuário no banco de dados
        AlunoConector dao = new AlunoConector();
        Aluno alunoLogado = dao.autenticarLogin(email, senha);


        if (alunoLogado != null) {
            // Sucesso! Cria uma "Sessão" (crachá) para o servidor lembrar quem está logado
            HttpSession sessao = request.getSession();
            sessao.setAttribute("alunoAtivo", alunoLogado);

            // Redireciona para a página principal do sistema
            response.sendRedirect("index.jsp");
        } else {
            // Falha! Email ou senha não batem.
            // Para simplificar agora, vamos apenas mostrar um texto na tela.
            response.setContentType("text/html");
            response.getWriter().println("<h3>Credenciais invalidas! Verifique seu email e senha.</h3>");
            response.getWriter().println("<a href='login.jsp'>Voltar para o Login</a>");
        }
    }
}