package com.br.estoqueapi.service;

import com.br.estoqueapi.dto.FiltroRelatorioDTO;
import com.br.estoqueapi.dto.RelatorioMovimentacaoDTO;
import com.br.estoqueapi.dto.RelatorioVendaDTO;
import com.br.estoqueapi.model.cliente.Cliente;
import com.br.estoqueapi.model.movimentacao.Movimentacao;
import com.br.estoqueapi.model.produto.Produto;
import com.br.estoqueapi.model.usuario.Usuario;
import com.br.estoqueapi.model.vendas.Venda;
import com.br.estoqueapi.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RelatorioService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final VendaRepository vendaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    public RelatorioService(MovimentacaoRepository movimentacaoRepository,
                            VendaRepository vendaRepository,
                            UsuarioRepository usuarioRepository,
                            ProdutoRepository produtoRepository,
                            ClienteRepository clienteRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.vendaRepository = vendaRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<RelatorioMovimentacaoDTO> relatioriDeMovimentacao(FiltroRelatorioDTO filtro) {
        List<Movimentacao> movimentacoes;

        if(filtro.inicio() != null && filtro.fim() != null) {
            movimentacoes = movimentacaoRepository.findByDataHoraBetween(filtro.inicio(), filtro.fim());
        } else if(filtro.inicio() != null){
            movimentacoes = movimentacaoRepository.findByDataHoraAfter(filtro.inicio());
        } else if (filtro.fim() != null) {
            movimentacoes = movimentacaoRepository.findByDataHoraBefore(filtro.fim());
        } else {
            movimentacoes = movimentacaoRepository.findAll();
        }

        return movimentacoes.stream()
                .filter(movimentacao -> filtro.usuarioId() == null || Objects.equals(movimentacao.getUsuarioId(), filtro.usuarioId()))
                .filter(movimentacao -> filtro.produtoId() == null || Objects.equals(movimentacao.getProdutoId(), filtro.produtoId()))
                .filter(movimentacao -> filtro.tipoMovimentacao() == null || Objects.equals(movimentacao.getTipoMovimentacao(), filtro.tipoMovimentacao()))
                .map(movimentacao -> {

                    Usuario usuario = filtro.usuarioId() != null ? usuarioRepository.findById(filtro.usuarioId()).orElse(null) : null;
                    Produto produto = filtro.produtoId() != null ? produtoRepository.findById(filtro.produtoId()).orElse(null) : null;

                    return new RelatorioMovimentacaoDTO(
                            movimentacao.getId(),
                            produto.getNome(),
                            movimentacao.getQuantidade(),
                            movimentacao.getTipoMovimentacao(),
                            movimentacao.getDescricao(),
                            usuario.getNomeUsuario(),
                            movimentacao.getDataHora()
                    );

                })
                .toList();
    }

    public List<RelatorioVendaDTO> relatorioDeVenda(FiltroRelatorioDTO filtro) {
        List<Venda> vendas;

        if(filtro.inicio() != null && filtro.fim() != null) {
            vendas = vendaRepository.findByDataVendaBetween(filtro.inicio(), filtro.fim());
        } else if(filtro.inicio() != null) {
            vendas = vendaRepository.findByDataVendaAfter(filtro.inicio());
        } else if (filtro.fim() != null) {
            vendas = vendaRepository.findByDataVendaBefore(filtro.fim());
        } else {
            vendas = vendaRepository.findAll();
        }

        return vendas.stream()
                .filter(venda -> filtro.produtoId() == null || Objects.equals(venda.getProdutoId(), filtro.produtoId()))
                .filter(venda -> filtro.clienteId() == null || Objects.equals(venda.getClienteId(), filtro.clienteId()))
                .filter(venda -> filtro.usuarioId() == null || Objects.equals(venda.getUsuarioId(), filtro.usuarioId()))
                .map(venda -> {

                    Usuario usuario = filtro.usuarioId() != null ? usuarioRepository.findById(filtro.usuarioId()).orElse(null) : null;
                    Produto produto = filtro.produtoId() != null ? produtoRepository.findById(filtro.produtoId()).orElse(null) : null;
                    Cliente cliente = filtro.clienteId() != null ? clienteRepository.findById(filtro.clienteId()).orElse(null) : null;

                    return new RelatorioVendaDTO(
                            venda.getId(),
                            produto.getNome(),
                            venda.getQuantidade(),
                            venda.getValorTotalVendido(),
                            produto.getPreco(),
                            cliente.getNomeCliente(),
                            usuario.getNomeUsuario(),
                            venda.getDataVenda()
                    );
                        })
                .toList();
    }
}