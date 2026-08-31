package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.model.Comercio;
import com.placeti.avaliacao.repository.CidadeRepository;
import com.placeti.avaliacao.repository.ComercioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ComercioService {

    private final ComercioRepository comercioRepository;
    private final CidadeRepository cidadeRepository;

    public ComercioService(
            ComercioRepository comercioRepository,
            CidadeRepository cidadeRepository
    ) {
        this.comercioRepository = comercioRepository;
        this.cidadeRepository = cidadeRepository;
    }

    @Transactional(readOnly = true)
    public List<ComercioDTO> pesquisarComercios() {
        return comercioRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ComercioDTO pesquisarComercio(Long id) {
        Comercio comercio = buscarComercio(id);
        return converterParaDTO(comercio);
    }

    @Transactional
    public ComercioDTO incluirComercio(ComercioDTO dto) {
        Cidade cidade = buscarCidade(dto.cidadeId());

        Comercio comercio = new Comercio();
        comercio.setNomeComercio(dto.nomeComercio());
        comercio.setNomeResponsavel(dto.nomeResponsavel());
        comercio.setTipoComercio(dto.tipoComercio());
        comercio.setCidade(cidade);

        Comercio comercioSalvo =
                comercioRepository.save(comercio);

        return converterParaDTO(comercioSalvo);
    }

    @Transactional
    public ComercioDTO alterarComercio(
            Long id,
            ComercioDTO dto
    ) {
        Comercio comercio = buscarComercio(id);
        Cidade cidade = buscarCidade(dto.cidadeId());

        comercio.setNomeComercio(dto.nomeComercio());
        comercio.setNomeResponsavel(dto.nomeResponsavel());
        comercio.setTipoComercio(dto.tipoComercio());
        comercio.setCidade(cidade);

        Comercio comercioSalvo =
                comercioRepository.save(comercio);

        return converterParaDTO(comercioSalvo);
    }

    @Transactional
    public void excluirComercio(Long id) {
        Comercio comercio = buscarComercio(id);
        comercioRepository.delete(comercio);
    }

    private Comercio buscarComercio(Long id) {
        return comercioRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Comércio não encontrado com o ID: " + id
                        )
                );
    }

    private Cidade buscarCidade(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Cidade não encontrada com o ID: " + cidadeId
                        )
                );
    }

    private ComercioDTO converterParaDTO(Comercio comercio) {
        return new ComercioDTO(
                comercio.getId(),
                comercio.getNomeComercio(),
                comercio.getNomeResponsavel(),
                comercio.getTipoComercio(),
                comercio.getCidade().getId()
        );
    }
}