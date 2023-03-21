package com.market.authservice.controller;

import com.market.authservice.model.LoginRequest;
import com.market.authservice.response.UserAuthResponse;
import com.market.authservice.service.UserService;
import com.market.userservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    /**
     * @param userDto
     * @return ResponseEntity<UserResponse> this controller accept userRequest object type and save in
     * DB
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> registration(@RequestBody UserDto userDto) {
        userService.registration(userDto);
        return ResponseEntity.ok().build();
    }

    /**
     * @param request
     * @return ResponseEntity<UserAuthResponse> This controller for login write your correct login and
     * password
     */
    @PostMapping("/sign-in")
    public ResponseEntity<UserAuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.auth(request));
    }


    /**
     * @param refreshToken
     * @return ResponseEntity<String> This controller need as for refresh token verification
     */
    @PutMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody String refreshToken) {
        return ResponseEntity.ok(userService.refreshToken(refreshToken));
    }
}
