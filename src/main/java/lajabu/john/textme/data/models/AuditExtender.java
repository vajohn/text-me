package lajabu.john.textme.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lajabu.john.textme.utilities.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class AuditExtender {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column
  private Long createdAt;
  @Column
  private Long modifiedAt;

  @Transient
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  public LocalDateTime getDateCreated() {
    return LocalDateTime.ofEpochSecond(createdAt, 0, ZoneOffset.UTC);
  }

  @Transient
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  public LocalDateTime getDateModified() {
    return LocalDateTime.ofEpochSecond(modifiedAt, 0, ZoneOffset.UTC);
  }

  @PrePersist
  public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    this.createdAt = now.toEpochSecond(ZoneOffset.UTC);
    this.modifiedAt = now.toEpochSecond(ZoneOffset.UTC);
  }

  @PreUpdate
  public void preUpdate() {
    this.modifiedAt = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
  }
}
