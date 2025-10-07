package com.br.estoqueapi.model.logAcoes;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "log_acoes")
public class LogAcoes {

    @Id
    private String id;

    private Long usarioId;

    private String acao;

    private LocalDateTime dataHora;

    private String recurso;

    private String recursoId;

    public LogAcoes(Long usarioId, String acao, LocalDateTime dataHora, String recurso, String recursoId) {
        this.usarioId = usarioId;
        this.acao = acao;
        this.dataHora = dataHora;
        this.recurso = recurso;
        this.recursoId = recursoId;
    }
}
