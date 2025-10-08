package com.br.estoqueapi.controller;

import com.br.estoqueapi.dto.LogAcoesRequestDTO;
import com.br.estoqueapi.dto.VendasDTO;
import com.br.estoqueapi.model.usuario.Usuario;
import com.br.estoqueapi.model.vendas.Venda;
import com.br.estoqueapi.repository.UsuarioRepository;
import com.br.estoqueapi.service.LogAcoesService;
import com.br.estoqueapi.service.VendaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;
    private final UsuarioRepository usuarioRepository;
    private final LogAcoesService logAcoesService;

    public VendaController(VendaService vendaService, UsuarioRepository usuarioRepository, LogAcoesService logAcoesService) {
        this.vendaService = vendaService;
        this.usuarioRepository = usuarioRepository;
        this.logAcoesService = logAcoesService;
    }

    @PostMapping
    public Venda registrar(@RequestBody VendasDTO vendasDTO, Principal principal) {
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(principal.getName());

        Venda venda = vendaService.registrarVenda(vendasDTO.produtoId(),
                vendasDTO.quantidade(), vendasDTO.clienteId(), usuario);

        logAcoesService.registrarLogAcao(new LogAcoesRequestDTO(
                usuario.getId(),
                "REGISTRAR_VENDA",
                LocalDateTime.now(),
                "Produto",
                vendasDTO.produtoId()
        ));

        return venda;
    }
}