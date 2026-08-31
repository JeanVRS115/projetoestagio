package com.placeti.avaliacao.mapper;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.model.Cidade;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-30T08:19:18-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.12 (Oracle Corporation)"
)
@Component
public class CidadeMapperImpl implements CidadeMapper {

    @Override
    public CidadeDTO toDTO(Cidade cidade) {
        if ( cidade == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String uf = null;
        Boolean capital = null;

        id = cidade.getId();
        nome = cidade.getNome();
        uf = cidade.getUf();
        capital = cidade.getCapital();

        CidadeDTO cidadeDTO = new CidadeDTO( id, nome, uf, capital );

        return cidadeDTO;
    }

    @Override
    public Cidade toEntity(CidadeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cidade cidade = new Cidade();

        cidade.setId( dto.id() );
        cidade.setNome( dto.nome() );
        cidade.setUf( dto.uf() );
        cidade.setCapital( dto.capital() );

        return cidade;
    }

    @Override
    public void atualizarEntidade(CidadeDTO dto, Cidade cidade) {
        if ( dto == null ) {
            return;
        }

        cidade.setId( dto.id() );
        cidade.setNome( dto.nome() );
        cidade.setUf( dto.uf() );
        cidade.setCapital( dto.capital() );
    }
}
