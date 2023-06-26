package ru.nxckywhxte.ad.server.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nxckywhxte.ad.server.entities.Profile;
import ru.nxckywhxte.ad.server.entities.User;

import java.util.UUID;

@Repository
@Transactional
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Profile findProfileByUserIs(User user);
}
