package conectores;

import conexao.ConexaoBD;
import model.HorarioDia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HorarioDiaConector {

    public boolean salvarDiaDaGrade(HorarioDia dia, int gradeId) {
        String sql = "INSERT INTO HorarioDia (grade_id, dia_semana, faltou_dia_inteiro) VALUES (?, ?, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, gradeId);
            stmt.setString(2, dia.getDiaSemana());
            stmt.setBoolean(3, dia.isFaltouDiaInteiro());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar o dia da grade: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarFaltaNoDiaInteiro(int horarioDiaId) {
        String sql = "UPDATE HorarioDia SET faltou_dia_inteiro = true WHERE id = ?";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, horarioDiaId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao macar falta no dia inteiro: " + e.getMessage());
            return false;
        }
    }
}
