package com.br.estoqueapi.service;

import com.br.estoqueapi.exceptions.ClienteNaoEncontradoException;
import com.br.estoqueapi.exceptions.ProdutoNaoEncontradoException;
import com.br.estoqueapi.exceptions.QuantidadeMaiorQueEstoqueException;
import com.br.estoqueapi.model.cliente.Cliente;
import com.br.estoqueapi.model.movimentacao.Movimentacao;
import com.br.estoqueapi.model.movimentacao.TipoMovimentacao;
import com.br.estoqueapi.model.produto.Produto;
import com.br.estoqueapi.model.usuario.Usuario;
import com.br.estoqueapi.model.vendas.Venda;
import com.br.estoqueapi.repository.ClienteRepository;
import com.br.estoqueapi.repository.ProdutoRepository;
import com.br.estoqueapi.repository.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendaService {

    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;
    private final MovimentacaoService movimentacaoService;
    private final ClienteRepository clienteRepository;

    public VendaService(ProdutoRepository produtoRepository,
                        VendaRepository vendaRepository,
                        MovimentacaoService movimentacaoService,
                        ClienteRepository clienteRepository) {
        this.produtoRepository = produtoRepository;
        this.vendaRepository = vendaRepository;
        this.movimentacaoService = movimentacaoService;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Venda registrarVenda(Long produtoId, int quantidade, Long clienteId, Usuario usuario) {
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
        Cliente cliente =  clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNaoEncontradoException(clienteId));
        String descricao = "Venda realizada para o cliente: " + cliente.getNomeCliente();
        Movimentacao movimentacao = movimentacaoService.registrarMovimentacao(produtoId, quantidade, TipoMovimentacao.SAIDA, descricao, usuario);

        if(produto.getQuantidade() < quantidade) {
            throw new QuantidadeMaiorQueEstoqueException();
        }


        Venda venda = new Venda();
        venda.setUsuarioId(usuario.getId());
        venda.setQuantidade(quantidade);
        venda.setValorTotalVendido((produto.getPreco() * quantidade) * 1.05); // provisório (valor que vai ser vendido)
        venda.setProdutoId(produto.getId());
        venda.setMovimentacaoId(movimentacao.getId());
        venda.setClienteId(cliente.getId());

        return  vendaRepository.save(venda);

    }
}