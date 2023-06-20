package ru.nxckywhxte.ad.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nxckywhxte.ad.server.dtos.user.UserResponseDto;
import ru.nxckywhxte.ad.server.entities.Role;
import ru.nxckywhxte.ad.server.entities.User;
import ru.nxckywhxte.ad.server.services.impl.RoleServiceImpl;
import ru.nxckywhxte.ad.server.services.impl.UserServiceImpl;

import java.util.Collection;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        Collection<User> allUsers = userService.findAllUsers();
        Collection<UserResponseDto> response = allUsers
                .stream()
                .map(
                        user -> UserResponseDto
                                .builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .roles(user.getRoles())
                                .groups(user.getGroups())
                                .build()
                ).toList();
        return new ResponseEntity<>(response, OK);
    }

    @GetMapping("/teachers")
    public ResponseEntity<?> getAllTeachers(@RequestBody String roleName) {
        return getResponseEntity(roleName);
    }


    @GetMapping("/students")
    public ResponseEntity<?> getAllStudent(@RequestBody String roleName) {
        return getResponseEntity(roleName);
    }

    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmins(@RequestBody String roleName) {
        return getResponseEntity(roleName);
    }

    private ResponseEntity<?> getResponseEntity(@RequestBody String roleName) {
        Collection<Role> existRoles = roleService.findAllRolesByName(roleName);
        Collection<User> allUsers = userService.findAllUsersByRole(existRoles);
        Collection<UserResponseDto> response = allUsers
                .stream()
                .map(
                        user -> UserResponseDto
                                .builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .roles(user.getRoles())
                                .groups(user.getGroups())
                                .build()
                ).toList();
        return new ResponseEntity<>(response, OK);
    }
}
