package com.placeti.avaliacao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Comercio")
public class Comercio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(
            name = "NOME_COMERCIO",
            length = 100,
            nullable = false
    )
    private String nomeComercio;

    @Column(
            name = "NOME_RESPONSAVEL",
            length = 100,
            nullable = false
    )
    private String nomeResponsavel;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "TIPO_COMERCIO",
            length = 30,
            nullable = false
    )
    private TipoComercio tipoComercio;

    @ManyToOne
    @JoinColumn(name = "CIDADE_ID", nullable = false)
    private Cidade cidade;
}