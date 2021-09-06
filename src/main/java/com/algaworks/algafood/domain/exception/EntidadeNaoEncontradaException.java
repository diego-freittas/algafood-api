package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(HttpStatus.NOT_FOUND)
// Se eu anotar o codigo na classe fica fixo.
public class EntidadeNaoEncontradaException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    // Extendendo ResponseStatusException posso passar pelo construtor o código customizado.
    public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
        super(status, mensagem);
    }
    // Dessa forma eu atribuir como padrão o NOT_FOUND, mas permitindo que o código seja alterado.
        public EntidadeNaoEncontradaException(String mensagem){
        this(HttpStatus.NOT_FOUND,mensagem);
    }
}
