package ru.nxckywhxte.ad.server.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nxckywhxte.ad.server.entities.Role;
import ru.nxckywhxte.ad.server.entities.User;

import java.util.UUID;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, UUID> {
}
