package conectores;

import conexao.ConexaoBD;
import model.AulaDisciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AulaDisciplinaConector {


    public boolean vincularAulaNoDia(AulaDisciplina aula, int horarioDiaId) {
        String sql = "INSERT INTO AulaDisciplina (horario_dia_id, disciplina_codigo, quantidade_tempos, faltou_aula, professor_ausente, nao_aplicavel) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, horarioDiaId);
            stmt.setString(2, aula.getDisciplina().getCodigo());
            stmt.setInt(3, aula.getQuantidadeTempos());
            stmt.setBoolean(4, aula.isFaltouAula());
            stmt.setBoolean(5, aula.isProfessorAusente());
            stmt.setBoolean(6, aula.isNaoAplicavel());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao vincular aula no dia: " + e.getMessage());
            return false;
        }
    }


    public boolean marcarFaltaNaAula(int aulaId, boolean faltou) {
        String sql = "UPDATE AulaDisciplina SET faltou_aula = ? WHERE id = ?";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setBoolean(1, faltou);
            stmt.setInt(2, aulaId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao registrar falta na aula: " + e.getMessage());
            return false;
        }
    }

    // Método que adicionamos para a tela principal
    public int contarFaltasDoAluno(String matricula, String codigoDisciplina) {
        int totalFaltas = 0;
        String sql = "SELECT SUM(ad.quantidade_tempos) AS total_faltas " +
                "FROM AulaDisciplina ad " +
                "JOIN HorarioDia hd ON ad.horario_dia_id = hd.id " +
                "JOIN GradeSemanal gs ON hd.grade_id = gs.id " +
                "WHERE gs.aluno_matricula = ? " +
                "AND ad.disciplina_codigo = ? " +
                "AND ad.faltou_aula = TRUE";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            stmt.setString(2, codigoDisciplina);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                totalFaltas = rs.getInt("total_faltas");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar faltas: " + e.getMessage());
        }
        return totalFaltas;
    }


    public boolean registrarNovaFaltaSimples(String matricula, String codigoDisciplina, int qtd) {
        int horarioId = -1;

        String sqlSelect = "SELECT hd.id FROM HorarioDia hd JOIN GradeSemanal gs ON hd.grade_id = gs.id WHERE gs.aluno_matricula = ? LIMIT 1";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect)) {

            stmtSelect.setString(1, matricula);
            ResultSet rs = stmtSelect.executeQuery();
            if (rs.next()) {
                horarioId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar horario para falta: " + e.getMessage());
        }

        if (horarioId == -1) return false;

        String sqlInsert = "INSERT INTO AulaDisciplina (horario_dia_id, disciplina_codigo, quantidade_tempos, faltou_aula) VALUES (?, ?, ?, TRUE)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {

            stmtInsert.setInt(1, horarioId);
            stmtInsert.setString(2, codigoDisciplina);
            stmtInsert.setInt(3, qtd);
            stmtInsert.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir falta: " + e.getMessage());
            return false;
        }
    }
}