package lajabu.john.textme.services;

import java.util.Optional;
import lajabu.john.textme.data.models.ChatRoom;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.data.repositories.ChatRoomRepository;
import lajabu.john.textme.exceptions.Status403NotAllowedException;
import lajabu.john.textme.exceptions.Status404NotFoundException;
import lajabu.john.textme.services.implementations.ChatRoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ChatRoomServiceTest {

  @Mock
  private ChatRoomRepository chatRoomRepository;

  private ChatRoomService chatRoomService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    chatRoomService = new ChatRoomServiceImpl(chatRoomRepository);
  }

  @Test
  void saveChatRoom_success() {
    ChatRoom chatRoom = ChatRoom.builder()
        .name("Test Room")
        .description("Test chat room")
        .build();
    ChatRoom savedChatRoom = ChatRoom.builder()
        .name("Test Room")
        .description("Test chat room")
        .build();
    savedChatRoom.setId(1L);
    Mockito.when(chatRoomRepository.save(Mockito.any(ChatRoom.class))).thenReturn(savedChatRoom);
    ChatRoom result = chatRoomService.saveChatRoom(chatRoom);
    Assertions.assertEquals(savedChatRoom, result);
  }

  @Test
  void getChatRoomById_success() {
    ChatRoom chatRoom = ChatRoom.builder()
        .name("Test Room")
        .description("Test chat room")
        .build();
    chatRoom.setId(1L);
    Mockito.when(chatRoomRepository.findById(1L)).thenReturn(Optional.of(chatRoom));
    ChatRoom result = chatRoomService.getChatRoomById(1L);
    Assertions.assertEquals(chatRoom, result);
  }

  @Test
  void getChatRoomById_notFound() {
    Assertions.assertThrows(Status404NotFoundException.class,
        () -> chatRoomService.getChatRoomById(1L));
  }

  @Test
  void updateChatRoom_success() {
    ChatRoom existingChatRoom = ChatRoom.builder()
        .name("Old Room")
        .description("Old chat room")
        .build();
    ChatRoom updatedChatRoom = ChatRoom.builder()
        .name("New Room")
        .description("New chat room")
        .build();
    existingChatRoom.setId(1L);
    updatedChatRoom.setId(1L);
    Mockito.when(chatRoomRepository.findById(1L)).thenReturn(Optional.of(existingChatRoom));
    Mockito.when(chatRoomRepository.save(Mockito.any(ChatRoom.class))).thenReturn(updatedChatRoom);
    ChatRoom result = chatRoomService.updateChatRoom(updatedChatRoom);
    Assertions.assertEquals(updatedChatRoom, result);
  }

  @Test
  void updateChatRoom_notFound() {
    ChatRoom updatedChatRoom = ChatRoom.builder()
        .name("New Room")
        .description("New chat room")
        .build();
    updatedChatRoom.setId(1L);
    Assertions.assertThrows(Status404NotFoundException.class,
        () -> chatRoomService.updateChatRoom(updatedChatRoom));
  }

  @Test
  void deleteChatRoom_success() {
    ChatRoom chatRoom = ChatRoom.builder()
        .name("Test Room")
        .description("Test chat room")
        .build();
    chatRoom.setId(1L);
    User owner = new User("owner", "owner@email.com", "password");
    owner.setId(1L);
    chatRoom.setCreatedBy(owner);
    chatRoom.setModifiedBy(owner);
    Mockito.when(chatRoomRepository.findById(1L)).thenReturn(Optional.of(chatRoom));
    chatRoomService.deleteChatRoom(1L, owner.getId());
    Mockito.verify(chatRoomRepository).deleteById(1L);
  }

  @Test
  void deleteChatRoom_notFound() {
    Assertions.assertThrows(
        Status404NotFoundException.class, () -> chatRoomService.deleteChatRoom(1L, 1L));
  }

  @Test
  void deleteChatRoom_notOwner() {
    ChatRoom chatRoom = ChatRoom.builder()
        .name("Test Room")
        .description("Test chat room")
        .build();
    chatRoom.setId(1L);
    User owner = new User("owner", "owner@email.com", "password");
    owner.setId(1L);
    User notOwner = new User("notOwner", "notowner@email.com", "password");
    notOwner.setId(2L);
    chatRoom.setCreatedBy(owner);
    Mockito.when(chatRoomRepository.findById(1L)).thenReturn(Optional.of(chatRoom));
    Assertions.assertThrows(
        Status403NotAllowedException.class,
        () -> chatRoomService.deleteChatRoom(1L, notOwner.getId()));
  }
}
