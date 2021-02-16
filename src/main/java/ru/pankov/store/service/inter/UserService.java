package ru.pankov.store.service.inter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.pankov.store.dto.UserDTO;
import ru.pankov.store.entity.Role;
import ru.pankov.store.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findByUsername(String username);

    Optional<UserDTO> findUserDTOByUsername(String username);

    Optional<User> findDuplicate(String username, String email, String phone);

    Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles);

    void save(UserDTO userDTO);
}
