package lajabu.john.textme.controllers;

import java.util.List;
import lajabu.john.textme.data.dao.ChatRoomDto;
import lajabu.john.textme.data.models.ChatRoom;
import lajabu.john.textme.services.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat-room")
public class ChatRoomController {

  private final ChatRoomService chatRoomService;

  @PostMapping
  public ResponseEntity<ChatRoom> createChatRoom(@RequestBody ChatRoom chatRoom) {
    return ResponseEntity.ok(chatRoomService.saveChatRoom(chatRoom));
  }

  @GetMapping
  public ResponseEntity<List<ChatRoomDto>> getAllChatRooms() {
    List<ChatRoom> chatRooms = chatRoomService.getAllVisibleChatRooms();
    List<ChatRoomDto> chatRoomDTOs = chatRooms.stream()
        .map(ChatRoom::toDTO)
        .toList();
    return ResponseEntity.ok(chatRoomDTOs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ChatRoomDto> getChatRoom(@PathVariable Long id) {
    ChatRoom chatRoom = chatRoomService.getChatRoomById(id);
    return ResponseEntity.ok(chatRoom.toDTO());
  }

  @PutMapping
  public ResponseEntity<ChatRoomDto> updateChatRoom(@RequestBody ChatRoom chatRoom) {
    ChatRoom chatRoom1 = chatRoomService.updateChatRoom(chatRoom);
    return ResponseEntity.ok(chatRoom1.toDTO());
  }

  @DeleteMapping("/{id}/{userId}")
  public ResponseEntity<Void> deleteChatRoom(@PathVariable Long id, @PathVariable Long userId) {
    chatRoomService.deleteChatRoom(id, userId);
    return ResponseEntity.ok().build();
  }

}
