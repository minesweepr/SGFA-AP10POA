package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    
    private static final String URL = "jdbc:mysql://localhost:3306/SeLigaFalta";
    
    private static final String USER = "root";
    
    private static final String PASS = "qwe123!A#"; 

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC do MySQL não encontrado");
            return null;
        } catch (SQLException e) {
            System.err.println("Erro de Autenticação/Banco offline: " + e.getMessage());
            return null;
        }
    }
}
