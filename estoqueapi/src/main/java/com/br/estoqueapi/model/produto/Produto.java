package com.br.estoqueapi.model.produto;

import com.br.estoqueapi.model.fornecedor.Fornecedor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produtos_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    @Setter
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "valorTotal")
    private Double valorTotal = 0.0;

    @Column(name = "quantidade")
    private int quantidade = 0;

    @JoinColumn(name = "fornecedor_id")
    @ManyToOne
    private Fornecedor fornecedor;

}