package com.br.estoqueapi.repository;

import com.br.estoqueapi.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Optional<Cliente>> findByNomeCliente(String nomeCliente);

    Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

}