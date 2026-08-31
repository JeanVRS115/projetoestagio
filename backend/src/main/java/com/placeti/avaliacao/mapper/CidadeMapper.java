package com.placeti.avaliacao.mapper;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.model.Cidade;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CidadeMapper {

    CidadeDTO toDTO(Cidade cidade);

    Cidade toEntity(CidadeDTO dto);

    void atualizarEntidade(
            CidadeDTO dto,
            @MappingTarget Cidade cidade
    );
}