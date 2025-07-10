public class Ticket {
    private int id;
    private String nom;

    public Ticket(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String toJson() {
        return String.format("{\"id\": %d, \"nom\": \"%s\"}", id, nom);
    }
}
