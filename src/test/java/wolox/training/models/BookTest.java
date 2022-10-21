package wolox.training.models;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import wolox.training.repositories.BookRepository;

@DataJpaTest

class BookTest {
/*
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void whenCreateBook_thenBookIsPersisted(){
        Book testBook = new Book("Science Fiction","George Orwell","cover_image.jpg"
                ,"1984","-","Secker & Warburg","1949",328,"470015866");
        entityManager.persistAndFlush(testBook);
        Optional<Book> foundBook = bookRepository.findByAuthor("George Orwell");
        assertThat(testBook.getGenre()).isEqualTo(foundBook.get().getGenre());
        assertThat(testBook.getAuthor()).isEqualTo(foundBook.get().getAuthor());
        assertThat(testBook.getImage()).isEqualTo(foundBook.get().getImage());
        assertThat(testBook.getTitle()).isEqualTo(foundBook.get().getTitle());
        assertThat(testBook.getSubtitle()).isEqualTo(foundBook.get().getSubtitle());
        assertThat(testBook.getPublisher()).isEqualTo(foundBook.get().getPublisher());
        assertThat(testBook.getYear()).isEqualTo(foundBook.get().getYear());
        assertThat(testBook.getPages()==foundBook.get().getPages());
        assertThat(testBook.getIsbn()).isEqualTo(foundBook.get().getIsbn());
    }

    @Test
    public void whenCreateBookWithoutGenre_thenThrowException(){
        Book testBook = new Book("Science Fiction","George Orwell","cover_image.jpg"
                ,"1984","-","Secker & Warburg","1949",328,"470015866");
        entityManager.persistAndFlush(testBook);
        assertThrows(NullPointerException.class, () -> {
            testBook.setGenre(null);
            bookRepository.save(testBook);
        });
    }
    @Test
    public void whenCreateBookWithoutPages_thenThrowException(){
        Book testBook = new Book("Science Fiction","George Orwell","cover_image.jpg"
                ,"1984","-","Secker & Warburg","1949",328,"470015866");
        entityManager.persistAndFlush(testBook);
        assertThrows(NullPointerException.class, () -> {
            testBook.setPages(null);
            bookRepository.save(testBook);
        });
    }

 */
}
