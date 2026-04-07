package servlets;

import conexao.ConexaoBD;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/AdicionarAulaServlet")
public class AdicionarAulaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String matricula=request.getParameter("matriculaAluno");
        String codigoDisc=request.getParameter("codigo");
        String dia=request.getParameter("diaAula");
        String inicioStr=request.getParameter("inicioAula");
        String qtdStr=request.getParameter("qtdAula");

        try(Connection conn=ConexaoBD.conectar()){
            if(codigoDisc==null || inicioStr==null || qtdStr==null || matricula==null){
                throw new Exception("dados ausentes: matricula/matriculaAluno, codigoDisc/codigo, " +
                        "inicioStr/inicioAula ou qtdStr/qtdAula não recebidos.");
            }

            // buscar o id da grade do aluno
            int idGrade=-1;
            String sqlGrade="SELECT id FROM gradesemanal WHERE aluno_matricula = ?";
            try(PreparedStatement ps=conn.prepareStatement(sqlGrade)){
                ps.setString(1, matricula);
                ResultSet rs=ps.executeQuery();
                if(rs.next()) idGrade=rs.getInt("id");
            }
            if(idGrade==-1) throw new Exception("grade semanal não encontrada para a matrícula: " + matricula);

            // converter valores para inteiros
            int inicioIdx=Integer.parseInt(inicioStr);
            int quantidade=Integer.parseInt(qtdStr);

            salvarAula(conn, idGrade, codigoDisc, dia, inicioIdx, quantidade);

            response.sendRedirect("gerenciargrade.jsp");

        } catch (Exception e){
            e.printStackTrace();
            response.sendError(500, "erro: " + e.getMessage());
        }
    }

    private void salvarAula(Connection conn, int idGrade, String codigo, String dia, int inicioIdx, int qtd) throws SQLException {
        int tempoInicioBanco=inicioIdx;

        // busca o id do dia(ex:id da segunda na grade xyz)
        String sqlDia="SELECT id FROM horariodia WHERE grade_id = ? AND dia_semana = ?";
        int idHorarioDia=-1;

        try(PreparedStatement ps = conn.prepareStatement(sqlDia)){
            ps.setInt(1, idGrade);
            ps.setString(2, dia);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) idHorarioDia = rs.getInt("id");
        }

        if(idHorarioDia!=-1){
            String sqlAula="INSERT INTO auladisciplina (horario_dia_id, disciplina_codigo, quantidade_tempos, tempo_inicio) VALUES (?, ?, ?, ?)";
            try(PreparedStatement ps = conn.prepareStatement(sqlAula)){
                ps.setInt(1, idHorarioDia);
                ps.setString(2, codigo);
                ps.setInt(3, qtd);
                ps.setInt(4, tempoInicioBanco);
                ps.executeUpdate();
            }
        }
    }
}