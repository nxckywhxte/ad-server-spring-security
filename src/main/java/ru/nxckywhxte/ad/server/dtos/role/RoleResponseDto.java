package ru.nxckywhxte.ad.server.dtos.role;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RoleResponseDto {
    private UUID id;
    private String name;
}
