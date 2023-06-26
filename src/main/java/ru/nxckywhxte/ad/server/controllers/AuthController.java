package ru.nxckywhxte.ad.server.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthUserDto;
import ru.nxckywhxte.ad.server.dtos.profile.CreateUserProfileDto;
import ru.nxckywhxte.ad.server.dtos.user.CreateUserDto;
import ru.nxckywhxte.ad.server.services.impl.AuthServiceImpl;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestPart("avatarFile") MultipartFile avatarFile,
            @RequestPart("createUser") CreateUserDto createUserDto,
            @RequestPart("createProfile") CreateUserProfileDto createUserProfileDto
    ) {
        return this.authService.register(createUserDto, createUserProfileDto, avatarFile);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginAuthUserDto loginAuthUserDto) {
        return this.authService.login(loginAuthUserDto);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

}
