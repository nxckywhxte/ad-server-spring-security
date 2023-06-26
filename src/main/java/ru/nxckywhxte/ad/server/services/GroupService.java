package ru.nxckywhxte.ad.server.services;

import ru.nxckywhxte.ad.server.entities.Group;


public interface GroupService {
    Group findGroupById(String groupId);

    Group findGroupByName(String groupName);
}
