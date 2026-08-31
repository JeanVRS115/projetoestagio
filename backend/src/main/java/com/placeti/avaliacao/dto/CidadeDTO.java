package com.placeti.avaliacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CidadeDTO(
        Long id,

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "A UF é obrigatória")
        @Size(min = 2, max = 2, message = "A UF deve possuir 2 caracteres")
        String uf,

        @NotNull(message = "É necessário informar se a cidade é capital")
        Boolean capital
) {
}