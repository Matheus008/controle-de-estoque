package com.br.estoqueapi.repository;

import com.br.estoqueapi.model.movimentacao.Movimentacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimentacaoRepository extends MongoRepository<Movimentacao, Long> {

    List<Movimentacao> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Movimentacao> findByDataHoraAfter(LocalDateTime inicio);
    List<Movimentacao> findByDataHoraBefore(LocalDateTime fim);
    List<Movimentacao> findByProdutoId(Long produtoId);

}