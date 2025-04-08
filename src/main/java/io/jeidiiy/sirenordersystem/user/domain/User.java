package io.jeidiiy.sirenordersystem.user.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@ToString
@NoArgsConstructor
@Table(
    name = "\"users\"",
    indexes = {@Index(columnList = "username")})
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE user_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Setter
  @Column(nullable = false)
  private String username;

  @Setter
  @Column(length = 100, nullable = false)
  private String password;

  @Setter
  @Column(length = 6, nullable = false)
  private String realname;

  @Setter
  @Column(length = 6)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column
  private Role role;

  @Column private LocalDateTime createdAt;

  @Column private LocalDateTime updatedAt;

  @Column private LocalDateTime deletedAt;

  @Builder
  public User(String username, String realname, String password, String nickname) {
    this.username = username;
    this.realname = realname;
    this.password = password;
    this.nickname = nickname;
    this.role = Role.CUSTOMER; // 기본값으로 고객으로 저장. 관리자는 별도로 관리
  }

  @PrePersist
  private void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  private void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;

    if (user.getId() != null) {
      return Objects.equals(getId(), user.getId());
    }

    return Objects.equals(getUsername(), user.getUsername())
        && Objects.equals(getPassword(), user.getPassword())
        && Objects.equals(getRealname(), user.getRealname())
        && Objects.equals(getNickname(), user.getNickname())
        && getRole() == user.getRole();
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }
    return Objects.hash(getUsername(), getPassword(), getRealname(), getNickname(), getRole());
  }
}
