package com.syngenta.digital.lab.kyiv.chronos.model.entities;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserRoleEnum;
import com.syngenta.digital.lab.kyiv.chronos.service.UserRoleConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "APPLICATION_USER_ROLE")
@Getter
@Setter
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ROLE")
    @Convert(converter = UserRoleConverter.class)
    private UserRoleEnum role;
}
