package com.br.estoqueapi.dto;

import com.br.estoqueapi.model.configEstoque.StatusEstoque;

public record SituacaoGeralEstoqueDTO(int estoqueAtual, int mínimo, int máximo, StatusEstoque status,String nomeProduto) {
}
