package lajabu.john.textme.utilities;


import lajabu.john.textme.data.projections.MessageProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageProjectionImpl implements MessageProjection {
  private String content;
  private String sender;
  private SenderUserProjection senderUser;

  @Override
  public String getContent() {
    return content;
  }

  @Override
  public String getSender() {
    return sender;
  }

  @Override
  public SenderUserProjection getSenderUser() {
    return senderUser;
  }

  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class SenderUserProjectionImpl implements SenderUserProjection {
    private Long id;
    private String username;

    @Override
    public Long getId() {
      return id;
    }

    @Override
    public String getUsername() {
      return username;
    }
  }
}

