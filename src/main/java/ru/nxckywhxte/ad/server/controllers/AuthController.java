package ru.nxckywhxte.ad.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthResponse;
import ru.nxckywhxte.ad.server.dtos.auth.LoginAuthUserDto;
import ru.nxckywhxte.ad.server.dtos.auth.RegisterAuthResponse;
import ru.nxckywhxte.ad.server.dtos.user.CreateUserDto;
import ru.nxckywhxte.ad.server.services.impl.AuthServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public RegisterAuthResponse register(@RequestBody CreateUserDto createUserDto) {
        return this.authService.register(createUserDto);
    }

    @PostMapping("/login")
    public LoginAuthResponse login(@RequestBody LoginAuthUserDto loginAuthUserDto) {
        return this.authService.login(loginAuthUserDto);
    }
}
