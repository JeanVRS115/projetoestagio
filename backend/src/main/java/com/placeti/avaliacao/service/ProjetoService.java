package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.mapper.CidadeMapper;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.repository.CidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjetoService {

	private final CidadeRepository cidadeRepository;
	private final CidadeMapper cidadeMapper;

	public ProjetoService(
			CidadeRepository cidadeRepository,
			CidadeMapper cidadeMapper
	) {
		this.cidadeRepository = cidadeRepository;
		this.cidadeMapper = cidadeMapper;
	}

	@Transactional(readOnly = true)
	public CidadeDTO pesquisarCidade(Long id) {
		Cidade cidade = cidadeRepository.findById(id)
				.orElseThrow(() ->
						new EntityNotFoundException(
								"Cidade não encontrada com o ID: " + id
						)
				);

		return cidadeMapper.toDTO(cidade);
	}

	@Transactional(readOnly = true)
	public List<CidadeDTO> pesquisarCidades() {
		return cidadeRepository.findAll()
				.stream()
				.map(cidadeMapper::toDTO)
				.toList();
	}

	@Transactional
	public void incluirCidade(CidadeDTO dto) {
		Cidade cidade = cidadeMapper.toEntity(dto);

		// Garante que será realizado um novo cadastro
		cidade.setId(null);

		cidadeRepository.save(cidade);
	}

	@Transactional
	public void alterarCidade(CidadeDTO dto) {
		if (dto.id() == null) {
			throw new IllegalArgumentException(
					"O ID da cidade é obrigatório para alteração"
			);
		}

		Cidade cidade = cidadeRepository.findById(dto.id())
				.orElseThrow(() ->
						new EntityNotFoundException(
								"Cidade não encontrada com o ID: " + dto.id()
						)
				);

		cidadeMapper.atualizarEntidade(dto, cidade);
		cidadeRepository.save(cidade);
	}

	@Transactional
	public void excluirCidade(Long idCidade) {
		Cidade cidade = cidadeRepository.findById(idCidade)
				.orElseThrow(() ->
						new EntityNotFoundException(
								"Cidade não encontrada com o ID: " + idCidade
						)
				);

		cidadeRepository.delete(cidade);
	}
}