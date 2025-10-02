package com.br.estoqueapi.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException{
    public ClienteNaoEncontradoException(Long id) {
        super("Cliente com o id: " + id + " não encontrado");
    }

    public ClienteNaoEncontradoException(String campo,String texto) {
        super("Cliente com o "+ texto + ": " + texto + " não encontrado");
    }

}
