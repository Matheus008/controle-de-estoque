package com.br.estoqueapi.dto;

import java.time.LocalDateTime;

public record RelatorioVendaDTO(
        Long id,
        String produtoNome,
        int quantidade,
        double valorTotalVendido,
        double valorDoProduto,
        String cliente,
        String usuario,
        LocalDateTime dataHora) {
}