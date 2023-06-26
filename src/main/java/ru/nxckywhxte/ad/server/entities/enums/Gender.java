package ru.nxckywhxte.ad.server.entities.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {
    @JsonProperty("Мужской")
    MALE,
    @JsonProperty("Женский")
    FEMALE
}
