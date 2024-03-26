package lajabu.john.textme.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lajabu.john.textme.data.dao.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditExtender implements Serializable {
  @Serial
  private static final long serialVersionUID = 1692723119L;
  @Column
  private String username;
  @Column
  @JsonIgnore
  private String password;
  @Column
  private String email;
  //todo add roles

  public UserDto toDto() {
    return UserDto.builder()
        .id(this.getId())
        .username(this.username)
        .email(this.email)
        .password(this.password)
        .build();
  }
}

