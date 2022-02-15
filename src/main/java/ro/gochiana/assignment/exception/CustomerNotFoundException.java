package ro.gochiana.assignment.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Integer id) {
        super("Could not find the customer with id: " + id);
    }
}
