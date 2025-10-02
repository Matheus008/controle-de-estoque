package com.br.estoqueapi.dto;

import com.br.estoqueapi.model.movimentacao.TipoMovimentacao;

import java.time.LocalDateTime;

public record FiltroRelatorioDTO(
        LocalDateTime inicio,
        LocalDateTime fim,
        Long usuarioId,
        Long produtoId,
        Long clienteId,
        TipoMovimentacao tipoMovimentacao
) {}