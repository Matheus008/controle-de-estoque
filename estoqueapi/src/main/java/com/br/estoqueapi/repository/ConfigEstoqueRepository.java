package com.br.estoqueapi.repository;

import com.br.estoqueapi.model.configEstoque.ConfigEstoque;
import com.br.estoqueapi.model.configEstoque.StatusEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConfigEstoqueRepository extends JpaRepository<ConfigEstoque, Long> {

    List<Optional<ConfigEstoque>> findByStatusEstoque(StatusEstoque statusEstoque);
}
