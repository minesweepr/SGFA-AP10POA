package servlets;

import conectores.RegistroFaltaConector;
import java.sql.Date;
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

        
        String matriculaAluno = request.getParameter("matriculaAluno");
        String codigoDisciplina = request.getParameter("codigoDisciplina");
        String dataFaltaHTML = request.getParameter("dataFalta");
        int quantidadeTempos = Integer.parseInt(request.getParameter("quantidadeTempos"));

        try {
            Date dataFalta = Date.valueOf(dataFaltaHTML);

            RegistroFaltaConector dao = new RegistroFaltaConector();
            boolean sucesso = dao.salvarFaltaPorCodigo(matriculaAluno, codigoDisciplina, dataFalta, quantidadeTempos);

            if (sucesso) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                response.getWriter().println("Erro: Nao encontramos essa aula na sua grade.");
            }

        } catch (Exception e) {
            System.err.println("Erro no processamento da falta: " + e.getMessage());
            response.getWriter().println("Erro nos dados enviados. Verifique a data e campos.");
        }
    }
}