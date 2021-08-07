package com.algaworks.algafood.di.service;

import com.algaworks.algafood.di.notificacao.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.NotificadorEmail;

@Component
public class AtivacaoClienteService {

    @Autowired // injeção de dependencia utilizando anotação.
    private Notificador notificador;

    public void ativar(Cliente cliente) {
        //cliente.setAtivo(true);
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
    }

}