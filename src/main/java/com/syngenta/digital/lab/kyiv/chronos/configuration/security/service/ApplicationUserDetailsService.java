package com.syngenta.digital.lab.kyiv.chronos.configuration.security.service;

import com.syngenta.digital.lab.kyiv.chronos.configuration.security.UserPrincipal;
import com.syngenta.digital.lab.kyiv.chronos.mappers.UserMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.UserValidationException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {
    private static final int ERROR_CODE_FOR_NON_EXISTING_EMAIL = 7;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        return userRepository.find(userEmail)
                .map(userMapper::mapToUserPrinciple)
                .orElseThrow(() -> new UserValidationException(ERROR_CODE_FOR_NON_EXISTING_EMAIL,
                        String.format("Cannot find the given user for the provided name '%s'", userEmail)));
    }

    @Transactional(readOnly = true)
    public UserPrincipal findUserPrincipal(long userId) {
        return userRepository
                .findById(userId)
                .map(userMapper::mapToUserPrinciple)
                .orElseThrow(() -> new UserValidationException(ERROR_CODE_FOR_NON_EXISTING_EMAIL,
                        String.format("Cannot find the given user id '%s'", userId)));
    }
}
