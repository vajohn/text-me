package lajabu.john.textme.data.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lajabu.john.textme.data.dao.ChatRoomDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends AuditExtender implements Serializable {
  @Serial
  private static final long serialVersionUID = 1693855717L;
  @Column
  private String name;
  @Column
  private String description;
  @Column
  private boolean visible;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "created_by_id")
  private User createdBy;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "modified_by_id")
  private User modifiedBy;

  public ChatRoomDto toDTO() {
    return ChatRoomDto.builder()
        .id(this.getId())
        .name(this.name)
        .description(this.description)
        .createdByUserId(this.createdBy != null ? this.createdBy.getId() : null)
        .modifiedByUserId(this.modifiedBy != null ? this.modifiedBy.getId() : null)
        .build();
  }
}

