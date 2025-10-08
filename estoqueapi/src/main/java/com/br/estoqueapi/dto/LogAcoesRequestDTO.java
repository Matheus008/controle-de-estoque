package com.br.estoqueapi.dto;

import java.time.LocalDateTime;

public record LogAcoesRequestDTO(Long usarioId, String acao, LocalDateTime dataHora, String recurso, Long recursoId) {
}
