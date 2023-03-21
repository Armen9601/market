package com.market.authservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;
    private String userName;
    private String email;
    private String password;
    private double balance;
    private UserRole role;

}
