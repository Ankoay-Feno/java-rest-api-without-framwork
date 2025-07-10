import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FIFO<T> {
    private LinkedList<T> queue = new LinkedList<>();

    public void enqueue(T element) {
        queue.addLast(element);
    }

    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("File vide");
        return queue.removeFirst();
    }

    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("File vide");
        return queue.getFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}
