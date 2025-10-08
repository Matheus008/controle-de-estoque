package com.br.estoqueapi.controller;

import com.br.estoqueapi.dto.ConfigEstoqueRequestDTO;
import com.br.estoqueapi.dto.LogAcoesRequestDTO;
import com.br.estoqueapi.dto.SituacaoGeralEstoqueDTO;
import com.br.estoqueapi.model.configEstoque.ConfigEstoque;
import com.br.estoqueapi.model.configEstoque.StatusEstoque;
import com.br.estoqueapi.model.usuario.Usuario;
import com.br.estoqueapi.repository.UsuarioRepository;
import com.br.estoqueapi.service.ConfigEstoqueService;
import com.br.estoqueapi.service.LogAcoesService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/configEstoque")
public class ConfigEstoqueController {

    private final ConfigEstoqueService configEstoqueService;
    private final LogAcoesService logAcoesService;
    private final UsuarioRepository usuarioRepository;

    public ConfigEstoqueController(ConfigEstoqueService configEstoqueService, LogAcoesService logAcoesService, UsuarioRepository usuarioRepository) {
        this.configEstoqueService = configEstoqueService;
        this.logAcoesService = logAcoesService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/registrar")
    public ConfigEstoque registrarConfigEstoque(@RequestBody ConfigEstoqueRequestDTO configEstoqueRequestDTO, Principal principal) {
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(principal.getName());

        logAcoesService.registrarLogAcao(new LogAcoesRequestDTO(
                usuario.getId(),
                "CONFIGURAR_ESTOQUE",
                LocalDateTime.now(),
                "Produto",
                configEstoqueRequestDTO.produtoId()
        ));

        return configEstoqueService.registrarConfigEstoque(configEstoqueRequestDTO.quantidadeMin(),
                configEstoqueRequestDTO.quantidadeMax(),
                configEstoqueRequestDTO.produtoId());
    }

    @PutMapping("/atualizar")
    public ConfigEstoque atualizarConfigEstoque(@RequestBody ConfigEstoqueRequestDTO configEstoqueRequestDTO, Principal principal) {
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(principal.getName());

        logAcoesService.registrarLogAcao(new LogAcoesRequestDTO(
                usuario.getId(),
                "ATUALIZAR_CONFIGURAÇÃO_ESTOQUE",
                LocalDateTime.now(),
                "Produto",
                configEstoqueRequestDTO.produtoId()
        ));

        return configEstoqueService.registrarConfigEstoque(configEstoqueRequestDTO.quantidadeMin(),
                configEstoqueRequestDTO.quantidadeMax(),
                configEstoqueRequestDTO.produtoId());
    }

    @GetMapping("/inventario")
    public List<SituacaoGeralEstoqueDTO> inventario() {
        return configEstoqueService.listarSituacaoDoEstoque();
    }

    @GetMapping("/alertas")
    public List<String> alertas() {
        return configEstoqueService.alertas();
    }

    @GetMapping("/buscaStatus")
    public List<Optional<ConfigEstoque>> buscaStatus(@RequestParam StatusEstoque statusEstoque) {
        return configEstoqueService.buscarPorStatus(statusEstoque);
    }
}
