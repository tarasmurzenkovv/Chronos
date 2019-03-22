package com.syngenta.digital.lab.kyiv.chronos.mappers;

import com.syngenta.digital.lab.kyiv.chronos.configuration.security.UserPrincipal;
import com.syngenta.digital.lab.kyiv.chronos.configuration.security.service.JwtTokenProvider;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserRoleEnum;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private static final String DEFAULT_JOB_TITLE = "Developer";
    private final UserRoleRepository userRoleRepository;

    public UserEntity mapToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setUserEmail(userDto.getEmail());
        userEntity.setUserPassword(userDto.getPassword());
        userEntity.setUserRoleEntity(userRoleRepository.findByRole(UserRoleEnum.REGULAR)
                .orElseThrow(() -> new RuntimeException("Cannot locate role in db " + userDto.getRole())));
        userEntity.setJobTitle(StringUtils.isEmpty(userDto.getJobTitle())? DEFAULT_JOB_TITLE : userDto.getJobTitle());
        return userEntity;
    }

    public UserDto mapToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getUserEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setRole(userEntity.getUserRoleEntity().getRole().getRoleAsString());
        userDto.setJobTitle(StringUtils.isEmpty(userEntity.getJobTitle())? DEFAULT_JOB_TITLE : userEntity.getJobTitle());

        return userDto;
    }

    public UserPrincipal mapToUserPrinciple(UserEntity user) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(
                String.format("ROLE_%s", user.getUserRoleEntity().getRole().name())
        ));

        return new UserPrincipal(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserEmail(),
                user.getUserPassword(),
                authorities
        );
    }

    public UserDto mapToDto(Authentication authentication, UserPrincipal principal) {

        UserDto userDto = new UserDto();

        userDto.setEmail(principal.getEmail());
        userDto.setFirstName(principal.getName());
        userDto.setLastName(principal.getUsername());
        userDto.setId(principal.getId());
        userDto.setRole(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().get());
        return userDto;
    }
}
