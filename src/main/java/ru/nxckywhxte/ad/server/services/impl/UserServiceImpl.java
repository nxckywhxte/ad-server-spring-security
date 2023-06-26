package ru.nxckywhxte.ad.server.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.nxckywhxte.ad.server.dtos.user.CreateUserDto;
import ru.nxckywhxte.ad.server.entities.Role;
import ru.nxckywhxte.ad.server.entities.User;
import ru.nxckywhxte.ad.server.repositories.UserRepository;
import ru.nxckywhxte.ad.server.services.UserService;

import java.security.Principal;
import java.util.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "Пользователь с такими данными не найден"
                        )
                );
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User createNewUser(CreateUserDto createUserDto) {
        Role existRole = roleService.getRoleByName(createUserDto.getRoleName());
        User existUser = userRepository.findByUsername(createUserDto.getUsername());
        if (Objects.nonNull(existUser)) {
            throw new ResponseStatusException(CONFLICT, "Пользователь с такими данными уже существует");
        }
        User newUser = User.builder()
                .username(createUserDto.getUsername())
                .email(createUserDto.getEmail())
                .hashedPassword(new BCryptPasswordEncoder().encode(createUserDto.getRawPassword()))
                .roles(Collections.singleton(existRole))
                .build();
        userRepository.saveAndFlush(newUser);

        return newUser;
    }

    @Override
    public User findUserById(UUID id) {
        User existUser = userRepository.findUserById(id);
        if (Objects.isNull(existUser)) {
            throw new ResponseStatusException(NOT_FOUND, "Пользователь с такими данными не найден");
        }
        return existUser;
    }

    @Override
    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public Collection<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public Collection<User> findAllUsersByRole() {
        return null;
    }

    @Override
    public Collection<User> findAllUsersByRole(Collection<Role> roles) {
        return null;
    }

    @Override
    public User getMe(Principal principal) {
        String username = principal.getName();
        return findByUsername(username).orElseThrow();
    }


}
