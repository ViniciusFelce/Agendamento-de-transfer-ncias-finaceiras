package com.project.backend.service;

import com.project.backend.model.AgendamentoTransferencia;
import com.project.backend.repository.AgendamentoTransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoTransferenciaService {

    private final AgendamentoTransferenciaRepository agendamentoTransferenciaRepository;

    @Autowired
    public AgendamentoTransferenciaService(AgendamentoTransferenciaRepository agendamentoTransferenciaRepository) {
        this.agendamentoTransferenciaRepository = agendamentoTransferenciaRepository;
    }

    public List<AgendamentoTransferencia> listarAgendamentos() {
        return agendamentoTransferenciaRepository.findAll();
    }

    public AgendamentoTransferencia buscarAgendamentoPorId(Long id) {
        Optional<AgendamentoTransferencia> agendamentoOptional = agendamentoTransferenciaRepository.findById(id);
        if (agendamentoOptional.isEmpty()) {
            throw new IllegalArgumentException("Agendamento de transferência não encontrado.");
        }
        return agendamentoOptional.get();
    }

    public void deletarAgendamento(Long id) {
        agendamentoTransferenciaRepository.deleteById(id);
    }
}
