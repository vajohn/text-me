package lajabu.john.textme.data.projections;

public interface MessageProjection {
  String getContent();
  String getSender();
  SenderUserProjection getSenderUser();
  interface SenderUserProjection {
    Long getId();
    String getUsername();
  }
}
