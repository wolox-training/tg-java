package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Optional<Book> findByAuthor(String author);

    @Query("select b FROM Book b where b.publisher = :publisher OR b.genre = :genre"
            + " OR b.publicationYear = :year")
    Page<Book> findByPublisherOrGenreOrPublicationYear(@Param("publisher")String publisher,
            @Param("genre")String genre, @Param("year")String year, Pageable pageable);

    @Query("select b from Book b where b.id = :id OR b.genre = :genre OR b.author = :author"
            + " OR b.image = :image OR b.title = :title OR b.subtitle = :subtitle"
            + " OR b.publicationYear = :year OR b.pages = :pages OR b.isbn = :isbn"
            + " OR b.id > 0")
    Page<Book> findAll(@Param("id")long id, @Param("genre") String genre, @Param("author") String author,
            @Param("image")String img, @Param("title")String title, @Param("subtitle") String subtitle,
            @Param("year") String year, @Param("pages") int pages, @Param("isbn") String isbn,
            Pageable pageable);
}
