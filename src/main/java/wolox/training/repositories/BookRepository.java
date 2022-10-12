package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByAuthor(String author);

    @Query("SELECT b FROM Book b WHERE b.publisher = :publisher OR b.genre = :genre"
            + " OR b.publicationYear = :publicationYear")
    List<Optional<Book>> findByPublisherOrGenreOrPublicationYear(@Param("publisher")String publisher,
            @Param("genre")String genre, @Param("publicationYear")String year);
}
