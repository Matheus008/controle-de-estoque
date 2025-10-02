package com.br.estoqueapi.controller;

import com.br.estoqueapi.dto.ClienteDTO;
import com.br.estoqueapi.exceptions.ClienteNaoEncontradoException;
import com.br.estoqueapi.model.cliente.Cliente;
import com.br.estoqueapi.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Gerenciamento dos clientes.")
public class ClienteController {

    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Operation(summary = "Cadastrar cliente", description = "cadastrar cliente no banco de dados")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @PostMapping("cadastrar")
    public Cliente cadastrar(@RequestBody @Valid ClienteDTO clienteDTO) {
        ClienteDTO dtoFormatado = ClienteDTO.of(
                clienteDTO.nomeCliente(),
                clienteDTO.cpfOuCnpj(),
                clienteDTO.tipoCliente()
        );

        Cliente cliente = new Cliente();
        cliente.setNomeCliente(dtoFormatado.nomeCliente());
        cliente.setTipoCliente(dtoFormatado.tipoCliente());
        cliente.setCpfOuCnpj(dtoFormatado.cpfOuCnpj());

        clienteRepository.save(cliente);
        return cliente;
    }

    @Operation(summary = "Deletar cliente", description = "deletar cliente do banco de dados")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @DeleteMapping("{id}")
    public void deletar(@PathVariable("id") Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));

        clienteRepository.delete(cliente);
    }

    @Operation(summary = "Atualizar cliente", description = "Cliente atualizado no banco de dados")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @PutMapping("{id}")
    public Cliente atualizar(@PathVariable("id") Long id,@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));

        ClienteDTO dtoFormatado = ClienteDTO.of(
                clienteDTO.nomeCliente(),
                clienteDTO.cpfOuCnpj(),
                clienteDTO.tipoCliente()
        );

        cliente.setId(id);
        cliente.setNomeCliente(dtoFormatado.nomeCliente());
        cliente.setTipoCliente(dtoFormatado.tipoCliente());
        cliente.setCpfOuCnpj(dtoFormatado.cpfOuCnpj());

        return clienteRepository.save(cliente);
    }

    @Operation(summary = "Buscar todos os clientes", description = "buscar todos os clientes do banco de dados")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Lista de clientes buscada com sucesso com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @GetMapping
    public Object listarClientes(@PathVariable(value = "id", required = false) Long id,
                                 @PathVariable(value = "cpfOuCnpj", required = false) String cpfOuCnpj,
                                 @PathVariable(value = "nomeCliente", required = false) String nomeCliente) {
        if(id != null)
            return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
        if(cpfOuCnpj != null)
            return clienteRepository.findByCpfOuCnpj(cpfOuCnpj).orElseThrow(() -> new ClienteNaoEncontradoException("cpfOuCnpj",cpfOuCnpj));
        if(nomeCliente != null)
            return clienteRepository.findByNomeCliente(nomeCliente).orElseThrow(() -> new ClienteNaoEncontradoException("nome", nomeCliente));

        return clienteRepository.findAll();
    }
}