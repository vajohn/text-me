package lajabu.john.textme.services;

import java.util.List;
import lajabu.john.textme.data.models.ChatRoom;
import lajabu.john.textme.data.models.User;

public interface ChatRoomService {
  ChatRoom saveChatRoom(ChatRoom chatRoom);

  ChatRoom getChatRoomById(Long id);

  List<ChatRoom> getAllVisibleChatRooms();

  ChatRoom updateChatRoom(ChatRoom chatRoom);

  void deleteChatRoom(Long id, Long userId);

  void createDefaultChatRoom(User user);
}
