package ru.nxckywhxte.ad.server.dtos.user;

import lombok.Data;
import ru.nxckywhxte.ad.server.entities.Group;

import java.util.Collection;

@Data
public class CreateUserDto {
    private String username;
    private String email;
    private String rawPassword;
    private String roleName;
    private Collection<Group> groups;
}
