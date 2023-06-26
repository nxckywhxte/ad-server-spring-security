package ru.nxckywhxte.ad.server.dtos.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseAuthDto {
    String message;
    Number status;
}
