package ru.pankov.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pankov.store.dao.UserRepository;
import ru.pankov.store.dto.UserDTO;
import ru.pankov.store.entity.Cart;
import ru.pankov.store.entity.Role;
import ru.pankov.store.entity.User;
import ru.pankov.store.err.ResourceNotFoundException;
import ru.pankov.store.service.inter.RoleService;
import ru.pankov.store.service.inter.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private Role customerRole;

    @PostConstruct
    public void init() {
        customerRole = roleService.findByName("ROLE_CUSTOMER").orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserDTO> findUserDTOByUsername(String username) {
        return userRepository.findByUsername(username).map(UserDTO::new);
    }

    @Override
    public Optional<User> findDuplicate(String username, String email, String phone) {
        return userRepository.findByUsernameOrEmailOrPhone(username, email, phone);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userRepository.save(new User(userDTO));
        user.setCart(new Cart());
        user.setRoles(new ArrayList<>(Arrays.asList(customerRole)));
    }

}
