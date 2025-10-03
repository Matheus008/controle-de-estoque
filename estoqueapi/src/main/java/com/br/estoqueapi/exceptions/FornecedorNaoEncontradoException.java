package com.br.estoqueapi.exceptions;

public class FornecedorNaoEncontradoException extends RuntimeException{
    public FornecedorNaoEncontradoException(Long id) {
        super("Fornecedor com o id: " + id + " não encontrado.");
    }
    public FornecedorNaoEncontradoException(String campo, String valor) {
        super("Fornecedor com o " + campo + ": " + valor + " não encontrado.");
    }
}
