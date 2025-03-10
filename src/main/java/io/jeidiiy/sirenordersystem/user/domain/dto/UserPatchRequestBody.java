package io.jeidiiy.sirenordersystem.user.domain.dto;

import io.jeidiiy.sirenordersystem.web.validation.Nickname;
import io.jeidiiy.sirenordersystem.web.validation.Password;
import io.jeidiiy.sirenordersystem.web.validation.Realname;
import io.jeidiiy.sirenordersystem.web.validation.Username;
import lombok.*;

@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public final class UserPatchRequestBody {
  @Username private String username;
  @Password private String password;
  @Realname private String realname;
  @Nickname private String nickname;
}
