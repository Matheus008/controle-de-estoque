package com.br.estoqueapi.controller;

import com.br.estoqueapi.dto.ProdutoDTO;
import com.br.estoqueapi.exceptions.ProdutoNaoEncontradoException;
import com.br.estoqueapi.exceptions.QuantidadeEstoqueNaoZeradoException;
import com.br.estoqueapi.model.fornecedor.Fornecedor;
import com.br.estoqueapi.model.produto.Produto;
import com.br.estoqueapi.repository.FornecedorRepository;
import com.br.estoqueapi.repository.ProdutoRepository;
import com.br.estoqueapi.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produtos")
@Tag(name = "Produto", description = "Gerenciamento dos produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;
    private final FornecedorRepository fornecedorRepository;

    public ProdutoController(ProdutoRepository produtoRepository, ProdutoService produtoService, FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.produtoService = produtoService;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Operation(summary = "Salvar produto", description = "Cadastrar produto no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @PostMapping
    public Produto salvar(@RequestBody ProdutoDTO produtoDTO) {
        return produtoService.registrarProduto(produtoDTO.nome(), produtoDTO.descricao(), produtoDTO.preco(), produtoDTO.idFornecedor());
    }

    @Operation(summary = "Deletar produto", description = "Deletar produto do banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @DeleteMapping("{id}")
    public void deletar(@PathVariable("id") Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        if (produto.getQuantidade() != 0) {
            throw new QuantidadeEstoqueNaoZeradoException("O produto não pode ser excluido, deixe a quantidade do produto zerado!");
        } else {
            produtoRepository.deleteById(id);
        }
    }

    @Operation(summary = "Atualizar produto", description = "Atualizar o produto no bando de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto alterado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @PutMapping("{id}")
    public void atualizar(@PathVariable("id") Long id, @RequestBody ProdutoDTO produtoDTO) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        produto.setId(id);
        produto.setNome(produtoDTO.nome());
        produto.setPreco(produtoDTO.preco());
        produto.setDescricao(produtoDTO.descricao());
        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.idFornecedor()).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado!"));
        produto.setFornecedor(fornecedor);

        produtoRepository.save(produto);
    }

    @Operation(summary = "Buscar produtos", description = "Buscar todos os produtos do banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de produto realizada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @GetMapping("/buscar")
    public Object buscar(@RequestParam(value = "nome", required = false) String nome,
                         @RequestParam(value = "id", required = false) Long id) {
        if(id != null)
            return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        if (nome != null)
            return produtoRepository.findByNome(nome);
        return produtoRepository.findAll();
    }
}
