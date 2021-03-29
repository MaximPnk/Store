package ru.pankov.store;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.pankov.store.dao.RoleRepository;
import ru.pankov.store.dao.UserRepository;
import ru.pankov.store.dto.UserDTO;
import ru.pankov.store.entity.Role;
import ru.pankov.store.entity.User;
import ru.pankov.store.service.ProductServiceImpl;
import ru.pankov.store.service.RoleServiceImpl;
import ru.pankov.store.service.UserServiceImpl;
import ru.pankov.store.service.inter.RoleService;
import ru.pankov.store.service.inter.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceMockTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleService roleService;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void findUserDTOByUsername() {
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@gmail.com");
        user.setPhone("89031223344");

        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findByUsername("admin");

        //для post construct
        //roleService.findByName("ROLE_CUSTOMER")
        Mockito.doReturn(Optional.of(new Role()))
                .when(roleRepository)
                .findByName("ROLE_CUSTOMER");

        Optional<UserDTO> test = userService.findUserDTOByUsername("admin");
        assertTrue(test.isPresent());
        UserDTO userDTO = test.get();
        assertEquals("admin", userDTO.getUsername());
        assertEquals("admin@gmail.com", userDTO.getEmail());
        assertEquals("89031223344", userDTO.getPhone());
    }
}