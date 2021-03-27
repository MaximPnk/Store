package ru.pankov.store;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pankov.store.entity.User;
import ru.pankov.store.service.inter.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void findByUsername() {
        Optional<User> user = userService.findByUsername("admin");
        assertTrue(user.isPresent());
        assertEquals("admin", user.get().getUsername());

        Optional<User> user2 = userService.findByUsername("adminaaa");
        assertFalse(user2.isPresent());
    }
}