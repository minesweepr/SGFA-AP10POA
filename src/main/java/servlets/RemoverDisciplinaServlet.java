package servlets;

import conexao.ConexaoBD;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/RemoverDisciplinaServlet")
public class RemoverDisciplinaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String matricula = request.getParameter("matriculaAluno");
        String[] codigos = request.getParameterValues("codigos");

        try (Connection conn = ConexaoBD.conectar()) {

            if (matricula == null || codigos == null || codigos.length == 0) {
                throw new Exception("dados ausentes: matricula ou codigos");
            }

            int idGrade = -1;
            String sqlGrade = "SELECT id FROM gradesemanal WHERE aluno_matricula = ?";

            try (PreparedStatement ps = conn.prepareStatement(sqlGrade)) {
                ps.setString(1, matricula);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) idGrade = rs.getInt("id");
            }

            if (idGrade == -1) {
                throw new Exception("grade não encontrada para matrícula: " + matricula);
            }

            String sqlDelete = """
                DELETE ad FROM auladisciplina ad
                JOIN horariodia hd ON ad.horario_dia_id = hd.id
                WHERE hd.grade_id = ? AND ad.disciplina_codigo = ?
            """;

            try (PreparedStatement ps = conn.prepareStatement(sqlDelete)) {
                for (String codigoDisc : codigos) {
                    ps.setInt(1, idGrade);
                    ps.setString(2, codigoDisc);
                    ps.executeUpdate();
                }
            }

            response.sendRedirect("editargrade.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "erro: " + e.getMessage());
        }
    }
}