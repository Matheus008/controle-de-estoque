package com.br.estoqueapi.repository;

import com.br.estoqueapi.model.logAcoes.LogAcoes;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogAcoesRepository extends MongoRepository<LogAcoes, String> {
}
