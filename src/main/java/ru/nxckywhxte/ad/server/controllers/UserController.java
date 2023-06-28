package ru.nxckywhxte.ad.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.nxckywhxte.ad.server.dtos.auth.ResponseAuthDto;
import ru.nxckywhxte.ad.server.dtos.user.UserResponseDto;
import ru.nxckywhxte.ad.server.entities.User;
import ru.nxckywhxte.ad.server.services.impl.StorageServiceImpl;
import ru.nxckywhxte.ad.server.services.impl.UserServiceImpl;

import java.security.Principal;
import java.util.Collection;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final StorageServiceImpl storageService;

    @GetMapping(value = "/get-icon/{id}.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource getImage(@PathVariable String id) {
        User user = userService.findUserById(UUID.fromString(id));
        return storageService.loadIcon(user.getProfile().getAvatarUrl());
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers() {
        Collection<User> allUsers = userService.findAllUsers();
        Collection<UserResponseDto> allUsersResponse = allUsers.stream().map(user -> UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .groups(user.getGroups())
                .profile(user.getProfile())
                .build()).toList();
        return new ResponseEntity<>(allUsersResponse, OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getOneUserById(@PathVariable String userId) {
        User user = userService.findUserById(UUID.fromString(userId));
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .groups(user.getGroups())
                .profile(user.getProfile())
                .build();
        return new ResponseEntity<>(userResponseDto, OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(null);
    }
    @GetMapping("/me")
    public ResponseEntity<?> getMe(Principal principal) {
        try {
            User user = userService.getMe(principal);
            UserResponseDto userResponseDto = UserResponseDto
                    .builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .roles(user.getRoles())
                    .groups(user.getGroups())
                    .profile(user.getProfile())
                    .build();

            return new ResponseEntity<>(userResponseDto, OK);
        } catch(HttpClientErrorException.Unauthorized e) {
            return new ResponseEntity<>(
                    ResponseAuthDto
                            .builder()
                            .status(404)
                            .message("Пользователь не найден")
                            .build(), NOT_FOUND);
        }

    }

}
