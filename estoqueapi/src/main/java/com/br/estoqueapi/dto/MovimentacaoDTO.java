package com.br.estoqueapi.dto;

import com.br.estoqueapi.model.movimentacao.TipoMovimentacao;

public record MovimentacaoDTO(int quantidade, String descricao, Long produtoId, TipoMovimentacao tipoMovimentacao) {
}