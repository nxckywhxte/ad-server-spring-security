package ru.nxckywhxte.ad.server.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nxckywhxte.ad.server.entities.Group;

import java.util.UUID;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group, UUID> {

    Group findGroupById(UUID groupId);

    Group findGroupByName(String groupName);
}
