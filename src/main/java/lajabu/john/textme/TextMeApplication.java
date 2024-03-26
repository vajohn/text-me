package lajabu.john.textme;

import lajabu.john.textme.data.models.User;
import lajabu.john.textme.services.AuthenticationService;
import lajabu.john.textme.services.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
public class TextMeApplication {
  private final AuthenticationService authenticationService;
  private final ChatRoomService chatRoomService;
  public static void main(String[] args) {
    SpringApplication.run(TextMeApplication.class, args);
  }

  @EventListener
  public void startup(ApplicationStartedEvent e) {
    User user = authenticationService.createAdmin();
    chatRoomService.createDefaultChatRoom(user);
  }
  // TODO: 2024/03/26 add better exception handling for all controllers
}
