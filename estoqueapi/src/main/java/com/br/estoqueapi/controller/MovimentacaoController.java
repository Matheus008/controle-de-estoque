package com.br.estoqueapi.controller;

import com.br.estoqueapi.dto.LogAcoesRequestDTO;
import com.br.estoqueapi.dto.MovimentacaoDTO;
import com.br.estoqueapi.model.movimentacao.Movimentacao;
import com.br.estoqueapi.model.usuario.Usuario;
import com.br.estoqueapi.repository.MovimentacaoRepository;
import com.br.estoqueapi.repository.UsuarioRepository;
import com.br.estoqueapi.service.LogAcoesService;
import com.br.estoqueapi.service.MovimentacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movimentacao")
@Tag(name = "Movimentar estoque", description = "Gerenciamento das movimentações de produtos dentro de estoque.")
public class MovimentacaoController {

    private final UsuarioRepository usuarioRepository;
    private final MovimentacaoService movimentacaoService;
    private final MovimentacaoRepository movimentacaoRepository;
    private final LogAcoesService logAcoesService;

    public MovimentacaoController(UsuarioRepository usuarioRepository, MovimentacaoService movimentacaoService, MovimentacaoRepository movimentacaoRepository, LogAcoesService logAcoesService) {
        this.movimentacaoService = movimentacaoService;
        this.usuarioRepository = usuarioRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.logAcoesService = logAcoesService;
    }

    @Operation(summary = "Registrar movimentação", description = "Registrar entrada e saida do estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro cadastrado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para realizar essa ação")
    })
    @PostMapping("/registrar")
    public Movimentacao registrar(@RequestBody MovimentacaoDTO movimentacaoDTO, Principal principal) {
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(principal.getName());

        Movimentacao movimentacao = movimentacaoService.registrarMovimentacao(movimentacaoDTO.produtoId(),
                movimentacaoDTO.quantidade(),
                movimentacaoDTO.tipoMovimentacao(),
                movimentacaoDTO.descricao(), usuario);

        logAcoesService.registrarLogAcao(new LogAcoesRequestDTO(
                usuario.getId(),
                "MOVIMENTAR_ESTOQUE_PRODUTO",
                LocalDateTime.now(),
                "Produto",
                movimentacaoDTO.produtoId()
        ));

        return movimentacao;
    }

    @Operation(summary = "Buscar movimentações por Id", description = "Buscar movimentações pelo Id do produto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimentação executada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para realizar essa ação")
    })
    @GetMapping("produto/{produtoId}")
    public List<Movimentacao> listaPorProduto(@PathVariable Long produtoId) {
        List<Movimentacao> movimentacoes = movimentacaoRepository.findByProdutoId(produtoId);
        return movimentacoes;
    }
}