package com.algaworks.algafood.di.service;

import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.NotificadorEmail;

@Component
public class AtivacaoClienteService {

    @TipoDoNotificador(value= NivelUrgencia.URGENTE)
    @Autowired // injeção de dependencia utilizando anotação.
    private Notificador notificador;

    public void ativar(Cliente cliente) {
        //cliente.setAtivo(true);
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
    }

}