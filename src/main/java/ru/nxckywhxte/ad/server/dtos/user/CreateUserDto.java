package ru.nxckywhxte.ad.server.dtos.user;

import lombok.Data;

@Data
public class CreateUserDto {
    private String username;
    private String email;
    private String rawPassword;
    private String roleName;
}
