package ro.gochiana.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.gochiana.assignment.entity.Book;
import ro.gochiana.assignment.exception.BookNotFoundException;
import ro.gochiana.assignment.repository.BookRepository;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public void addBook(Book customer) {
        bookRepository.save(customer);
    }

    public Book getBookById(Integer id) {
        Optional<Book> customer = bookRepository.findById(id);
        return customer.orElseThrow(() -> new BookNotFoundException(id));
    }
}
