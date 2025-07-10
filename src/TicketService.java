import java.util.concurrent.atomic.AtomicInteger;

public class TicketService {
    private FIFO<Ticket> file = new FIFO<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    public Ticket ajouter(String nom) {
        Ticket t = new Ticket(idCounter.getAndIncrement(), nom);
        file.enqueue(t);
        return t;
    }

    public Ticket prochain() {
        return file.dequeue();
    }

    public Ticket voirProchain() {
        return file.peek();
    }

    public int taille() {
        return file.size();
    }

    public boolean estVide() {
        return file.isEmpty();
    }
}
