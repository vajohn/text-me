package lajabu.john.textme.services;

import java.util.List;
import lajabu.john.textme.data.models.ChatRoom;
import lajabu.john.textme.data.models.Message;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.data.repositories.MessageRepository;
import lajabu.john.textme.exceptions.Status404NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

  private final MessageRepository messageRepository;
  private final UserService userService;
  private final ChatRoomService chatRoomService;
  public static final String MESSAGE_NOT_FOUND = "Message not found";

  @Transactional
  public Message save(Message message) {
    return messageRepository.save(message);
  }

  @Transactional(readOnly = true)
  public List<Message> findAllByRoomAndVisibility(Long roomId, boolean visible) {
    ChatRoom chatRoom = chatRoomService.getChatRoomById(roomId);
    return messageRepository.findAllByChatRoomIdAndVisible(chatRoom.getId(), visible);
  }

  @Transactional(readOnly = true)
  public Message getMessageById(Long id) {
    return messageRepository.findById(id)
        .orElseThrow(() -> new Status404NotFoundException(MESSAGE_NOT_FOUND));
  }

  @Transactional
  public Message updateMessage(Message message) {
    Message old = messageRepository.findById(message.getId())
        .orElseThrow(() -> new Status404NotFoundException(MESSAGE_NOT_FOUND));
    old.setContent(message.getContent());
    return messageRepository.save(message);
  }

  @Transactional
  public void deleteMessage(Long id, Long userId) {
    Message message = messageRepository.findById(id)
        .orElseThrow(() -> new Status404NotFoundException(MESSAGE_NOT_FOUND));
    User user = userService.getUserById(userId);
    if (message.getSenderUser().getId().equals(userId)) {
      messageRepository.deleteById(id);
    } else {
      // we save the message with the user who deleted it
      message.setModifiedByUser(user);
      message.setVisible(false);
      messageRepository.save(message);
    }
  }

}
