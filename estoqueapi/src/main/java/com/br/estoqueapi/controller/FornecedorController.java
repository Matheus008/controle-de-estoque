package com.br.estoqueapi.controller;

import com.br.estoqueapi.dto.FornecedorDTO;
import com.br.estoqueapi.dto.LogAcoesRequestDTO;
import com.br.estoqueapi.exceptions.FornecedorNaoEncontradoException;
import com.br.estoqueapi.model.fornecedor.Fornecedor;
import com.br.estoqueapi.model.usuario.Usuario;
import com.br.estoqueapi.repository.FornecedorRepository;
import com.br.estoqueapi.repository.UsuarioRepository;
import com.br.estoqueapi.service.LogAcoesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/fornecedor")
@Tag(name = "Fornecedor", description = "Gerenciamento dos fornecedores.")
public class FornecedorController {

    private final FornecedorRepository fornecedorRepository;
    private final LogAcoesService logAcoesService;
    private final UsuarioRepository usuarioRepository;

    public FornecedorController(FornecedorRepository fornecedorRepository, LogAcoesService logAcoesService, UsuarioRepository usuarioRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.logAcoesService = logAcoesService;
        this.usuarioRepository = usuarioRepository;
    }

    @Operation(summary = "Cadastrar fornecedor", description = "cadastrar fornecedor no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor cadastrado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @PostMapping
    public Fornecedor cadastrar(@RequestBody FornecedorDTO fornecedorDTO, Principal principal) {
        FornecedorDTO dtoFormatado = FornecedorDTO.of(fornecedorDTO.nomeFornecedor(), fornecedorDTO.cpfOuCnpj(), fornecedorDTO.tipoFornecedor());
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(principal.getName());

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNomeFornecedor(dtoFormatado.nomeFornecedor());
        fornecedor.setCpfOuCnpj(dtoFormatado.cpfOuCnpj());
        fornecedor.setTipoFornecedor(dtoFormatado.tipoFornecedor());

        fornecedorRepository.save(fornecedor);

        logAcoesService.registrarLogAcao(new LogAcoesRequestDTO(
                usuario.getId(),
                "REGISTRAR_FORNECEDOR",
                LocalDateTime.now(),
                "Fornecedor",
                fornecedor.getId()
        ));

        return fornecedor;
    }

    @Operation(summary = "Deletar fornecedor", description = "deletar fornecedor no banco de dados")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Fornecedor deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @DeleteMapping("{id}")
    public void deletar(@PathVariable("id") Long id, Principal principal) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow(() -> new FornecedorNaoEncontradoException(id));
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(principal.getName());

        fornecedorRepository.delete(fornecedor);

        logAcoesService.registrarLogAcao(new LogAcoesRequestDTO(
                usuario.getId(),
                "DELETAR_FORNECEDOR",
                LocalDateTime.now(),
                "Fornecedor",
                fornecedor.getId()
        ));
    }

    @Operation(summary = "Atualizar fornecedor", description = "atualizar fornecedor no banco de dados")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @PutMapping("{id}")
    public Fornecedor atualizar(@PathVariable("id") Long id, @RequestBody FornecedorDTO fornecedorDTO, Principal principal) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow(() -> new FornecedorNaoEncontradoException(id));
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(principal.getName());

        FornecedorDTO dtoFormatado = FornecedorDTO.of(fornecedorDTO.nomeFornecedor(), fornecedorDTO.cpfOuCnpj(), fornecedorDTO.tipoFornecedor());

        fornecedor.setId(id);
        fornecedor.setNomeFornecedor(dtoFormatado.nomeFornecedor());
        fornecedor.setCpfOuCnpj(dtoFormatado.cpfOuCnpj());
        fornecedor.setTipoFornecedor(dtoFormatado.tipoFornecedor());

        fornecedorRepository.save(fornecedor);

        logAcoesService.registrarLogAcao((new LogAcoesRequestDTO(
                usuario.getId(),
                "ALTERAR_FORNECEDOR",
                LocalDateTime.now(),
                "Fornecedor",
                fornecedor.getId()
        )));

        return fornecedor;
    }

    @Operation(summary = "Buscar todos os fornecedores", description = "buscar todos os fornecedores no banco de dados")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Fornecedores buscado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não tem permissão para executar essa ação")
    })
    @GetMapping("/buscar")
    public Object buscar(@RequestParam(name = "id", required = false) Long id,
                                        @RequestParam(name = "nomeFornecedor", required = false) String nomeFornecedor,
                                        @RequestParam(name = "cpfOuCnpj", required = false) String cpfOuCnpj) {

        if(id != null)
            return fornecedorRepository.findById(id).orElseThrow(() -> new FornecedorNaoEncontradoException(id));
        if(nomeFornecedor != null)
            return fornecedorRepository.findByNomeFornecedor(nomeFornecedor);
        if(cpfOuCnpj != null)
            return fornecedorRepository.findByCpfOuCnpj(cpfOuCnpj).orElseThrow(() -> new FornecedorNaoEncontradoException("cpf ou cnpj", cpfOuCnpj));

        return fornecedorRepository.findAll();
    }
}