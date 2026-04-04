package conectores;

import conexao.ConexaoBD;
import model.AulaDisciplina;

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
}
