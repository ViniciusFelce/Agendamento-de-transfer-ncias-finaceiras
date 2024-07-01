package com.project.backend.controller;

import com.project.backend.dto.ContaBancariaDTO;
import com.project.backend.service.ContaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/contas")
public class ContaBancariaController {

    private final ContaBancariaService contaBancariaService;

    @Autowired
    public ContaBancariaController(ContaBancariaService contaBancariaService) {
        this.contaBancariaService = contaBancariaService;
    }

    @GetMapping
    public ResponseEntity<List<ContaBancariaDTO>> listarContas() {
        List<ContaBancariaDTO> contas = contaBancariaService.listarContas();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaBancariaDTO> buscarContaPorId(@PathVariable Long id) {
        ContaBancariaDTO conta = contaBancariaService.buscarContaPorId(id);
        return ResponseEntity.ok(conta);
    }

    @PostMapping
    public ResponseEntity<ContaBancariaDTO> criarConta(@RequestBody ContaBancariaDTO contaDTO) {
        ContaBancariaDTO novaConta = contaBancariaService.salvarConta(contaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable Long id) {
        contaBancariaService.deletarConta(id);
        return ResponseEntity.noContent().build();
    }
}
