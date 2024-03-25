package lajabu.john.textme.controllers;

import java.util.List;
import lajabu.john.textme.data.dao.MessageDto;
import lajabu.john.textme.data.projections.MessageProjection;
import lajabu.john.textme.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
  private final MessageService messageService;
  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/join/chatroom")
  public void joinChatroom(long roomId) {
    // Fetch historical messages from the database
    List<MessageProjection> historicalMessages = messageService.findAllByRoomAndVisibility(roomId, true);
    for (MessageProjection message : historicalMessages) {
      messagingTemplate.convertAndSend("/topic/chatroom", message);
    }
  }

  @MessageMapping("/send/message")
  @SendTo("/topic/chatroom")
  public MessageDto sendMessage(MessageDto dto) {
    return messageService.saveLite(dto);
  }

}
