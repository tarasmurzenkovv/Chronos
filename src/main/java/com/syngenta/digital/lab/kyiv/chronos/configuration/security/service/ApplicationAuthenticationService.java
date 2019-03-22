package com.syngenta.digital.lab.kyiv.chronos.configuration.security.service;

import com.syngenta.digital.lab.kyiv.chronos.configuration.security.UserPrincipal;
import com.syngenta.digital.lab.kyiv.chronos.mappers.UserMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationAuthenticationService {
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    public boolean isAllowed(long userId) {
        return currentAuthenticatedUserId() == userId || UserRoleEnum.ADMIN == currentAuthenticatedUserRole();
    }

    private Long currentAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return userMapper.mapToDto(authentication, principal).getId();
    }

    private UserRoleEnum currentAuthenticatedUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return UserRoleEnum.valueOf(userMapper.mapToDto(authentication, principal).getRole());
    }
}