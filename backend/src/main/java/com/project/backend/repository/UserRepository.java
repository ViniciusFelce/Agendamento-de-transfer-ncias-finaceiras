package com.project.backend.repository;

import com.project.backend.payload.SignUpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SignUpRequest, Long> {
    Optional<SignUpRequest> findByClienteId(Long clienteId);
    Optional<SignUpRequest> findByEmail(String email);
    Boolean existsByEmail(String email);
}
