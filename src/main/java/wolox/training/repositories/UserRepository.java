package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import net.bytebuddy.asm.Advice.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    /*@Query("select u from User u where UPPER(x.name) like %?3% and u.birthdate between ?1 and ?2")
    List<Optional<User>> findByBirthdateAndName(LocalDate date1, LocalDate date2, String name);*/

    List<Optional<User>> findByBirthdateBetweenAndNameIgnoreCaseContaining(LocalDate date1, LocalDate date2, String str);
}
