package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;
import org.springframework.stereotype.Component;


@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorSMS implements Notificador{

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf("Notificando %s através de SMS %s: %s\n",
                cliente.getNome(), cliente.getTelefone(), mensagem);
    }
}
