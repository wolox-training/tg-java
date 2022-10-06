package wolox.training.services;

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
    public Book bookInfo(String isbn){
        URI uri = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("books")
                .queryParam("bibkeys", isbn)
                .queryParam("format","json")
                .queryParam("jscmd","data")
                .build()
                .toUri();
        OpenLibraryResponseDTO responseDTO = restTemplate
                .getForEntity(uri, OpenLibraryResponseDTO.class)
                .getBody();
        return responseDTO.convertToBook();
    }
}
