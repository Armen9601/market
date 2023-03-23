package com.market.userservice.service.impl;

import com.market.userservice.dto.UserDto;
import com.market.userservice.entity.AccountStatus;
import com.market.userservice.entity.User;
import com.market.userservice.entity.UserRole;
import com.market.userservice.exception.EmailRepeatingException;
import com.market.userservice.mapper.UserMapper;
import com.market.userservice.repo.UserRepository;
import com.market.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto save(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EmailRepeatingException();
        }
        User saved = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(saved);
    }

    @Override
    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void blockUser(User currentUser, Long id) {
        UserDto byId = findById(id);
        if (currentUser.getRole().equals(UserRole.ADMIN) && byId.getAccountStatus().equals(AccountStatus.ACTIVE)) {
            byId.setAccountStatus(AccountStatus.BLOCKED);
            User user = userMapper.toEntity(byId);
            userRepository.save(user);
        }
    }

    @Override
    public void activateUser(User currentUser, Long id) {
        UserDto byId = findById(id);
        if (currentUser.getRole().equals(UserRole.ADMIN) && byId.getAccountStatus().equals(AccountStatus.BLOCKED)) {
            byId.setAccountStatus(AccountStatus.ACTIVE);
            User user = userMapper.toEntity(byId);
            userRepository.save(user);
        }
    }

    @Override
    public void addBalance(User currentUser, Long userId, double sum) {
        if (currentUser.getRole().equals(UserRole.ADMIN)) {
            UserDto byId = findById(userId);
            byId.setBalance(byId.getBalance() + sum);
            User user = userMapper.toEntity(byId);
            userRepository.save(user);
        }
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
