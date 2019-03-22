package com.syngenta.digital.lab.kyiv.chronos.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}
