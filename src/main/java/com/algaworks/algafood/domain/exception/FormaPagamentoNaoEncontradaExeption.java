package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaExeption extends EntidadeNaoEncontradaException{
    private static final long serialVersionUID = 1L;
    public static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA = "Não foi encontrada uma foma de pagamento" +
            "com o código %d";

    public FormaPagamentoNaoEncontradaExeption(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradaExeption(Long id) {

        this(String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA, id));
    }

}
