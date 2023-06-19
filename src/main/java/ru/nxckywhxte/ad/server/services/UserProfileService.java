package ru.nxckywhxte.ad.server.services;

import ru.nxckywhxte.ad.server.dtos.profile.CreateUserProfileDto;
import ru.nxckywhxte.ad.server.entities.Profile;

import java.util.UUID;

public interface UserProfileService {
    Profile createNewProfile(UUID userId, CreateUserProfileDto createUserProfileDto);
}
