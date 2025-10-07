package com.br.estoqueapi.service;

import com.br.estoqueapi.dto.SituacaoGeralEstoqueDTO;
import com.br.estoqueapi.exceptions.ProdutoNaoEncontradoException;
import com.br.estoqueapi.model.configEstoque.ConfigEstoque;
import com.br.estoqueapi.model.configEstoque.StatusEstoque;
import com.br.estoqueapi.model.produto.Produto;
import com.br.estoqueapi.repository.ConfigEstoqueRepository;
import com.br.estoqueapi.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigEstoqueService {

    private final ConfigEstoqueRepository configEstoqueRepository;
    private final ProdutoRepository produtoRepository;

    public ConfigEstoqueService(ConfigEstoqueRepository configEstoqueRepository, ProdutoRepository produtoRepository) {
        this.configEstoqueRepository = configEstoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ConfigEstoque registrarConfigEstoque(int estoqueMin, int estoqueMax, Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));

        ConfigEstoque configEstoque = new ConfigEstoque();
        configEstoque.setEstoqueMin(estoqueMin);
        configEstoque.setEstoqueMax(estoqueMax);
        configEstoque.setStatusEstoque(verificarStatus(estoqueMin, estoqueMax, produto.getQuantidade()));
        configEstoque.setProduto(produto);

        configEstoqueRepository.save(configEstoque);
        return configEstoque;
    }

    public StatusEstoque verificarStatus(int estqueMin, int estoqueMax, int estoqueProduto) {
        if(estoqueMax <= estoqueProduto) {
            return StatusEstoque.ACIMA;
        } else if(estqueMin >= estoqueProduto) {
            return StatusEstoque.ABAIXO;
        } else {
            return StatusEstoque.NORMAL;
        }
    }

    public List<Optional<ConfigEstoque>> buscarPorStatus(StatusEstoque statusEstoque) {
        return configEstoqueRepository.findByStatusEstoque(statusEstoque);
    }

    public List<SituacaoGeralEstoqueDTO> listarSituacaoDoEstoque() {
        List<ConfigEstoque> listaDeConfiguracao = configEstoqueRepository.findAll();

        return listaDeConfiguracao.stream()
                .map(m -> new SituacaoGeralEstoqueDTO(
                        m.getProduto().getQuantidade(),
                        m.getEstoqueMin(),
                        m.getEstoqueMax(),
                        m.getStatusEstoque(),
                        m.getProduto().getNome()
                ))
                .toList();

    }

    public List<String> alertas() {
        List<ConfigEstoque> lista = configEstoqueRepository.findAll();

        List<String> alertas = new ArrayList<>();

        for (ConfigEstoque configEstoque : lista) {

            if (configEstoque.getProduto().getQuantidade() < configEstoque.getEstoqueMin()) {
                alertas.add("⚠️ Produto " + configEstoque.getProduto().getNome() + " está abaixo do estoque mínimo ("
                        + configEstoque.getProduto().getQuantidade() + "/" + configEstoque.getEstoqueMin() + ")");
            }

            if (configEstoque.getProduto().getQuantidade() > configEstoque.getEstoqueMax()) {
                alertas.add("⚠️ Produto " + configEstoque.getProduto().getNome() + " está acima do estoque máximo ("
                        + configEstoque.getProduto().getQuantidade() + "/" + configEstoque.getEstoqueMax() + ")");
            }
        }

        return alertas;
    }
}
