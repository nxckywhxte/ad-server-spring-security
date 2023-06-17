package ru.nxckywhxte.ad.server.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.nxckywhxte.ad.server.dtos.user.CreateUserDto;
import ru.nxckywhxte.ad.server.dtos.user.CreateUserResponse;
import ru.nxckywhxte.ad.server.entities.Role;
import ru.nxckywhxte.ad.server.entities.User;
import ru.nxckywhxte.ad.server.repositories.UserRepository;
import ru.nxckywhxte.ad.server.services.UserService;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "Пользователь с такими данными не найден"
                        )
                );
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getHashedPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
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


}
