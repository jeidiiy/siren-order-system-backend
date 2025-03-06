package io.jeidiiy.sirenordersystem.user.domain;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 서비스를 이용하는 유저 정보.
 *
 * @author jeidiiy
 */
@Getter
@ToString
@Table(name = "\"users\"")
@Entity
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;

  @Setter
  @Column(length = 30, nullable = false)
  private String username;

  @Setter
  @Column(length = 100, nullable = false)
  private String password;

  @Setter
  @Column(length = 6)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column
  private Role role;

  public static User of(String username, String password, String nickname) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setNickname(nickname);
    user.role = Role.CUSTOMER;
    return user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.role == Role.ADMIN) {
      return List.of(
          new SimpleGrantedAuthority(Role.ADMIN.name()),
          new SimpleGrantedAuthority(Role.CUSTOMER.name()),
          new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.name()),
          new SimpleGrantedAuthority("ROLE_" + Role.CUSTOMER.name()));
    }

    return List.of(
        new SimpleGrantedAuthority(Role.CUSTOMER.name()),
        new SimpleGrantedAuthority("ROLE_" + Role.CUSTOMER.name()));
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;

    if (user.getUserId() != null) {
      return Objects.equals(getUserId(), user.getUserId());
    }

    return Objects.equals(getUsername(), user.getUsername())
        && Objects.equals(getPassword(), user.getPassword())
        && Objects.equals(getNickname(), user.getNickname())
        && getRole() == user.getRole();
  }

  @Override
  public int hashCode() {
    if (getUserId() != null) {
      return Objects.hash(getUserId());
    }

    return Objects.hash(getUsername(), getPassword(), getNickname(), getRole());
  }
}
