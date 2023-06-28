package ru.nxckywhxte.ad.server.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.nxckywhxte.ad.server.dtos.user.CreateUserDto;
import ru.nxckywhxte.ad.server.entities.Role;
import ru.nxckywhxte.ad.server.entities.User;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);

    User createNewUser(CreateUserDto createUserDto);

    User findUserById(UUID id);

    void save(User user);

    Collection<User> findAllUsers();

    Collection<User> findAllUsersByRole();

    Collection<User> findAllUsersByRole(Collection<Role> roles);

    User getMe(Principal principal);

    void deleteUser(String userId);
}
