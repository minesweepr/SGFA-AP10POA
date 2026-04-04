package conectores;

import conexao.ConexaoBD;
import model.AulaDisciplina;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    // Marca a falta em apenas uma aula
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
    public int contarFaltasDoAluno(String matricula, String codigoDisciplina) {
        int totalFaltas = 0;

        // Soma a quantidade de tempos apenas onde faltou_aula é TRUE
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
}
