package conectores;

import conexao.ConexaoBD;
import model.GradeSemanal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
}
