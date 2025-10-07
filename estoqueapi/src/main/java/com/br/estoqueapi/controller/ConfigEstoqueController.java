package com.br.estoqueapi.controller;

import com.br.estoqueapi.dto.ConfigEstoqueRequestDTO;
import com.br.estoqueapi.dto.SituacaoGeralEstoqueDTO;
import com.br.estoqueapi.model.configEstoque.ConfigEstoque;
import com.br.estoqueapi.model.configEstoque.StatusEstoque;
import com.br.estoqueapi.repository.ProdutoRepository;
import com.br.estoqueapi.service.ConfigEstoqueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/configEstoque")
public class ConfigEstoqueController {

    private final ConfigEstoqueService configEstoqueService;

    public ConfigEstoqueController(ConfigEstoqueService configEstoqueService, ProdutoRepository produtoRepository) {
        this.configEstoqueService = configEstoqueService;
    }

    @PostMapping("/registrar")
    public ConfigEstoque registrarConfigEstoque(@RequestBody ConfigEstoqueRequestDTO configEstoqueRequestDTO) {
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
