package wolox.training.repositories;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u from User u WHERE u.birthdate BETWEEN :date1 AND :date2 "
            + "OR UPPER(u.name) like UPPER(concat('%', :str,'%'))")
    Page<User> findByBirthdateBetweenAndNameIgnoreCaseContaining(@Param("date1")LocalDate date1,
            @Param("date2")LocalDate date2, @Param("str")String str, Pageable pageable);
}
