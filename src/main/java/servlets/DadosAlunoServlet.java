package servlets;

import conectores.RegistroFaltaConector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DadosAlunoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String matricula = "123456"; 
        RegistroFaltaConector historico = new RegistroFaltaConector();

        int faltasSeguranca = historico.contarTotalFaltas(matricula, "4SEG");
        int faltasModelagem = historico.contarTotalFaltas(matricula, "4MOD");
        int faltasEmpreendedorismo = historico.contarTotalFaltas(matricula, "4EMP");

        String json = "{"
            + "\"nome\": \"Athena\","
            + "\"status\": \"Regular\","
            + "\"disciplinas\": ["
            + "  {"
            + "    \"id\": 1,"
            + "    \"sigla\": \"4SEG\","
            + "    \"professor\": \"Dr. Segurança\","
            + "    \"faltasAtuais\": " + faltasSeguranca + ","
            + "    \"limiteFaltas\": 15,"
            + "    \"corTabela\": \"bg-red\","
            + "    \"agenda\": [{\"dia\": 1, \"tempos\": [0, 1]}]"
            + "  },"
            + "  {"
            + "    \"id\": 2,"
            + "    \"sigla\": \"4MOD\","
            + "    \"professor\": \"Prof. Modelagem\","
            + "    \"faltasAtuais\": " + faltasModelagem + ","
            + "    \"limiteFaltas\": 15,"
            + "    \"corTabela\": \"bg-green\","
            + "    \"agenda\": [{\"dia\": 1, \"tempos\": [2, 3]}]"
            + "  },"
            + "  {"
            + "    \"id\": 3,"
            + "    \"sigla\": \"4EMP\","
            + "    \"professor\": \"Prof. Empreendedorismo\","
            + "    \"faltasAtuais\": " + faltasEmpreendedorismo + ","
            + "    \"limiteFaltas\": 15,"
            + "    \"corTabela\": \"bg-blue\","
            + "    \"agenda\": [{\"dia\": 2, \"tempos\": [2, 3]}]"
            + "  }"
            + "]"
            + "}";

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
