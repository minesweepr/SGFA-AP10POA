package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. Puxa a sessão atual do usuário (se ela existir)
        HttpSession session = request.getSession(false);

        // 2. Se a sessão existir, nós a destruímos (isso apaga o alunoAtivo da memória)
        if (session != null) {
            session.invalidate();
        }

        // 3. Redireciona o usuário de volta para a porta da frente
        response.sendRedirect("login.jsp");
    }
}