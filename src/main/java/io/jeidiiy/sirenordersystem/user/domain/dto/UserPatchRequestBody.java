package io.jeidiiy.sirenordersystem.user.domain.dto;

import io.jeidiiy.sirenordersystem.web.validation.Nickname;
import io.jeidiiy.sirenordersystem.web.validation.Realname;
import lombok.*;

@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public final class UserPatchRequestBody {
  @Realname private String realname;
  @Nickname private String nickname;
}
