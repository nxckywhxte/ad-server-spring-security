package ru.nxckywhxte.ad.server.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.nxckywhxte.ad.server.dtos.role.CreateRoleDto;
import ru.nxckywhxte.ad.server.dtos.role.RoleResponseDto;
import ru.nxckywhxte.ad.server.entities.Role;
import ru.nxckywhxte.ad.server.repositories.RoleRepository;
import ru.nxckywhxte.ad.server.services.RoleService;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleResponseDto getOneRoleById(UUID roleId) {
        Role existRole = roleRepository.findRoleById(roleId);
        if (Objects.isNull(existRole)) {
            throw new ResponseStatusException(NOT_FOUND, "Роль с такими данными не найдена!");
        }
        return RoleResponseDto.builder()
                .id(existRole.getId())
                .name(existRole.getName())
                .build();

    }

    @Override
    public Collection<RoleResponseDto> getAllRoles() {
        Collection<Role> allRoles = roleRepository.findAll();
        return allRoles.stream().map(role -> RoleResponseDto.builder()
                .name(role.getName())
                .id(role.getId())
                .build()).toList();
    }

    @Override
    public RoleResponseDto createNewRole(CreateRoleDto createRoleDto) {
        if (roleRepository.existsRoleByName(createRoleDto.getName())) {
            throw new ResponseStatusException(CONFLICT, "Роль с такими данными уже существует");
        }
        Role newRole = Role.builder()
                .name(createRoleDto.getName())
                .build();
        roleRepository.saveAndFlush(newRole);
        return RoleResponseDto.builder()
                .id(newRole.getId())
                .name(newRole.getName())
                .build();
    }

    @Override
    public RoleResponseDto getOneRoleByName(String roleName) {
        Role existRole = roleRepository.findRoleByName(roleName).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Роль с такими данными не найдена!"));
        return RoleResponseDto.builder()
                .id(existRole.getId())
                .name(existRole.getName())
                .build();
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Роль с такими данными не найдена!"));
    }

    @Override
    public void deleteRoleById(UUID id) {
        Role existRole = roleRepository.findRoleById(id);
        if (Objects.isNull(existRole)) {
            throw new ResponseStatusException(NOT_FOUND, "Роли с такими данными не существует");
        }
        this.roleRepository.delete(existRole);
    }

    @Override
    public Collection<Role> findAllRolesByName(String roleName) {
        return roleRepository.findRolesByName(roleName);
    }
}
