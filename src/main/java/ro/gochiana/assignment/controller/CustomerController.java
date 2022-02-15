package ro.gochiana.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.gochiana.assignment.entity.Customer;
import ro.gochiana.assignment.exception.CustomerNotFoundException;
import ro.gochiana.assignment.service.CustomerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/library")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity<String> addCustomer(@Valid @RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("New customer created");
    }

    @GetMapping("/customer/{id}")
    public Customer getCustomer(@PathVariable Integer id) {
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted");
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<String> editCustomer(@Valid @RequestBody Customer editCustomer, @PathVariable Integer id) {
        customerService.editCustomerById(editCustomer, id);
        return ResponseEntity.status(HttpStatus.OK).body("Customer edited");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleValidationExceptions(CustomerNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
