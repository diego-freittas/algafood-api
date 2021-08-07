package com.algaworks.algafood.di.notificacao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

import java.util.Locale;

@Component
public class NotificadorEmail implements Notificador {

    @Setter
    private Boolean caixaAlta;
    @Getter
    private String hostSevidorSmtp;


    public NotificadorEmail (String hostSevidorSmtp){
        this.hostSevidorSmtp = hostSevidorSmtp;
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        if (this.caixaAlta){
            mensagem = mensagem.toUpperCase();
        }
        System.out.printf("Notificando %s através do e-mail %s usando o SMTP %s: %s\n",
                cliente.getNome(), cliente.getEmail(), this.getHostSevidorSmtp(), mensagem);
    }


}