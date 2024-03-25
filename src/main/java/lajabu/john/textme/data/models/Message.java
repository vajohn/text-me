package lajabu.john.textme.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "message")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AuditExtender implements Serializable {
  @Serial
  private static final long serialVersionUID = 1116927318969192L;
  @Column
  private String content;
  @Column
  private boolean visible;
  @Column
  private String sender;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_user_id")
  private User senderUser;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modified_by_user_id")
  private User modifiedByUser;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  private ChatRoom chatRoom;
}

