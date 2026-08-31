package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
@CrossOrigin(origins = "http://localhost:4200")
public class CidadeController {

	private final ProjetoService projetoService;

	public CidadeController(ProjetoService projetoService) {
		this.projetoService = projetoService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<CidadeDTO> buscarPeloId(
			@PathVariable Long id
	) {
		CidadeDTO cidade = projetoService.pesquisarCidade(id);
		return ResponseEntity.ok(cidade);
	}

	@GetMapping
	public ResponseEntity<List<CidadeDTO>> pesquisarCidades() {
		List<CidadeDTO> cidades = projetoService.pesquisarCidades();
		return ResponseEntity.ok(cidades);
	}

	@PostMapping
	public ResponseEntity<Void> incluirCidade(
			@Valid @RequestBody CidadeDTO cidadeDto
	) {
		projetoService.incluirCidade(cidadeDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping
	public ResponseEntity<Void> alterarCidade(
			@Valid @RequestBody CidadeDTO cidadeDto
	) {
		projetoService.alterarCidade(cidadeDto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{idCidade}")
	public ResponseEntity<Void> excluirCidade(
			@PathVariable Long idCidade
	) {
		projetoService.excluirCidade(idCidade);
		return ResponseEntity.noContent().build();
	}
}