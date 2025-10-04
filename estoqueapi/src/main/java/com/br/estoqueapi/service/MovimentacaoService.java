package com.br.estoqueapi.service;

import com.br.estoqueapi.exceptions.QuantidadeMaiorQueEstoqueException;
import com.br.estoqueapi.model.movimentacao.Movimentacao;
import com.br.estoqueapi.model.movimentacao.TipoMovimentacao;
import com.br.estoqueapi.model.produto.Produto;
import com.br.estoqueapi.model.usuario.Usuario;
import com.br.estoqueapi.repository.MovimentacaoRepository;
import com.br.estoqueapi.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovimentacaoService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoService produtoService;


    public MovimentacaoService(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository, ProdutoService produtoService) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
        this.produtoService = produtoService;
    }

    @Transactional
    public Movimentacao registrarMovimentacao(Long produtoId, int quantidade, TipoMovimentacao tipoMovimentacao, String descricao, Usuario usuario) {
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (tipoMovimentacao == TipoMovimentacao.SAIDA) {
            if (produto.getQuantidade() < quantidade) {
                throw new QuantidadeMaiorQueEstoqueException();
            }
            produto.setQuantidade(produto.getQuantidade() - quantidade);

        } else {
            produto.setQuantidade(produto.getQuantidade() + quantidade);
        }

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProdutoId(produto.getId());
        movimentacao.setQuantidade(quantidade);
        movimentacao.setTipoMovimentacao(tipoMovimentacao);
        movimentacao.setDescricao(descricao);
        movimentacao.setUsuarioId(usuario.getId());


        produto.setValorTotal(produtoService.calcularValorTotal(produto.getQuantidade(), produto.getPreco()));

        produtoRepository.save(produto);
        return movimentacaoRepository.save(movimentacao);

    }

}