package com.market.userservice.util;

import com.market.userservice.entity.User;
import com.market.userservice.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SecurityRegistrar {

    private final JwtTokenParser jwtTokenParser;
    private final ModelMapper modelMapper;

    public void registerUserFromTokenToSecurityContext(String token) {
        Map<String, Object> credentials = jwtTokenParser.parseToken(token);
        if (!credentials.isEmpty()) {
            SecurityContextHolder.clearContext();
            User user = new User();
            CurrentUser currentUser = null;
            if (!credentials.isEmpty()) {
                Object parsedUser = credentials.get("user");
                modelMapper.map(parsedUser, user);
                currentUser = new CurrentUser(user);
            }
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(currentUser,
                    user.getPassword(),
                    AuthorityUtils.createAuthorityList(user.getRole().name()));
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authReq);
        }
    }
}
