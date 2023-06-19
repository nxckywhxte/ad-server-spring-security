package ru.nxckywhxte.ad.server.dtos.profile;

import lombok.Data;
import ru.nxckywhxte.ad.server.entities.enums.Gender;

import java.util.Date;
import java.util.UUID;

@Data
public class CreateUserProfileDto {
    private UUID id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String avatarUrl;
    private String phoneNumber;
    private Gender gender;
    private Date birthday;
}
