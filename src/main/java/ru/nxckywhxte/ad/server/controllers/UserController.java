package ru.nxckywhxte.ad.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.nxckywhxte.ad.server.dtos.profile.CreateUserProfileDto;
import ru.nxckywhxte.ad.server.dtos.profile.UserProfileResponseDto;
import ru.nxckywhxte.ad.server.entities.Profile;
import ru.nxckywhxte.ad.server.entities.User;
import ru.nxckywhxte.ad.server.services.impl.StorageServiceImpl;
import ru.nxckywhxte.ad.server.services.impl.UserProfileServiceImpl;
import ru.nxckywhxte.ad.server.services.impl.UserServiceImpl;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final StorageServiceImpl storageService;
    private final UserProfileServiceImpl userProfileService;

    @PostMapping(value = "/profile/{userId}")
    public UserProfileResponseDto updateUserProfile(@RequestPart("user-info") CreateUserProfileDto createUserProfileDto, @RequestPart("file") MultipartFile file, @PathVariable String userId) {
        //ищем пользователя по айди
        User user = userService.findUserById(UUID.fromString(userId));
        // проверяем у пользователя айди профиля на налл
        if (Objects.nonNull(user.getProfile())) {
            throw new ResponseStatusException(CONFLICT, "Невозможно создать профиль для пользователя");
        }
        storageService.initIconsPath();
        String avatarUri = storageService.saveIcon(file, user.getId());

        Profile newProfile = userProfileService.createNewProfile(UUID.fromString(userId), createUserProfileDto, avatarUri);
        // если профиль равно налл то создаеем новый профиль если не налл выкидываем ошибку
        return UserProfileResponseDto.builder()
                .id(newProfile.getId())
                .lastName(newProfile.getLastName())
                .firstName(newProfile.getFirstName())
                .patronymic(newProfile.getPatronymic())
                .avatarUrl(newProfile.getAvatarUrl())
                .gender(newProfile.getGender())
                .build();
    }

    @GetMapping(value = "/get-icon/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource getImage(@PathVariable String id) {
        User user = userService.findUserById(UUID.fromString(id));
        return storageService.loadIcon(user.getProfile().getAvatarUrl());
    }
}
