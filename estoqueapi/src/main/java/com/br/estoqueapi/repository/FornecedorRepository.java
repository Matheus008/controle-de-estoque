package com.br.estoqueapi.repository;

import com.br.estoqueapi.model.fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    List<Fornecedor> findByNomeFornecedor(String nomeCliente);

    String findByCpfOuCnpj(String cpfOuCnpj);

}