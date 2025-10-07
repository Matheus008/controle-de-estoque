package com.br.estoqueapi.dto;

public record ConfigEstoqueRequestDTO(int quantidadeMin, int quantidadeMax, Long produtoId) {
}
