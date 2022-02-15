package ro.gochiana.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.gochiana.assignment.entity.Book;
import ro.gochiana.assignment.exception.BookNotFoundException;
import ro.gochiana.assignment.exception.CustomerNotFoundException;
import ro.gochiana.assignment.service.BookService;

import javax.validation.Valid;

@RestController
@RequestMapping("/secure")
public class BookController {

    @Autowired
    private BookService bookRepository;

    @PostMapping("/book")
    public ResponseEntity<String> addBook(@Valid @RequestBody Book book) {
        bookRepository.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body("New book added");
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable Integer id) {
        return bookRepository.getBookById(id);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleValidationExceptions(BookNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

}
