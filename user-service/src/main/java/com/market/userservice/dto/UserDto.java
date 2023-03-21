package com.market.userservice.dto;

import com.market.userservice.entity.AccountStatus;
import com.market.userservice.entity.UserRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String userName;
    private String email;
    private String password;
    private double balance;
    private UserRole role;
    private AccountStatus accountStatus;

}
