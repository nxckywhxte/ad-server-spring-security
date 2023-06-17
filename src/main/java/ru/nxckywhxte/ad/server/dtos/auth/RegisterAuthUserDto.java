package ru.nxckywhxte.ad.server.dtos.auth;

import lombok.Data;

@Data
public class RegisterAuthUserDto {
    private String email;
    private String username;
    private String rawPassword;
    private String roleName;
}
