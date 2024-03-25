package lajabu.john.textme.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
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
  private String password;
  @Column
  private String email;
  //todo add roles
}

