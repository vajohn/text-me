package lajabu.john.textme.services;

import java.util.Optional;
import lajabu.john.textme.data.models.ChatRoom;
import lajabu.john.textme.data.models.Message;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.data.repositories.MessageRepository;
import lajabu.john.textme.exceptions.Status404NotFoundException;
import lajabu.john.textme.services.implementations.ChatRoomServiceImpl;
import lajabu.john.textme.services.implementations.MessageServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MessageServiceTest {

  @Mock
  private MessageRepository messageRepository;
  @Mock
  private UserService userService;
  @Mock
  private ChatRoomServiceImpl chatRoomService;

  private MessageServiceImpl messageServiceImpl;

  private User user;
  private ChatRoom chatRoom;
  private Message savedMessage;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    messageServiceImpl = new MessageServiceImpl(messageRepository, userService, chatRoomService);
    user = User.builder()
        .email("old@email.com")
        .username("oldUser")
        .password("password")
        .build();
    user.setId(1L);
    chatRoom = ChatRoom.builder()
        .name("Test Room")
        .description("Test chat room")
        .build();
    chatRoom.setId(1L);
    savedMessage = Message.builder()
        .content("Test content")
        .senderUser(user)
        .chatRoom(chatRoom)
        .build();
    savedMessage.setId(1L);
  }

  @Test
  void saveMessage_success() {
    Message message = Message.builder()
        .content("Test content")
        .senderUser(user)
        .chatRoom(chatRoom)
        .build();
    savedMessage.setId(1L);
    Mockito.when(messageRepository.save(Mockito.any(Message.class))).thenReturn(savedMessage);
    Message result = messageRepository.save(message);
    Assertions.assertEquals(savedMessage, result);
  }

  @Test
  void getMessageById_success() {
    Mockito.when(messageRepository.findById(1L)).thenReturn(Optional.of(savedMessage));
    Message result = messageServiceImpl.getMessageById(1L);
    Assertions.assertEquals(savedMessage, result);
  }

  @Test
  void getMessageById_notFound() {
    Assertions.assertThrows(Status404NotFoundException.class,
        () -> messageServiceImpl.getMessageById(1L));
  }

  @Test
  void updateMessage_success() {
    Message updatedMessage = Message.builder()
        .content("new content")
        .senderUser(user)
        .chatRoom(chatRoom)
        .build();
    updatedMessage.setId(1L);
    Mockito.when(messageRepository.findById(1L)).thenReturn(Optional.of(savedMessage));
    Mockito.when(messageRepository.save(Mockito.any(Message.class))).thenReturn(updatedMessage);
    Message result = messageServiceImpl.updateMessage(updatedMessage);
    Assertions.assertEquals(updatedMessage, result);
  }

  @Test
  void updateMessage_notFound() {
    Message updatedMessage = Message.builder()
        .content("new content")
        .senderUser(user)
        .chatRoom(chatRoom)
        .build();
    updatedMessage.setId(1L);
    Assertions.assertThrows(Status404NotFoundException.class,
        () -> messageServiceImpl.updateMessage(updatedMessage));
  }

  @Test
  void deleteMessage_senderOwner() {
    Mockito.when(messageRepository.findById(1L)).thenReturn(Optional.of(savedMessage));
    Mockito.when(userService.getUserById(1L)).thenReturn(user);
    messageServiceImpl.deleteMessage(1L, user.getId());
    Mockito.verify(messageRepository).deleteById(1L);
  }

  @Test
  void deleteMessage_notSender() {
    User deleter = User.builder()
        .email("deleter@email.com")
        .username("deleterUser")
        .password("password")
        .build();
    deleter.setId(2L);
    Mockito.when(messageRepository.findById(1L)).thenReturn(Optional.of(savedMessage));
    Mockito.when(userService.getUserById(2L)).thenReturn(deleter);
    messageServiceImpl.deleteMessage(1L, 2L);
    Mockito.verify(messageRepository, Mockito.times(0)).deleteById(1L);
    Mockito.verify(messageRepository, Mockito.times(1)).save(Mockito.any(Message.class));
  }

}