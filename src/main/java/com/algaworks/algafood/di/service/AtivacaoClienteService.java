package com.algaworks.algafood.di.service;

import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;


@Component
public class AtivacaoClienteService {

    @TipoDoNotificador(value= NivelUrgencia.URGENTE)
    @Autowired // injeção de dependencia utilizando anotação.
    private Notificador notificador;

    // O Spring fornece um publicador de eventos
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

//    @PostConstruct
//    public void init(){
//        System.out.println("INIT");
//    }
//    @PreDestroy
//    public void destroy(){
//        System.out.println("DESTROY: Chamado um pouco antes do bean ser destruido");
//    }

    public void ativar(Cliente cliente) {
        //cliente.setAtivo(true);
        cliente.ativar();
         // direto no método fica muito aclopado a classe notificador. Devemos ter menos responsabilidade nesse classe
         //notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");

         // dizer para o container que o cliente está ativo neste momento.
         applicationEventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
    }

}