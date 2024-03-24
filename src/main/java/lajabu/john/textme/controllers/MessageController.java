package lajabu.john.textme.controllers;

import java.util.List;
import lajabu.john.textme.data.dao.MessageDto;
import lajabu.john.textme.data.models.Message;
import lajabu.john.textme.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {
  private final MessageService messageService;

  @PostMapping
  public ResponseEntity<Message> createMessage(MessageDto messageDto) {
    return ResponseEntity.ok(messageService.saveLite(messageDto));
  }

  @PostMapping("/save")
  public ResponseEntity<Message> saveMessage(Message message) {
    return ResponseEntity.ok(messageService.save(message));
  }

  @PutMapping
  public ResponseEntity<Message> updateMessage(Message message) {
    return ResponseEntity.ok(messageService.updateMessage(message));
  }

  @DeleteMapping("/{id}/{userId}")
  public ResponseEntity<Void> deleteMessage(@PathVariable Long id,@PathVariable Long userId) {
    messageService.deleteMessage(id, userId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Message> getMessage(@PathVariable Long id) {
    return ResponseEntity.ok(messageService.getMessageById(id));
  }

  @GetMapping("/{roomId}/{visible}")
  public ResponseEntity<List<Message>> getMessages(@PathVariable Long roomId,@PathVariable boolean visible) {
    return ResponseEntity.ok(messageService.findAllByRoomAndVisibility(roomId, visible));
  }
}
