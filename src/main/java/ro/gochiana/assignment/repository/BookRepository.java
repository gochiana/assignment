package ro.gochiana.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.gochiana.assignment.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
