package com.br.estoqueapi.repository;

import com.br.estoqueapi.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeCliente(String nomeCliente);

    String findByCpfOuCnpj(String cpfOuCnpj);

}