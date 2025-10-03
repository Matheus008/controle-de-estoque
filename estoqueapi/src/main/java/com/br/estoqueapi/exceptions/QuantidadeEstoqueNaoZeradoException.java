package com.br.estoqueapi.exceptions;

public class QuantidadeEstoqueNaoZeradoException extends RuntimeException {
    public QuantidadeEstoqueNaoZeradoException(String mensagem) {
        super(mensagem);
    }
}
