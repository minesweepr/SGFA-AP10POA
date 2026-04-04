package servlets;

import conectores.AulaDisciplinaConector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class RegistrarFaltaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String matricula = request.getParameter("matriculaAluno");
        String codigoMateria = request.getParameter("codigoDisciplina");


        int qtdAulas = 0;
        try {
            qtdAulas = Integer.parseInt(request.getParameter("quantidadeTempos"));
        } catch (NumberFormatException e) {
            System.err.println("Erro ao ler quantidade de aulas: " + e.getMessage());
        }


        AulaDisciplinaConector dao = new AulaDisciplinaConector();
        boolean sucesso = dao.registrarNovaFaltaSimples(matricula, codigoMateria, qtdAulas);


        if (sucesso) {

            response.sendRedirect("index.jsp");
        } else {

            response.getWriter().println("Erro ao registrar falta. Verifique o console do IntelliJ.");
        }
    }
}