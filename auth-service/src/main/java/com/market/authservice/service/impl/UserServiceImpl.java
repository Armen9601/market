package com.market.authservice.service.impl;

import com.market.authservice.exception.*;
import com.market.authservice.mapper.UserMapper;
import com.market.authservice.model.LoginRequest;
import com.market.authservice.response.UserAuthResponse;
import com.market.authservice.security.JwtTokenUtil;
import com.market.authservice.service.UserService;
import com.market.userservice.dto.UserDto;
import com.market.userservice.entity.AccountStatus;
import com.market.userservice.feign.UserClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>.%]).{8,18}$";

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserClient userClient;

    @Value("${token.expiration}")
    private Long expiration;

    @Value("${token.refresh.expiration}")
    private Long refreshExpiration;

    /**
     * @param userDto
     * @throws IncorrectPasswordException this method accept userRequest object type and save in DB
     *                                    check email is Present or not in DB, after that save it
     *                                    check email and password via REGEX
     */
    @Override
    public void registration(UserDto userDto)
            throws IncorrectPasswordException, IncorrectEmailException {
        if (!userDto.getPassword().matches(PASSWORD_REGEX)) {
            log.error(this.getClass().getName(), IncorrectPasswordException.class);
            throw new IncorrectPasswordException();
        }
        if (!userDto.getEmail().matches(EMAIL_REGEX)) {
            log.error(this.getClass().getName(), IncorrectEmailException.class);
            throw new IncorrectEmailException();
        }
        UserDto byEmail = userClient.findByEmail(userDto.getEmail());
        if (byEmail==null){
            saveUser(userDto);
        }else {
            throw new  EmailAlreadyExist();
        }

    }


    /**
     * @param loginRequest
     * @return UserAuthResponse
     */
    @Override
    public UserAuthResponse auth(LoginRequest loginRequest)
            throws EntityNotFoundException, FeignException {
        UserDto byEmail;
        try {
            byEmail = userClient.findByEmail(loginRequest.getEmail());
        } catch (FeignException e) {
            throw new EntityNotFoundException();
        }

        if (byEmail == null) {
            log.error(this.getClass().getName(), EntityNotFoundException.class);
            throw new EntityNotFoundException();
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), byEmail.getPassword())) {
            log.error(this.getClass().getName(), IncorrectPasswordException.class);
            throw new IncorrectPasswordException();
        }
        return new UserAuthResponse(
                jwtTokenUtil.generateToken(byEmail, expiration),
                jwtTokenUtil.generateToken(byEmail, refreshExpiration),
                byEmail);
    }

    /**
     * @param refreshToken
     * @return String token
     * @throws TokenExpiredException if token invalid or expired This method for update token
     */
    @Override
    public String refreshToken(String refreshToken) {
        String token = jwtTokenUtil.refreshToken(refreshToken, expiration);
        if (token == null) {
            log.error(this.getClass().getName(), TokenExpiredException.class);
            throw new TokenExpiredException();
        }
        return token;
    }

    private void saveUser(UserDto userDto) throws FeignException {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setAccountStatus(AccountStatus.ACTIVE);
        userClient.save(userDto);
    }

}
