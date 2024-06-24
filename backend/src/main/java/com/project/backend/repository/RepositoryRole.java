package com.project.backend.repository;



import com.project.backend.model.Role;
import com.project.backend.security.EPermits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryRole extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(EPermits name);
}
