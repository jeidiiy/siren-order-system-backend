package io.jeidiiy.sirenordersystem.user.domain.dto;

import io.jeidiiy.sirenordersystem.user.domain.Role;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Getter
@ToString
public class AuthenticationUser implements UserDetails {

  private String username;
  private String password;
  private Role role;

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
    AuthenticationUser that = (AuthenticationUser) o;
    return Objects.equals(getUsername(), that.getUsername())
        && Objects.equals(getPassword(), that.getPassword())
        && getRole() == that.getRole();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUsername(), getPassword(), getRole());
  }
}
