package ro.gochiana.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ro.gochiana.assignment.entity.Customer;
import ro.gochiana.assignment.exception.CustomerNotFoundException;
import ro.gochiana.assignment.repository.CustomerRepository;
import ro.gochiana.assignment.service.CustomerService;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void shouldGetCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("gabriel@mail.com");
        customer.setName("Gabriel");
        when(customerService.getCustomerById(anyInt())).thenReturn(customer);
        this.mockMvc.perform(get("/library/customer/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Gabriel")))
                .andExpect(jsonPath("$.username", is("gabriel@mail.com")));
    }

    @Test
    public void shouldAddCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("gabriel@mail.com");
        customer.setName("Gabriel");
        doNothing().when(customerService).addCustomer(customer);

        this.mockMvc.perform(post("/library/customer").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(customer)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotAddCustomerBadEmail() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("invalid_mail");
        customer.setName("Gabriel");
        doNothing().when(customerService).addCustomer(customer);

        this.mockMvc.perform(post("/library/customer").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(customer)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$.username", is("username should be a valid email")));
    }

    @Test
    public void shouldNotAddCustomerEmptyInputs() throws Exception {
        Customer customer = new Customer();
        doNothing().when(customerService).addCustomer(customer);

        this.mockMvc.perform(post("/library/customer").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(customer)))
                .andDo(print())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$.name", is("name must not be empty")))
                .andExpect(jsonPath("$.username", is("username must not be empty")));
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("invalid_mail");
        customer.setName("Gabriel");
        when(customerService.getCustomerById(anyInt())).thenReturn(customer);

        this.mockMvc.perform(delete("/library/customer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotDeleteCustomer() throws Exception {
        when(customerService.getCustomerById(anyInt())).thenThrow(CustomerNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/patient/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("gabriel@mail.com");
        customer.setName("Gabriel");
        when(customerService.getCustomerById(anyInt())).thenReturn(customer);

        this.mockMvc.perform(put("/library/customer/1")
                        .contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(customer)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotUpdateCustomerBadEmail() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("badEmail.com");
        customer.setName("Gabriel");
        when(customerService.getCustomerById(anyInt())).thenReturn(customer);

        this.mockMvc.perform(put("/library/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(customer)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }
}
