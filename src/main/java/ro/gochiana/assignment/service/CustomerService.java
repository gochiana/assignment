package ro.gochiana.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.gochiana.assignment.entity.Customer;
import ro.gochiana.assignment.exception.CustomerNotFoundException;
import ro.gochiana.assignment.repository.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer getCustomerById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new CustomerNotFoundException(id));
    }


    public void deleteCustomerById(Integer id) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        existingCustomer.orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.deleteById(id);
    }

    public void editCustomerById(Customer editCustomer, Integer id) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        Customer customer = existingCustomer.orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setUsername(editCustomer.getUsername());
        customer.setName(editCustomer.getName());
        customerRepository.save(customer);
    }
}
