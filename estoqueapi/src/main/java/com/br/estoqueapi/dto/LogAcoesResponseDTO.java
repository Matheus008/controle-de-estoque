package com.br.estoqueapi.dto;

import com.br.estoqueapi.model.usuario.Usuario;

import java.time.LocalDateTime;

public record LogAcoesResponseDTO(Usuario usuario, String acao, LocalDateTime dataHora, String recurso, String recursoId) {
}
