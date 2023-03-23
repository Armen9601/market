package com.market.userservice.controller;

import com.market.userservice.dto.UserDto;
import com.market.userservice.entity.User;
import com.market.userservice.security.CurrentUser;
import com.market.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/")
    public UserDto save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

   @PutMapping(value = "/")
    public UserDto update(@RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/email/{email}")
    public UserDto findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PostMapping("/{id}/block")
    public void blockUser(@PathVariable Long id, @AuthenticationPrincipal CurrentUser currentUser) {
        userService.blockUser(currentUser.getUser(), id);
    }

    @PostMapping("/{id}/activate")
    public void activateUser(@PathVariable Long id, @AuthenticationPrincipal CurrentUser currentUser) {
        userService.activateUser(currentUser.getUser(), id);
    }

    @PostMapping("/{userId}/balance")
    public void addBalance(@PathVariable Long userId, @RequestParam double sum, @AuthenticationPrincipal CurrentUser currentUser) {
        userService.addBalance(currentUser.getUser(), userId, sum);
    }
}
