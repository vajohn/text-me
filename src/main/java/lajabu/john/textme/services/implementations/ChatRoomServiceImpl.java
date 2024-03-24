package lajabu.john.textme.services.implementations;

import java.util.List;
import java.util.Optional;
import lajabu.john.textme.data.models.ChatRoom;
import lajabu.john.textme.data.models.User;
import lajabu.john.textme.data.repositories.ChatRoomRepository;
import lajabu.john.textme.exceptions.Status403NotAllowedException;
import lajabu.john.textme.exceptions.Status404NotFoundException;
import lajabu.john.textme.services.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

  public static final String CHAT_ROOM_NOT_FOUND = "Chat room not found";
  private final ChatRoomRepository chatRoomRepository;

  @Transactional
  public ChatRoom saveChatRoom(ChatRoom chatRoom) {
    return chatRoomRepository.save(chatRoom);
  }

  @Transactional(readOnly = true)
  public ChatRoom getChatRoomById(Long id) {
    return chatRoomRepository.findById(id)
        .orElseThrow(() -> new Status404NotFoundException(CHAT_ROOM_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public List<ChatRoom> getAllChatRooms() {
    return chatRoomRepository.findAll();
  }

  @Transactional
  public ChatRoom updateChatRoom(ChatRoom chatRoom) {
    ChatRoom old = chatRoomRepository.findById(chatRoom.getId())
        .orElseThrow(() -> new Status404NotFoundException(CHAT_ROOM_NOT_FOUND));
    old.setName(chatRoom.getName());
    old.setDescription(chatRoom.getDescription());
    old.setModifiedBy(chatRoom.getModifiedBy());
    return chatRoomRepository.save(chatRoom);
  }
  @Transactional
  public void deleteChatRoom(Long id, Long userId) {
    ChatRoom chatRoom = chatRoomRepository.findById(id)
        .orElseThrow(() -> new Status404NotFoundException(CHAT_ROOM_NOT_FOUND));
    if (chatRoom.getCreatedBy().getId().equals(userId)) {
      chatRoomRepository.deleteById(id);
    } else {
      throw new Status403NotAllowedException("You are not the owner of this chat room");
    }
  }

  @Transactional
  public void createDefaultChatRoom(User user) {
    Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByName("General");
    if (existingChatRoom.isPresent()) {
      return;
    }

    ChatRoom chatRoom = ChatRoom.builder()
        .name("General")
        .description("General chat room")
        .createdBy(user)
        .build();
    chatRoomRepository.save(chatRoom);
  }


}
