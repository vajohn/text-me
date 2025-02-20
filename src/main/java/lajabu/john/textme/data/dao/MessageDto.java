package lajabu.john.textme.data.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
  private String content;
  private boolean visible;
  private long senderId;
  private String sender;
  private long modifiedById;
  private long chatRoomId;
  private UserDto senderDto;
  private UserDto modifiedBy;
  private ChatRoomDto chatRoom;
}
