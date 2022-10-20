package wolox.training.controllers;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.services.OpenLibraryService;


@WireMockTest(httpPort = 8081)
@AutoConfigureMockMvc
@SpringBootTest
class BookControllerTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OpenLibraryService openLibraryService;

    @MockBean
    private BookRepository mockBookRepository;
    @MockBean
    private UserRepository mockUserRepository;

    @BeforeAll
    static void init(){
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8081);
    }

    @Test
    @WithMockUser
    void givenBook_whenGetAllBooks_thenReturnJsonArray() throws Exception{
        Mockito.when(mockBookRepository.findAll(1,null,null,null,null,
                null,null,0,null, PageRequest.of(0,1))); //.thenReturn(allBooks);
        mvc.perform(MockMvcRequestBuilders.get("/api/books/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
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
    @WithMockUser
    void whenFindABookByIdThatDoesNotExist_thenThrowException(){
        Book testBook = new Book("Science Fiction","George Orwell","cover_image.jpg"
                ,"1984","-","Secker & Warburg","1949",328,"470015866");
        assertThrows(org.springframework.web.util.NestedServletException.class, () -> {

            Mockito.when(mockBookRepository.findById(0L)).thenReturn(Optional.of(testBook));
            mvc.perform(MockMvcRequestBuilders.get("/api/books/2").contentType(MediaType.APPLICATION_JSON));
        });
    }

    @Test
    @WithMockUser
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
    @WithMockUser
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
    void whenSearchByIsbnAndDoesNotExist_thenFindItOnOpenLibrary() throws Exception {
        stubFor(WireMock.get("/api/books/isbn/0451521234").willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBodyFile("bookBody.json")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8081/api/books/isbn/0451521234");
        HttpResponse response = httpClient.execute(request);
    }

}
