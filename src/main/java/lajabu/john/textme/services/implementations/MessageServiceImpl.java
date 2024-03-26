package lajabu.john.textme.services.implementations;

import java.util.List;
import lajabu.john.textme.data.dao.MessageDto;
import lajabu.john.textme.data.models.ChatRoom;
import lajabu.john.textme.data.models.Message;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.data.projections.MessageProjection;
import lajabu.john.textme.data.repositories.MessageRepository;
import lajabu.john.textme.exceptions.Status404NotFoundException;
import lajabu.john.textme.services.ChatRoomService;
import lajabu.john.textme.services.MessageService;
import lajabu.john.textme.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;
  private final UserService userService;
  private final ChatRoomService chatRoomService;
  public static final String MESSAGE_NOT_FOUND = "Message not found";

  @Override
  @Transactional
  public Message save(MessageDto message) {
    User user = userService.getUserById(message.getSenderId());
    Message newMessage = Message.builder()
        .content(message.getContent())
        .chatRoom(chatRoomService.getChatRoomById(message.getChatRoomId()))
        .senderUser(user)
        .sender(user.getUsername())
        .visible(true)
        .build();
    return messageRepository.save(newMessage);
  }

  @Override
  @Transactional()
  public MessageDto saveLite(MessageDto messageDto) {
    ChatRoom chatRoom = chatRoomService.getChatRoomById(messageDto.getChatRoomId());
    User senderUser = userService.getUserById(messageDto.getSenderId());
    Message message = Message.builder()
        .content(messageDto.getContent())
        .chatRoom(chatRoom)
        .visible(true)
        .sender(senderUser.getUsername())
        .senderUser(senderUser)
        .build();
    message = messageRepository.save(message);
    return MessageDto.builder()
        .content(message.getContent())
        .sender(message.getSenderUser().getUsername())
        .chatRoomId(message.getChatRoom().getId())
        .senderId(message.getSenderUser().getId())
        .build();
  }
  @Override
  @Transactional(readOnly = true)
  public List<MessageProjection> findAllByRoomAndVisibility(Long roomId, boolean visible) {
    ChatRoom chatRoom = chatRoomService.getChatRoomById(roomId);
    return messageRepository.findAllByChatRoomIdAndVisible(chatRoom.getId(), visible);
  }

  @Override
  @Transactional(readOnly = true)
  public Message getMessageById(Long id) {
    return messageRepository.findById(id)
        .orElseThrow(() -> new Status404NotFoundException(MESSAGE_NOT_FOUND));
  }

  @Override
  @Transactional
  public Message updateMessage(Message message) {
    Message old = messageRepository.findById(message.getId())
        .orElseThrow(() -> new Status404NotFoundException(MESSAGE_NOT_FOUND));
    old.setContent(message.getContent());
    return messageRepository.save(old);
  }

  @Override
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
