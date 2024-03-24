package lajabu.john.textme.data.repositories;

import java.util.Optional;
import lajabu.john.textme.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);
}
