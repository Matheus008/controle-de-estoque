package com.br.estoqueapi.model.fornecedor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fornecedor_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_fornecedor")
    private String nomeFornecedor;

    @Column(name = "cpfOuCnpj")
    private String cpfOuCnpj;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_fornecedor")
    private TipoFornecedor tipoFornecedor;
}