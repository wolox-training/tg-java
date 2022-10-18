package wolox.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@WebMvcTest(BookController.class)

class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository mockBookRepository;
    @MockBean
    private UserRepository mockUserRepository;

    @Test
    void givenBook_whenGetAllBooks_thenReturnJsonArray() throws Exception{
        Book testBook = new Book("Science Fiction","George Orwell","cover_image.jpg"
                ,"1984","-","Secker & Warburg","1949",328,"470015866");
        List<Book> allBooks = Arrays.asList(testBook);

        Mockito.when(mockBookRepository.findAll()).thenReturn(allBooks);
        mvc.perform(MockMvcRequestBuilders.get("/api/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void whenFindABookByIdThatExists_thenReturnJson() throws Exception{
        Book testBook = new Book("Science Fiction","George Orwell","cover_image.jpg"
                ,"1984","-","Secker & Warburg","1949",328,"470015866");

        Mockito.when(mockBookRepository.findById(0L)).thenReturn(Optional.of(testBook));
        mvc.perform(MockMvcRequestBuilders.get("/api/books/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":0,"
                        + "\"genre\":\"Science Fiction\",\"author\":\"George Orwell\","
                        + "\"image\":\"cover_image.jpg\",\"title\":\"1984\",\"subtitle\":\"-\","
                        + "\"publisher\":\"Secker & Warburg\",\"year\":\"1949\",\"pages\":328,"
                        + "\"isbn\":\"470015866\"}"));
    }

    @Test
    void whenFindABookByIdThatDoesNotExist_thenThrowException(){
        Book testBook = new Book("Science Fiction","George Orwell","cover_image.jpg"
                ,"1984","-","Secker & Warburg","1949",328,"470015866");
        assertThrows(org.springframework.web.util.NestedServletException.class, () -> {

            Mockito.when(mockBookRepository.findById(0L)).thenReturn(Optional.of(testBook));
            mvc.perform(MockMvcRequestBuilders.get("/api/books/2").contentType(MediaType.APPLICATION_JSON));
        });
    }

    @Test
    void whenFindByAuthorThatExists_thenReturnJson() throws Exception{
        Book testBook = new Book("Science Fiction","George Orwell","cover_image.jpg"
                ,"1984","-","Secker & Warburg","1949",328,"470015866");

        Mockito.when(mockBookRepository.findByAuthor("George Orwell")).thenReturn(Optional.of(testBook));
        mvc.perform(MockMvcRequestBuilders.get("/api/books/George Orwell").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":0,"
                        + "\"genre\":\"Science Fiction\",\"author\":\"George Orwell\","
                        + "\"image\":\"cover_image.jpg\",\"title\":\"1984\",\"subtitle\":\"-\","
                        + "\"publisher\":\"Secker & Warburg\",\"year\":\"1949\",\"pages\":328,"
                        + "\"isbn\":\"470015866\"}"));
    }

    @Test
    void whenFindByAuthorThatDoesNotExist_thenThrowException(){
        Book testBook = new Book("Science Fiction","George Orwell","cover_image.jpg"
                ,"1984","-","Secker & Warburg","1949",328,"470015866");
        assertThrows(org.springframework.web.util.NestedServletException.class, () -> {
            Mockito.when(mockBookRepository.findByAuthor("George Orwell")).thenReturn(null);
            mvc.perform(MockMvcRequestBuilders.get("/api/books/author/Aldous Huxley").contentType(MediaType.APPLICATION_JSON));
        });
    }

    @Test
    @WithMockUser
    void whenSearchByIsbnAndDoesNotExist_thenThrowException() throws Exception {
        Mockito.when(mockBookRepository.findByIsbn("123")).thenReturn(null);
        mvc.perform(MockMvcRequestBuilders.get("/api/books/isbn/123")
                .contentType(MediaType.APPLICATION_JSON));
    }
}
