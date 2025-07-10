import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        TicketService ticketService = new TicketService();

        server.createContext("/add", new TicketHandler(ticketService));
        server.createContext("/next", new TicketHandler(ticketService));
        server.createContext("/peek", new TicketHandler(ticketService));
        server.createContext("/status", new TicketHandler(ticketService));

        server.setExecutor(null); // thread pool par défaut
        server.start();
        System.out.println("🚀 Serveur lancé sur http://localhost:8080");
    }
}
