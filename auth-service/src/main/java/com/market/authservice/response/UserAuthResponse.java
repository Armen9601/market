package com.market.authservice.response;

import com.market.userservice.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthResponse {

  private String token;
  private String refreshToken;
  private UserDto userDto;
}
