package ro.gochiana.assignment.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Integer id) {
        super("Could not find the book with id: " + id);
    }
}
