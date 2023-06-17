package ru.nxckywhxte.ad.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nxckywhxte.ad.server.dtos.role.RoleResponseDto;
import ru.nxckywhxte.ad.server.services.impl.RoleServiceImpl;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleService;

    @GetMapping("/{roleId}")
    public RoleResponseDto getRoleById(@PathVariable String roleId) {
        return roleService.getOneRoleById(UUID.fromString(roleId));
    }

    @GetMapping()
    public Collection<RoleResponseDto> getAllRoles() {
        return roleService.getAllRoles();
    }
}
