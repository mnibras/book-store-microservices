package com.bookstore.order.domain;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getLoginUserName() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

//        String username = jwt.getClaimAsString("preferred_username");
//        String email = jwt.getClaimAsString("email");
//        String name = jwt.getClaimAsString("name");
//        String token = jwt.getTokenValue();
//        Collection<GrantedAuthority> authorities = authentication.getAuthorities();

        return jwt.getClaimAsString("preferred_username");
    }
}
