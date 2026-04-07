package conectores;

import conexao.ConexaoBD;
import model.GradeSemanal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GradeSemanalConector {


    public boolean salvarGrade(GradeSemanal grade, String matriculaAluno) {
        String sql = "INSERT INTO GradeSemanal (semestre, aluno_matricula) VALUES (?, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, grade.getSemestre());
            stmt.setString(2, matriculaAluno);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar grade: " + e.getMessage());
            return false;
        }
    }

    public void preencherGrade(String matriculaAluno, String[][] matriz) {

        String sql = "SELECT hd.dia_semana, ad.disciplina_codigo, ad.quantidade_tempos, ad.tempo_inicio " +
                "FROM HorarioDia hd " +
                "JOIN GradeSemanal gs ON hd.grade_id = gs.id " +
                "JOIN AulaDisciplina ad ON ad.horario_dia_id = hd.id " +
                "WHERE gs.aluno_matricula = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matriculaAluno);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String dia = rs.getString("dia_semana");
                String codigo = rs.getString("disciplina_codigo");
                int qtdTempos = rs.getInt("quantidade_tempos");
                int linhaInicio = rs.getInt("tempo_inicio") - 1;

                // tranformei função pra ficar mais organizado
                int coluna = getColunaPorDia(dia);

                if (coluna != -1 && linhaInicio >= 0) {
                    // preenche a quantidade d tempos a partir da linha d inicio definida
                    for (int i = 0; i < qtdTempos && (linhaInicio + i) < 6; i++) {
                        matriz[linhaInicio + i][coluna] = codigo;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao preencher grade de horários: " + e.getMessage());
        }
    }

    private int getColunaPorDia(String dia) {
        if(dia==null) return -1;
        String d=dia.toLowerCase();
        if(d.contains("seg")) return 0;
        if(d.contains("ter")) return 1;
        if(d.contains("qua")) return 2;
        if(d.contains("qui")) return 3;
        if(d.contains("sex")) return 4;
        return -1;
    }

    public int contarTemposDaDisciplina(String codigo, String matriculaAluno){
        String sql="SELECT SUM(ad.quantidade_tempos) FROM AulaDisciplina ad " +
                "JOIN HorarioDia hd ON ad.horario_dia_id = hd.id " +
                "JOIN GradeSemanal gs ON hd.grade_id = gs.id " +
                "WHERE ad.disciplina_codigo = ? AND gs.aluno_matricula = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt=conn.prepareStatement(sql)){
            stmt.setString(1, codigo);
            stmt.setString(2, matriculaAluno);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()) return rs.getInt(1);
        }catch(SQLException e){ e.printStackTrace(); }
        return 0;
    }
}