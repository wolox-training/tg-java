package wolox.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@WebMvcTest(UserController.class)

class UserControllerTest {
/*
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository mockUserRepository;
    @MockBean
    private BookRepository mockBookRepository;

    @Test
    void givenUserWithAllParameters_thenCreateUser() throws Exception{
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));
        Mockito.when(mockUserRepository.save(testUser)).thenReturn(testUser);
        mvc.perform(MockMvcRequestBuilders.post("/api/users").contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenUser_whenGetAllUsers_thenReturnJsonArray() throws Exception{
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));
        List<User> allUsers = Arrays.asList(testUser);

        Mockito.when(mockUserRepository.findAll()).thenReturn(allUsers);
        mvc.perform(MockMvcRequestBuilders.get("/api/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void whenFindByUsername_thenReturnUser() throws Exception {
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));

        Mockito.when(mockUserRepository.findByUsername("guillotom")).thenReturn(Optional.of(testUser));
        mvc.perform(MockMvcRequestBuilders.get("/api/users/guillotom")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":0,\"username\":\"guillotom\",\"name\":\"Tomás\","
                           + "\"birthdate\":\"2001-05-24\",\"books\":[]}"
                ));
    }

    @Test
    void whenFindByUsernameThatDoesNotExist_thenThrowException() {
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));
        assertThrows(org.springframework.web.util.NestedServletException.class, () -> {

            Mockito.when(mockUserRepository.findByUsername("guillotom")).thenReturn(Optional.of(testUser));
            mvc.perform(MockMvcRequestBuilders.get("/api/users/asd").contentType(MediaType.APPLICATION_JSON));
        });
    }

    @Test
    void whenAddABookToUsersCollection_thenReturnUserWithBookList() throws Exception{
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));
        Book testBook = new Book("Science Fiction","George Orwell","cover_image.jpg"
                ,"1984","-","Secker & Warburg","1949",328,"470015866");
        testUser.addBookToCollection(testBook);

        Mockito.when(mockUserRepository.findByUsername("guillotom")).thenReturn(Optional.of(testUser));
        mvc.perform(MockMvcRequestBuilders.get("/api/users/guillotom")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"id\":0,\"username\":\"guillotom\",\"name\":\"Tomás\","
                                + "\"birthdate\":\"2001-05-24\",\"books\":[{\"id\":0,"
                                + "\"genre\":\"Science Fiction\",\"author\":\"George Orwell\","
                                + "\"image\":\"cover_image.jpg\",\"title\":\"1984\",\"subtitle\":\"-\","
                                + "\"publisher\":\"Secker & Warburg\",\"year\":\"1949\",\"pages\":328,"
                                + "\"isbn\":\"470015866\"}]}"
                ));
    }

    @Test
    void whenRemoveBookFromUsersCollection_thenReturnUserWithEmptyBookList() throws Exception{
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));
        Book testBook = new Book("Science Fiction","George Orwell","cover_image.jpg"
                ,"1984","-","Secker & Warburg","1949",328,"470015866");
        testUser.addBookToCollection(testBook);
        testUser.removeBookFromCollection(testBook);

        Mockito.when(mockUserRepository.findByUsername("guillotom")).thenReturn(Optional.of(testUser));
        mvc.perform(MockMvcRequestBuilders.get("/api/users/guillotom")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"id\":0,\"username\":\"guillotom\",\"name\":\"Tomás\","
                                + "\"birthdate\":\"2001-05-24\",\"books\":[]}"
                ));
    }

    @Test
    void whenUpdateUser_thenGetUpdatedUser() throws Exception {
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));
        Mockito.when(mockUserRepository.save(testUser)).thenReturn(testUser);
        mvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON));

       testUser.setUsername("tom");
       Mockito.when(mockUserRepository.save(testUser)).thenReturn(testUser);
       mvc.perform(MockMvcRequestBuilders.put("/api/users/id/0")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("{\"id\":0,\"username\":\"tom\",\"name\":\"Tomás\","
                               + "\"birthdate\":\"2001-05-24\",\"books\":[]}"))
                       .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/api/users/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"id\":0,\"username\":\"tom\",\"name\":\"Tomás\","
                        + "\"birthdate\":\"2001-05-24\",\"books\":[]}"
                ));
    }

    @Test
    void givenUserID_thenDeleteUser() throws Exception{
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));
        Mockito.when(mockUserRepository.save(testUser)).thenReturn(testUser);
        mvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON));

        mvc.perform(MockMvcRequestBuilders.delete("/api/users/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

 */
}
