package lajabu.john.textme.services;

import java.util.List;
import lajabu.john.textme.data.dao.MessageDto;
import lajabu.john.textme.data.models.Message;

public interface MessageService {

  Message save(Message message);

  List<Message> findAllByRoomAndVisibility(Long roomId, boolean visible);

  Message getMessageById(Long id);

  Message updateMessage(Message message);

  void deleteMessage(Long id, Long userId);

  Message saveLite(MessageDto messageDto);
}
