package lajabu.john.textme.controllers;

import java.util.List;
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
  public ResponseEntity<ChatRoom> createDefaultChatRoom(@RequestBody ChatRoom chatRoom) {
    return ResponseEntity.ok(chatRoomService.saveChatRoom(chatRoom));
  }

  @GetMapping
  public ResponseEntity<List<ChatRoom>> getAllChatRooms() {
    return ResponseEntity.ok(chatRoomService.getAllChatRooms());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ChatRoom> getChatRoom(@PathVariable Long id) {
    return ResponseEntity.ok(chatRoomService.getChatRoomById(id));
  }

  @PutMapping
  public ResponseEntity<ChatRoom> updateChatRoom(@RequestBody ChatRoom chatRoom) {
    return ResponseEntity.ok(chatRoomService.updateChatRoom(chatRoom));
  }

  @DeleteMapping("/{id}/{userId}")
  public ResponseEntity<Void> deleteChatRoom(@PathVariable Long id, @PathVariable Long userId) {
    chatRoomService.deleteChatRoom(id, userId);
    return ResponseEntity.ok().build();
  }

}
