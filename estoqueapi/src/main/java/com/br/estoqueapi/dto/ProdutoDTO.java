package com.br.estoqueapi.dto;

public record ProdutoDTO(String nome, String descricao, Double preco, Long idFornecedor) {
}
