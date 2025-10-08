package com.br.estoqueapi.controller;

import com.br.estoqueapi.dto.ClienteDTO;
import com.br.estoqueapi.dto.LogAcoesRequestDTO;
import com.br.estoqueapi.exceptions.ClienteNaoEncontradoException;
import com.br.estoqueapi.model.cliente.Cliente;
import com.br.estoqueapi.model.usuario.Usuario;
import com.br.estoqueapi.repository.ClienteRepository;
import com.br.estoqueapi.repository.UsuarioRepository;
import com.br.estoqueapi.service.LogAcoesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Gerenciamento dos clientes.")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final LogAcoesService logAcoesService;
    private final UsuarioRepository usuarioRepository;

    public ClienteController(ClienteRepository clienteRepository, LogAcoesService logAcoesService, UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.logAcoesService = logAcoesService;
        this.usuarioRepository = usuarioRepository;
    }

    @Operation(summary = "Cadastrar cliente", description = "cadastrar cliente no banco de dados")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @PostMapping("cadastrar")
    public Cliente cadastrar(@RequestBody @Valid ClienteDTO clienteDTO, Principal principal) {

        Usuario usuario = (Usuario) usuarioRepository.findByEmail(principal.getName());

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

        logAcoesService.registrarLogAcao(new LogAcoesRequestDTO(
                usuario.getId(),
                "CADASTRAR_CLIENTE",
                LocalDateTime.now(),
                "Cliente",
                cliente.getId()));

        return cliente;
    }

    @Operation(summary = "Deletar cliente", description = "deletar cliente do banco de dados")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @DeleteMapping("{id}")
    public void deletar(@PathVariable("id") Long id, Principal principal) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(principal.getName());

        clienteRepository.delete(cliente);

        logAcoesService.registrarLogAcao(new LogAcoesRequestDTO(
                usuario.getId(),
                "DELETAR_CLIENTE",
                LocalDateTime.now(),
                "Cliente",
                cliente.getId()
        ));
    }

    @Operation(summary = "Atualizar cliente", description = "Cliente atualizado no banco de dados")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @PutMapping("{id}")
    public Cliente atualizar(@PathVariable("id") Long id,@RequestBody ClienteDTO clienteDTO, Principal principal) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(principal.getName());

        ClienteDTO dtoFormatado = ClienteDTO.of(
                clienteDTO.nomeCliente(),
                clienteDTO.cpfOuCnpj(),
                clienteDTO.tipoCliente()
        );

        cliente.setId(id);
        cliente.setNomeCliente(dtoFormatado.nomeCliente());
        cliente.setTipoCliente(dtoFormatado.tipoCliente());
        cliente.setCpfOuCnpj(dtoFormatado.cpfOuCnpj());

        clienteRepository.save(cliente);

        logAcoesService.registrarLogAcao(new LogAcoesRequestDTO(
                usuario.getId(),
                "ALTERAR_CLIENTE",
                LocalDateTime.now(),
                "Cliente",
                cliente.getId()
        ));

        return cliente;
    }

    @Operation(summary = "Buscar todos os clientes", description = "buscar todos os clientes do banco de dados")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Lista de clientes buscada com sucesso com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @GetMapping("/buscar")
    public Object listarClientes(@RequestParam(value = "id", required = false) Long id,
                                 @RequestParam(value = "cpfOuCnpj", required = false) String cpfOuCnpj,
                                 @RequestParam(value = "nomeCliente", required = false) String nomeCliente) {
        if(id != null)
            return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
        if(cpfOuCnpj != null)
            return clienteRepository.findByCpfOuCnpj(cpfOuCnpj).orElseThrow(() -> new ClienteNaoEncontradoException("cpfOuCnpj",cpfOuCnpj));
        if(nomeCliente != null)
            return clienteRepository.findByNomeCliente(nomeCliente);

        return clienteRepository.findAll();
    }
}