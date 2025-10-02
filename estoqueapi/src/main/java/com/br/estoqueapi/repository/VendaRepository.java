package com.br.estoqueapi.repository;

import com.br.estoqueapi.model.vendas.Venda;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends MongoRepository<Venda, Long> {

    List<Venda> findByDataVendaBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Venda> findByDataVendaAfter(LocalDateTime inicio);
    List<Venda> findByDataVendaBefore(LocalDateTime fim);
}