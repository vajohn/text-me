package lajabu.john.textme.controllers;

import java.util.ArrayList;
import java.util.List;
import lajabu.john.textme.data.dao.MessageDto;
import lajabu.john.textme.data.projections.MessageProjection;
import lajabu.john.textme.services.MessageService;
import lajabu.john.textme.utilities.MessageProjectionImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

class ChatControllerTest {

  @Mock
  private MessageService messageService;

  @Mock
  private SimpMessagingTemplate messagingTemplate;

  @InjectMocks
  private ChatController chatController;

  public ChatControllerTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testJoinChatroom() {
    long roomId = 123L;
    List<MessageProjection> historicalMessages = new ArrayList<>();

    MessageProjectionImpl.SenderUserProjectionImpl senderUser = MessageProjectionImpl.SenderUserProjectionImpl
        .builder()
        .username("senderUsername")
        .build();

    MessageProjectionImpl testMessage = MessageProjectionImpl.builder().content("Sample content")
        .sender("Sample sender").senderUser(senderUser).build();

    historicalMessages.add(testMessage);
    Mockito.when(messageService.findAllByRoomAndVisibility(roomId, true))
        .thenReturn(historicalMessages);

    chatController.joinChatroom(roomId);

    for (MessageProjection message : historicalMessages) {
      Mockito.verify(messagingTemplate).convertAndSend("/topic/chatroom", message);
    }
  }

  @Test
  void testSendMessage() {
    MessageDto inputDto = MessageDto.builder().content("Test message").build();
    MessageDto outputDto = MessageDto.builder().content("Test message").build();
    Mockito.when(messageService.saveLite(inputDto)).thenReturn(outputDto);
    MessageDto result = chatController.sendMessage(inputDto);
    Mockito.verify(messageService).saveLite(inputDto);
    Assertions.assertEquals(outputDto, result);
  }

}

