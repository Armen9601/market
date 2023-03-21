package com.market.userservice.service;

import com.market.userservice.dto.UserDto;
import com.market.userservice.entity.User;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto);

    UserDto findByEmail(String email);

    UserDto findById(Long id);

    List<UserDto> findAll();

    void delete(Long id);

    void blockUser(User user, Long id);

    void activateUser(User user, Long id);

    void addBalance(User user, Long userId, double sum);

}
