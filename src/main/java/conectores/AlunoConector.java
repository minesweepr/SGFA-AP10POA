package conectores;

import conexao.ConexaoBD;
import model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoConector {

    public boolean cadastrarAluno(Aluno aluno) {
        String sql = "INSERT INTO Aluno (matricula, nome, email, senha) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getEmail());
            stmt.setString(4, aluno.getSenha()); 

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar o aluno no banco: " + e.getMessage());
            return false;
        }
    }

    public Aluno autenticarLogin(String email, String senha) {
        String sql = "SELECT * FROM Aluno WHERE email = ? AND senha = ?";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Aluno alunoLogado = new Aluno();
                alunoLogado.setMatricula(rs.getString("matricula"));
                alunoLogado.setNome(rs.getString("nome"));
                alunoLogado.setEmail(rs.getString("email"));
                alunoLogado.setSenha(rs.getString("senha"));
                return alunoLogado;
            }
        } catch (SQLException e) {
            System.err.println("Erro na busca de login: " + e.getMessage());
        }
        return null;
    }
    public Aluno buscarPorMatricula(String matriculaAluno) {
        Aluno aluno = null;
        String sql = "SELECT * FROM Aluno WHERE matricula = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matriculaAluno);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                aluno = new Aluno();
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setSenha(rs.getString("senha"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por matrícula: " + e.getMessage());
        }

        return aluno;
    }
}

