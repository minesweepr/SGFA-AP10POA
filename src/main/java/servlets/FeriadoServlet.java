package servlets;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/api/feriados")
public class FeriadoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String apiKey = null;
        try {
            InitialContext ctx = new InitialContext();
            apiKey = (String) ctx.lookup("java:comp/env/api.key");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (apiKey == null) {
            throw new RuntimeException("API_KEY não encontrada no contexto do Tomcat!");
        }

        try {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("https://feriadosapi.com/api/v1/feriados/cidade/3304557?ano=2026&facultativos=true"))
                    .header("Authorization", "Bearer " + apiKey)
                    .GET()
                    .build();

            HttpResponse<String> apiResponse = client.send(req, HttpResponse.BodyHandlers.ofString(java.nio.charset.StandardCharsets.UTF_8));

            response.getWriter().write(apiResponse.body());

        } catch (Exception e) {
            response.getWriter().write("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }
}