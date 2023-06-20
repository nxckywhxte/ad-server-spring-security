package ru.nxckywhxte.ad.server.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nxckywhxte.ad.server.entities.Role;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findRoleById(UUID id);

    Optional<Role> findRoleByName(String name);

    Boolean existsRoleByName(String name);

    Collection<Role> findRolesByName(String roleName);

}
