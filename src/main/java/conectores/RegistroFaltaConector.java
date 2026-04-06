package conectores;

import conexao.ConexaoBD;
import model.RegistroFalta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistroFaltaConector {

    public boolean salvar(RegistroFalta registro) {
        String sql = "INSERT INTO RegistroFalta (aula_disciplina_id, data_falta, quantidade_tempos_perdidos) VALUES (?, ?, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, registro.getAulaDisciplinaId());
            stmt.setDate(2, registro.getDataFalta());
            stmt.setInt(3, registro.getQuantidadeTemposPerdidos());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar registro de falta: " + e.getMessage());
            return false;
        }
    }

    public int contarTotalFaltas(String matriculaAluno, String codigoDisciplina) {
        int total = 0;
        String sql = "SELECT SUM(rf.quantidade_tempos_perdidos) as total " +
                     "FROM RegistroFalta rf " +
                     "JOIN AulaDisciplina ad ON rf.aula_disciplina_id = ad.id " +
                     "JOIN HorarioDia hd ON ad.horario_dia_id = hd.id " +
                     "JOIN GradeSemanal gs ON hd.grade_id = gs.id " +
                     "WHERE gs.aluno_matricula = ? AND ad.disciplina_codigo = ?";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, matriculaAluno);
            stmt.setString(2, codigoDisciplina);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar total de faltas: " + e.getMessage());
        }
        return total;
    }

    /**
     * Salva uma falta buscando automaticamente o ID da aula vinculada ao aluno/disciplina.
     */
    public boolean salvarFaltaPorCodigo(String matriculaAluno, String codigoDisciplina, java.sql.Date dataFalta, int quantidadeTempos) {
        int aulaId = -1;
        String sqlBusca = "SELECT ad.id FROM AulaDisciplina ad " +
                          "JOIN HorarioDia hd ON ad.horario_dia_id = hd.id " +
                          "JOIN GradeSemanal gs ON hd.grade_id = gs.id " +
                          "WHERE gs.aluno_matricula = ? AND ad.disciplina_codigo = ? LIMIT 1";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmtBusca = con.prepareStatement(sqlBusca)) {
            
            stmtBusca.setString(1, matriculaAluno);
            stmtBusca.setString(2, codigoDisciplina);
            ResultSet rs = stmtBusca.executeQuery();
            if (rs.next()) aulaId = rs.getInt("id");

        } catch (SQLException e) {
            System.err.println("Erro ao buscar aula para registro: " + e.getMessage());
        }

        if (aulaId == -1) return false;

        RegistroFalta rf = new RegistroFalta();
        rf.setAulaDisciplinaId(aulaId);
        rf.setDataFalta(dataFalta);
        rf.setQuantidadeTemposPerdidos(quantidadeTempos);
        return salvar(rf);
    }
}
