package com.br.estoqueapi.dto;

import com.br.estoqueapi.model.movimentacao.TipoMovimentacao;

import java.time.LocalDateTime;

public record RelatorioMovimentacaoDTO(
        Long id,
        String produtoNome,
        int quantidade,
        TipoMovimentacao tipoMovimentacao,
        String Descricao,
        String usuario,
        LocalDateTime dataHora
) {
}