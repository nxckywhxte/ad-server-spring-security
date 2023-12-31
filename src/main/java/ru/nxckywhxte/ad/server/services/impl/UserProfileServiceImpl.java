package ru.nxckywhxte.ad.server.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.nxckywhxte.ad.server.dtos.profile.CreateUserProfileDto;
import ru.nxckywhxte.ad.server.entities.Profile;
import ru.nxckywhxte.ad.server.entities.User;
import ru.nxckywhxte.ad.server.repositories.ProfileRepository;
import ru.nxckywhxte.ad.server.services.UserProfileService;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final ProfileRepository profileRepository;
    private final UserServiceImpl userService;

    @Override
    public Profile createNewProfile(UUID userId, CreateUserProfileDto createUserProfileDto, String avatarUri) {
        User existUser = userService.findUserById(userId);
        Profile existProfile = profileRepository.findProfileByUserIs(existUser);

        if (Objects.nonNull(existProfile)) {
            throw new ResponseStatusException(CONFLICT, "Профиль для данного пользователя уже создан");
        }

        Profile newProfile = Profile.builder()
                .firstName(createUserProfileDto.getFirstName())
                .lastName(createUserProfileDto.getLastName())
                .patronymic(createUserProfileDto.getPatronymic())
                .birthday(createUserProfileDto.getBirthday())
                .gender(createUserProfileDto.getGender())
                .user(existUser)
                .avatarUrl(avatarUri)
                .build();
        existUser.setProfile(newProfile);
        profileRepository.saveAndFlush(newProfile);
        userService.save(existUser);
        return newProfile;
    }

    @Override
    public void deleteProfile(UUID id) {
        profileRepository.deleteById(id);
    }
}
