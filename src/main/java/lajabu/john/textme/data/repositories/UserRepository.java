package lajabu.john.textme.data.repositories;

import java.util.List;
import java.util.Optional;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.data.projections.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  List<UserProjection> findAllBy();
}
