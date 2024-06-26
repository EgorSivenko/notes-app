package org.example.todolist.repository;

import org.example.todolist.entity.Role;
import org.example.todolist.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleName name);
}
