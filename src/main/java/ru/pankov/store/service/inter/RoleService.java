package ru.pankov.store.service.inter;

import ru.pankov.store.entity.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findByName(String name);
}
