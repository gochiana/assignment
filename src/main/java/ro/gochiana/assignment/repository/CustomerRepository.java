package ro.gochiana.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.gochiana.assignment.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
