package com.placeti.avaliacao.dto;

import com.placeti.avaliacao.model.TipoComercio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComercioDTO(
        Long id,

        @NotBlank(message = "O nome do comércio é obrigatório")
        @Size(max = 100)
        String nomeComercio,

        @NotBlank(message = "O nome do responsável é obrigatório")
        @Size(max = 100)
        String nomeResponsavel,

        @NotNull(message = "O tipo do comércio é obrigatório")
        TipoComercio tipoComercio,

        @NotNull(message = "A cidade é obrigatória")
        Long cidadeId
) {
}