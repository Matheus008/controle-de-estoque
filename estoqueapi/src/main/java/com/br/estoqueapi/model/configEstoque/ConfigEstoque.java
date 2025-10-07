package com.br.estoqueapi.model.configEstoque;

import com.br.estoqueapi.model.produto.Produto;
import jakarta.persistence.*;

@Entity
@Table(name = "configEstoque_tb")
public class ConfigEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "estoqueMin")
    private int estoqueMin;

    @Column(name = "estoqueMax")
    private int estoqueMax;

    @OneToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusEstoque")
    private StatusEstoque statusEstoque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEstoqueMin() {
        return estoqueMin;
    }

    public void setEstoqueMin(int estoqueMin) {
        this.estoqueMin = estoqueMin;
    }

    public int getEstoqueMax() {
        return estoqueMax;
    }

    public void setEstoqueMax(int estoqueMax) {
        this.estoqueMax = estoqueMax;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public StatusEstoque getStatusEstoque() {
        return statusEstoque;
    }

    public void setStatusEstoque(StatusEstoque statusEstoque) {
        this.statusEstoque = statusEstoque;
    }
}
