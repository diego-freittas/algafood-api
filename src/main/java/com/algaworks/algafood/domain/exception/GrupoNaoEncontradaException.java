package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradaException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;
    public static final String MSG_GRUPO_NAO_ENCONTRADO = "Não existe cadastro de grupo com código %d";

    public GrupoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradaException(Long grupoId) {
        this(String.format(MSG_GRUPO_NAO_ENCONTRADO, grupoId));
    }

}
