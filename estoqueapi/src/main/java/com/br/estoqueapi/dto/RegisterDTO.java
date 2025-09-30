package com.br.estoqueapi.dto;

import com.br.estoqueapi.model.usuario.NivelDeUsuario;

public record RegisterDTO(String email, String senha, NivelDeUsuario nivelDeUsuario, String nomeUsuario) {
}
