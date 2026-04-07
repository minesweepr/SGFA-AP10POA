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
    public List<Disciplina> listarPorAluno(String matriculaAluno) {
        List<Disciplina> lista = new ArrayList<>();

        // Busca as disciplinas cruzando as tabelas GradeSemanal, HorarioDia e AulaDisciplina
        String sql = "SELECT DISTINCT d.codigo, d.nome, d.carga_horaria_total " +
                "FROM Disciplina d " +
                "JOIN AulaDisciplina ad ON d.codigo = ad.disciplina_codigo " +
                "JOIN HorarioDia hd ON ad.horario_dia_id = hd.id " +
                "JOIN GradeSemanal gs ON hd.grade_id = gs.id " +
                "WHERE gs.aluno_matricula = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matriculaAluno);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setCodigo(rs.getString("codigo"));
                d.setNome(rs.getString("nome"));
                d.setCargaHorariaTotal(rs.getInt("carga_horaria_total"));
                lista.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar disciplinas do aluno: " + e.getMessage());
        }

        return lista;
    }

    // usado para formatar o horario e dia que ocorre uma disciplica. ex: Seg 7:10-8:00
    public String buscarHorarioFormatado(String codigoDisciplina, String matriculaAluno){
        StringBuilder horarioFinal=new StringBuilder();
        String[] horasInicio={"07:10", "08:00", "08:50", "09:50", "10:40", "11:30"};
        String[] horasFim={"08:00", "08:50", "09:40", "10:40", "11:30", "12:20"};

        // Busca as disciplinas cruzando as tabelas pra garantir q a aula pertence ao aluno
        String sql="SELECT hd.dia_semana, ad.tempo_inicio, ad.quantidade_tempos " +
                "FROM horariodia hd " +
                "JOIN auladisciplina ad ON ad.horario_dia_id = hd.id " +
                "JOIN GradeSemanal gs ON hd.grade_id = gs.id " +
                "WHERE ad.disciplina_codigo = ? AND gs.aluno_matricula = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt=conn.prepareStatement(sql)){

            stmt.setString(1, codigoDisciplina);
            stmt.setString(2, matriculaAluno);
            ResultSet rs=stmt.executeQuery();

            while(rs.next()){
                String dia=rs.getString("dia_semana").substring(0, 3);
                int inicioIdx=rs.getInt("tempo_inicio") - 1;
                int qtd=rs.getInt("quantidade_tempos");
                int fimIdx=inicioIdx + qtd - 1;

                if(inicioIdx>=0 && inicioIdx<6) {
                    if(!horarioFinal.isEmpty()) horarioFinal.append("<br>"); //para disciplinas com mais d um dia (ex:4seg)
                    horarioFinal.append(dia).append(" ").append(horasInicio[inicioIdx]).append("-").append(horasFim[Math.min(fimIdx, 5)]);
                }
            }
        }catch(SQLException e){ e.printStackTrace(); }
        return !horarioFinal.isEmpty() ? horarioFinal.toString():"--:--";
    }
}

