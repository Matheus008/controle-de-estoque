package com.br.estoqueapi.exceptions;

public class QuantidadeMaiorQueEstoqueException extends RuntimeException{
    public QuantidadeMaiorQueEstoqueException() {
        super("Quantidade maior que estoque");
    }
}
