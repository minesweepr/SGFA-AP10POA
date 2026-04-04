package conectores;

import conexao.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.GradeSemanal;

public class GradeSemanalConector {




    // Salva a grade no Banco e atrela à Matricula do aluno em questão

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

    public void preencherGrade(String matricula, String[][] matriz) {

        String sql = "SELECT hd.dia_semana, ad.disciplina_codigo, ad.quantidade_tempos " +
                "FROM HorarioDia hd " +
                "JOIN GradeSemanal gs ON hd.grade_id = gs.id " +
                "JOIN AulaDisciplina ad ON ad.horario_dia_id = hd.id " +
                "WHERE gs.aluno_matricula = ? " +
                "ORDER BY hd.id, ad.id";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();

            int[] proximaLinhaLivre = new int[5]; // 0=Segunda, 1=Terça, ..., 4=Sexta

            while (rs.next()) {
                String dia = rs.getString("dia_semana");
                String codigo = rs.getString("disciplina_codigo");
                int qtdTempos = rs.getInt("quantidade_tempos");

                int coluna = -1;
                if (dia.equalsIgnoreCase("Segunda")) coluna = 0;
                else if (dia.equalsIgnoreCase("Terça") || dia.equalsIgnoreCase("Terca")) coluna = 1;
                else if (dia.equalsIgnoreCase("Quarta")) coluna = 2;
                else if (dia.equalsIgnoreCase("Quinta")) coluna = 3;
                else if (dia.equalsIgnoreCase("Sexta")) coluna = 4;

                if (coluna != -1) {
                    for (int i = 0; i < qtdTempos; i++) {
                        int linhaAtual = proximaLinhaLivre[coluna];
                        if (linhaAtual < 6) {
                            matriz[linhaAtual][coluna] = codigo;
                            proximaLinhaLivre[coluna]++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao preencher grade de horários: " + e.getMessage());
        }
    }
}



