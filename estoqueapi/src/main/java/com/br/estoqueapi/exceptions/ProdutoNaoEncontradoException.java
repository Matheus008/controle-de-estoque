package com.br.estoqueapi.exceptions;

public class ProdutoNaoEncontradoException extends RuntimeException{
    public ProdutoNaoEncontradoException(Long id) {
        super("Produto com o id: "+ id + " não encontrado.");
    }
    public ProdutoNaoEncontradoException(String campo,String texto) {
        super("Produto com o " + campo + ": "+ texto + " não encontrado.");
    }
}
