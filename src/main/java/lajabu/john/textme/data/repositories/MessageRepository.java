package lajabu.john.textme.data.repositories;

import java.util.List;
import lajabu.john.textme.data.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

  List<Message> findAllByChatRoomIdAndVisible(Long roomId, boolean visible);
}
