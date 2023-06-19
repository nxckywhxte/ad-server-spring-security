package ru.nxckywhxte.ad.server.dtos.profile;

import lombok.Builder;
import lombok.Data;
import ru.nxckywhxte.ad.server.entities.enums.Gender;

import java.util.UUID;

@Data
@Builder
public class UserProfileResponseDto {
    private UUID id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String avatarUrl;
    private String phoneNumber;
    private Gender gender;
}
