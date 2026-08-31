package com.placeti.avaliacao.service;

import com.placeti.avaliacao.repository.CidadeRepository;
import com.placeti.avaliacao.repository.ComercioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ComercioServiceTest {

    @Mock
    private ComercioRepository comercioRepository;

    @Mock
    private CidadeRepository cidadeRepository;

    @InjectMocks
    private ComercioService comercioService;

    @Test
    void deveCriarComercioService() {
        assertNotNull(comercioService);
    }
}