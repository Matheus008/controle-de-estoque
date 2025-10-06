package com.br.estoqueapi.repository;

import com.br.estoqueapi.model.fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    List<Optional<Fornecedor>> findByNomeFornecedor(String nomeCliente);

    Optional<Fornecedor> findByCpfOuCnpj(String cpfOuCnpj);

}