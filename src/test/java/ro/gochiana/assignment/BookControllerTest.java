package ro.gochiana.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ro.gochiana.assignment.controller.BookController;
import ro.gochiana.assignment.entity.Book;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookController bookController;

    @Test
    @WithMockUser(username = "gabriel")
    public void shouldGetBookSecure() throws Exception {
        Book book = new Book();
        book.setAuthor("Charles Dickens");
        book.setTitle("A Christmas Carol");
        when(bookController.getBook(anyInt())).thenReturn(book);
        this.mockMvc.perform(get("/secure/book/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("A Christmas Carol")))
                .andExpect(jsonPath("$.author", is("Charles Dickens")));
    }

    @Test
    public void shouldNotGetBookInsecure() throws Exception {
        Book book = new Book();
        book.setAuthor("Charles Dickens");
        book.setTitle("A Christmas Carol");
        when(bookController.getBook(anyInt())).thenReturn(book);
        this.mockMvc.perform(get("/secure/book/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
