package wolox.training.models;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import wolox.training.repositories.UserRepository;

@DataJpaTest

class UserTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenCreateUser_thenUserIsPersisted(){
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));
        entityManager.persistAndFlush(testUser);
        Optional<User> foundUser = userRepository.findByUsername(testUser.getUsername());
        assertThat(testUser.getUsername()).isEqualTo(foundUser.get().getUsername());
        assertThat(testUser.getName()).isEqualTo(foundUser.get().getName());
        assertThat(testUser.getBirthdate()).isEqualTo(foundUser.get().getBirthdate());
        assertThat(testUser.getBooks().size() == foundUser.get().getBooks().size());
    }

    @Test
    public void whenCreateUserWithoutUsername_thenThrowException(){
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));
        entityManager.persistAndFlush(testUser);
        assertThrows(NullPointerException.class, () -> {
            testUser.setUsername(null);
            userRepository.save(testUser);
        });
    }

    @Test
    public void whenCreateUserWithoutName_thenThrowException(){
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));
        entityManager.persistAndFlush(testUser);
        assertThrows(NullPointerException.class, () -> {
            testUser.setName(null);
            userRepository.save(testUser);
        });
    }

    @Test
    public void whenCreateUserWithoutBirthdate_thenThrowException(){
        User testUser = new User("guillotom","Tomás", LocalDate.parse("2001-05-24"));
        entityManager.persistAndFlush(testUser);
        assertThrows(NullPointerException.class, () -> {
            testUser.setBirthdate(null);
            userRepository.save(testUser);
        });
    }
}
