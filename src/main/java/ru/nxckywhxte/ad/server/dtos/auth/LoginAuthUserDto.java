package ru.nxckywhxte.ad.server.dtos.auth;

import lombok.Data;

@Data
public class LoginAuthUserDto {
    private String username;
    private String rawPassword;
}
