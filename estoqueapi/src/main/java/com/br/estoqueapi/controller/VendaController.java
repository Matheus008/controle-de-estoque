package com.br.estoqueapi.controller;

import com.br.estoqueapi.dto.VendasDTO;
import com.br.estoqueapi.model.usuario.Usuario;
import com.br.estoqueapi.model.vendas.Venda;
import com.br.estoqueapi.repository.UsuarioRepository;
import com.br.estoqueapi.service.VendaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private VendaService vendaService;
    private UsuarioRepository usuarioRepository;

    public VendaController(VendaService vendaService, UsuarioRepository usuarioRepository) {
        this.vendaService = vendaService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public Venda registrar(@RequestBody VendasDTO vendasDTO, Principal principal) {
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(principal.getName());

        return vendaService.registrarVenda(vendasDTO.produtoId(),
                vendasDTO.quantidade(), vendasDTO.clienteId(), usuario);
    }
}