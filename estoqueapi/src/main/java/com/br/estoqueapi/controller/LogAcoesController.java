package com.br.estoqueapi.controller;

import com.br.estoqueapi.model.logAcoes.LogAcoes;
import com.br.estoqueapi.repository.LogAcoesRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/log")
public class LogAcoesController {
    private final LogAcoesRepository logAcoesRepository;

    public LogAcoesController(LogAcoesRepository logAcoesRepository) {
        this.logAcoesRepository = logAcoesRepository;
    }

    @GetMapping
    public List<LogAcoes> buscarLogs() {
        return logAcoesRepository.findAll();
    }
}
