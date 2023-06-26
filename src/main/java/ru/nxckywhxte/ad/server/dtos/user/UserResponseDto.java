package ru.nxckywhxte.ad.server.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ru.nxckywhxte.ad.server.entities.Group;
import ru.nxckywhxte.ad.server.entities.Profile;
import ru.nxckywhxte.ad.server.entities.Role;

import java.util.Collection;
import java.util.UUID;

@Data
@Builder
public class UserResponseDto {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("roles")
    private Collection<Role> roles;
    @JsonProperty("groups")
    private Collection<Group> groups;
    @JsonProperty("profile")
    private Profile profile;
}
