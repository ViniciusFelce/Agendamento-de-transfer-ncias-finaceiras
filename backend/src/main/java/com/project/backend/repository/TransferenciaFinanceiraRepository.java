package com.project.backend.repository;

import com.project.backend.model.TransferenciaFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaFinanceiraRepository extends JpaRepository<TransferenciaFinanceira, Long> {
}
