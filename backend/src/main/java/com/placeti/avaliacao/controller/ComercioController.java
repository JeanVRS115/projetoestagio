package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.service.ComercioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comercios")
@CrossOrigin(origins = "http://localhost:4200")
public class ComercioController {

    private final ComercioService comercioService;

    public ComercioController(
            ComercioService comercioService
    ) {
        this.comercioService = comercioService;
    }

    @GetMapping
    public ResponseEntity<List<ComercioDTO>>
    pesquisarComercios() {
        return ResponseEntity.ok(
                comercioService.pesquisarComercios()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComercioDTO> pesquisarComercio(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                comercioService.pesquisarComercio(id)
        );
    }

    @PostMapping
    public ResponseEntity<ComercioDTO> incluirComercio(
            @Valid @RequestBody ComercioDTO dto
    ) {
        ComercioDTO comercioCriado =
                comercioService.incluirComercio(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(comercioCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComercioDTO> alterarComercio(
            @PathVariable Long id,
            @Valid @RequestBody ComercioDTO dto
    ) {
        return ResponseEntity.ok(
                comercioService.alterarComercio(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirComercio(
            @PathVariable Long id
    ) {
        comercioService.excluirComercio(id);
        return ResponseEntity.noContent().build();
    }
}