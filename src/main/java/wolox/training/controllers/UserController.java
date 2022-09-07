package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/api/users")
@Api
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

    @GetMapping("/username/{username}")
    public Optional<User> findByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            userRepository.findById(id);
        } catch (UserNotFoundException e) {
        }
        userRepository.deleteById(id);
    }

    /**
     * This method adds a book object to a user's collection.
     * @param user: User who wants a book to be added (User)
     * @param book: Book to be added (Book)
     */
    @PatchMapping("/{user}")
    @ApiOperation(value = "Given a book, add it to a user's collection", response= User.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message="Book successfully added to collection."),
            @ApiResponse(code=401, message="You are not authorized to view the resource."),
            @ApiResponse(code=403, message="Accessing the resource you were trying to reach is forbidden."),
            @ApiResponse(code=404, message="The resource you were trying to reach is not found.")
    })
    void addBookToUsersCollection(@ApiParam(value="User who wants to add the book", required = true)
    @PathVariable User user, @ApiParam(value="Book to be added", required = true) @RequestBody Book book ){
        try {
            user.addBookToCollection(book);
        }catch (BookNotFoundException e){}
    }

    /**
     * This method deletes a book objects from a user's collection.
     * @param user: User who has a book on their collection (User)
     * @param book: Book to be removed (Book)
     */
    @GetMapping("/{user}")
    void removeBookFromUsersCollection(@PathVariable User user, @RequestBody Book book){
        try{
            user.removeBookFromCollection(book);
        }catch (BookNotFoundException e){}
    }


}
