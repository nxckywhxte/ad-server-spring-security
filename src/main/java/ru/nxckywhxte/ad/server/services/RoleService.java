package ru.nxckywhxte.ad.server.services;

import ru.nxckywhxte.ad.server.dtos.role.CreateRoleDto;
import ru.nxckywhxte.ad.server.dtos.role.RoleResponseDto;
import ru.nxckywhxte.ad.server.entities.Role;

import java.util.Collection;
import java.util.UUID;

//public interface RoleService<T, B>

public interface RoleService {
    RoleResponseDto getOneRoleById(UUID roleId);

    Collection<RoleResponseDto> getAllRoles();

    RoleResponseDto createNewRole(CreateRoleDto createRoleDto);

    RoleResponseDto getOneRoleByName(String roleName);

    Role getRoleByName(String roleName);

    void deleteRoleById(UUID id);

    Collection<Role> findAllRolesByName(String roleName);
}
