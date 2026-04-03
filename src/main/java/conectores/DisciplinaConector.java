package conectores;

import conexao.ConexaoBD;
import model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaConector {

    // Método para cadastrar as matérias do semestre criando por desencargo de consciência 
    public boolean cadastrarDisciplina(Disciplina disciplina) {
        String sql = "INSERT INTO Disciplina (codigo, nome, carga_horaria_total) VALUES (?, ?, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, disciplina.getCodigo());
            stmt.setString(2, disciplina.getNome());
            stmt.setInt(3, disciplina.getCargaHorariaTotal());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar disciplina: " + e.getMessage());
            return false;
        }
    }

    // Retorna todas as disciplinas oferecidas
    public List<Disciplina> listarDisciplinas() {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM Disciplina";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setCodigo(rs.getString("codigo"));
                d.setNome(rs.getString("nome"));
                d.setCargaHorariaTotal(rs.getInt("carga_horaria_total"));
                lista.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar disciplinas: " + e.getMessage());
        }
        return lista;
    }
}
