package com.project.backend.repository;

import com.project.backend.model.AgendamentoTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoTransferenciaRepository extends JpaRepository<AgendamentoTransferencia, Long> {
}
