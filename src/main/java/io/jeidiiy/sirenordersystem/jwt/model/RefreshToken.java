package io.jeidiiy.sirenordersystem.jwt.model;

import io.jeidiiy.sirenordersystem.user.domain.User;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Table(
    name = "refresh_tokens",
    indexes = {@Index(columnList = "token")})
@Entity
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer refreshTokenId;

  @OneToOne
  @JoinColumn(name = "user_id")
  @ToString.Exclude
  private User user;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private Instant expiryDate;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    RefreshToken that = (RefreshToken) o;

    if (that.getRefreshTokenId() != null) {
      return Objects.equals(refreshTokenId, that.getRefreshTokenId());
    }

    return Objects.equals(getUser(), that.getUser())
        && Objects.equals(getToken(), that.getToken())
        && Objects.equals(getExpiryDate(), that.getExpiryDate());
  }

  @Override
  public int hashCode() {
    if (getRefreshTokenId() != null) {
      return Objects.hash(getRefreshTokenId());
    }
    return Objects.hash(getUser(), getToken(), getExpiryDate());
  }
}
