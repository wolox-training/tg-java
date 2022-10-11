package wolox.training.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import wolox.training.domain.OpenLibraryResponseDTO;
import wolox.training.models.Book;
@Service
public class OpenLibraryService {
    private static final String API_URL = "https://openlibrary.org/api/";
    private final RestTemplate restTemplate = new RestTemplate();


    public Book bookInfo(String isbn) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("books")
                .queryParam("bibkeys", isbn)
                .queryParam("format","json")
                .queryParam("jscmd","data")
                .build()
                .toUri();
        String json = restTemplate
                .getForEntity(uri, String.class)
                .getBody();
        JsonNode jsonNode = objectMapper.readTree(json);
        String bookBody = jsonNode.get(isbn).asText();
        OpenLibraryResponseDTO responseDTO = objectMapper.readValue(bookBody, OpenLibraryResponseDTO.class);
        return responseDTO.convertToBook();
    }
}
