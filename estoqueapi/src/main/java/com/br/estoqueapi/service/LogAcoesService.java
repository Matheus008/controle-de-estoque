package com.br.estoqueapi.service;

import com.br.estoqueapi.dto.LogAcoesRequestDTO;
import com.br.estoqueapi.model.logAcoes.LogAcoes;
import com.br.estoqueapi.repository.LogAcoesRepository;
import org.springframework.stereotype.Service;

@Service
public class LogAcoesService {

    private final LogAcoesRepository logAcoesRepository;

    public LogAcoesService(LogAcoesRepository logAcoesRepository) {
        this.logAcoesRepository = logAcoesRepository;
    }

    public void registrarLogAcao(LogAcoesRequestDTO logAcoesDTO) {
        LogAcoes acoes = new LogAcoes(logAcoesDTO.usarioId(),
                logAcoesDTO.acao(),
                logAcoesDTO.dataHora(),
                logAcoesDTO.recurso(),
                logAcoesDTO.recursoId());

        logAcoesRepository.save(acoes);
    }

}
