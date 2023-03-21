package com.market.authservice.service;

import com.market.authservice.model.LoginRequest;
import com.market.authservice.response.UserAuthResponse;
import com.market.userservice.dto.UserDto;


public interface UserService {

    void registration(UserDto userDto);

    UserAuthResponse auth(LoginRequest loginRequest);

    String refreshToken(String refreshToken);
}
