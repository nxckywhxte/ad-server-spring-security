package ru.nxckywhxte.ad.server.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.nxckywhxte.ad.server.entities.Group;
import ru.nxckywhxte.ad.server.repositories.GroupRepository;
import ru.nxckywhxte.ad.server.services.GroupService;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Override
    public Group findGroupById(String groupId) {
        Group existGroup = groupRepository.findGroupById(UUID.fromString(groupId));
        if (Objects.isNull(existGroup)) {
            throw new ResponseStatusException(NOT_FOUND, "Группа с такими данными не найдена!");
        }
        return existGroup;
    }

    @Override
    public Group findGroupByName(String groupName) {
        Group existGroup = groupRepository.findGroupByName(groupName);
        if (Objects.isNull(existGroup)) {
            throw new ResponseStatusException(NOT_FOUND, "Группа с такими данными не найдена!");
        }
        return existGroup;
    }
}
