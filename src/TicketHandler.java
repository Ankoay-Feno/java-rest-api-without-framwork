import com.sun.net.httpserver.*;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class TicketHandler implements HttpHandler {
    private TicketService service;

    public TicketHandler(TicketService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        String response = "";

        try {
            switch (path) {
                case "/add":
                    if ("POST".equals(method)) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                        String nom = reader.readLine();
                        Ticket t = service.ajouter(nom);
                        response = t.toJson();
                    }
                    break;

                case "/next":
                    if ("GET".equals(method)) {
                        Ticket t = service.prochain();
                        response = t.toJson();
                    }
                    break;

                case "/peek":
                    if ("GET".equals(method)) {
                        Ticket t = service.voirProchain();
                        response = t.toJson();
                    }
                    break;

                case "/status":
                    if ("GET".equals(method)) {
                        response = String.format("{\"size\": %d, \"isEmpty\": %s}",
                            service.taille(), service.estVide());
                    }
                    break;

                default:
                    response = "{\"message\":\"Endpoint inconnu\"}";
            }
        } catch (Exception e) {
            response = "{\"error\": \"" + e.getMessage() + "\"}";
        }

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
