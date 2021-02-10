package ru.pankov.store.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pankov.store.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
