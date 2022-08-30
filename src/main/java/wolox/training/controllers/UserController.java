package wolox.training.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.exceptions.UsernameMismatchException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @GetMapping
    public Optional<User> findByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    @PutMapping("/{username}")
    public User updateUser(@RequestBody User user, @PathVariable long id) {
        if (user.getId() != id) {
            throw new UsernameMismatchException("Username does not match!");
        }
        try {
            userRepository.findById(id);
        } catch (UserNotFoundException e) {
        }
        return userRepository.save(user);
    }

    @DeleteMapping("/{username}")
    public void delete(@PathVariable Long id) {
        try {
            userRepository.findById(id);
        } catch (UserNotFoundException e) {
        }
        userRepository.deleteById(id);
    }

    @PostMapping
    void addBookToUsersCollection(@RequestBody User user, @PathVariable long bookId ){
        try {
            Optional<Book> book = bookRepository.findById(bookId);
            user.addBookToCollection(book);
        }catch (BookNotFoundException e){}
    }

    @DeleteMapping
    void removeBookFromUsersCollection(@RequestBody User user, @PathVariable long bookId){
        try{
            Optional<Book> book = bookRepository.findById(bookId);
            user.removeBookFromCollection(book);
        }catch (BookNotFoundException e){}
    }


}
