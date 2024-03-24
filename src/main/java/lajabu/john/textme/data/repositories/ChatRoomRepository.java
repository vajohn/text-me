package lajabu.john.textme.data.repositories;

import java.util.Optional;
import lajabu.john.textme.data.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  Optional<ChatRoom> findByName(String name);
}
