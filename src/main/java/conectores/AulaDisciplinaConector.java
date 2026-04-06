package conectores;

import conexao.ConexaoBD;
import model.AulaDisciplina;
import model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AulaDisciplinaConector {

    public boolean vincularAulaNoDia(AulaDisciplina aula, int horarioDiaId) {
        String sql = "INSERT INTO AulaDisciplina (horario_dia_id, disciplina_codigo, quantidade_tempos, tempo_inicio, professor_ausente, nao_aplicavel) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, horarioDiaId);
            stmt.setString(2, aula.getDisciplina().getCodigo());
            stmt.setInt(3, aula.getQuantidadeTempos());
            stmt.setInt(4, aula.getTempoInicio());
            stmt.setBoolean(5, aula.isProfessorAusente());
            stmt.setBoolean(6, aula.isNaoAplicavel());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao vincular aula no dia: " + e.getMessage());
            return false;
        }
    }

    public List<AulaDisciplina> buscarAulasPorDia(int horarioDiaId) {
        List<AulaDisciplina> aulas = new ArrayList<>();
        String sql = "SELECT ad.*, d.nome as disciplina_nome, d.carga_horaria_total " +
                     "FROM AulaDisciplina ad " +
                     "JOIN Disciplina d ON ad.disciplina_codigo = d.codigo " +
                     "WHERE ad.horario_dia_id = ?";
        
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, horarioDiaId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                AulaDisciplina aula = new AulaDisciplina();
                aula.setId(rs.getInt("id"));
                aula.setQuantidadeTempos(rs.getInt("quantidade_tempos"));
                aula.setTempoInicio(rs.getInt("tempo_inicio"));
                aula.setProfessorAusente(rs.getBoolean("professor_ausente"));
                aula.setNaoAplicavel(rs.getBoolean("nao_aplicavel"));
                
                Disciplina d = new Disciplina();
                d.setCodigo(rs.getString("disciplina_codigo"));
                d.setNome(rs.getString("disciplina_nome"));
                d.setCargaHorariaTotal(rs.getInt("carga_horaria_total"));
                aula.setDisciplina(d);
                
                aulas.add(aula);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aulas por dia: " + e.getMessage());
        }
        return aulas;
    }

    public int contarFaltasDoAluno(String matriculaAluno, String codigoDisciplina) {
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
            System.err.println("Erro ao contar total de faltas no conector legado: " + e.getMessage());
        }
        return total;
    }
}