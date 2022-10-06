package wolox.training.domain;

import wolox.training.models.Book;

public class OpenLibraryResponseDTO {
    //public RequestDTO request;
    public BookDTO bookDTO;
    public Book convertToBook(){
        Book book = new Book();
        //book.setIsbn(this.bookDTO.isbn_10);
        book.setTitle(this.bookDTO.title);
        book.setSubtitle(this.bookDTO.subtitle);
        book.setAuthor(this.bookDTO.authors.stream().findFirst().orElse(""));
        book.setYear(this.bookDTO.publish_date);
        book.setPublisher(this.bookDTO.publisher);
        book.setPages(this.bookDTO.number_of_pages);
        return book;
    }
}
