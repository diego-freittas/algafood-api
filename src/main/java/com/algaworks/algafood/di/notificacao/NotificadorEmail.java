package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("prod") // Diz ao Spring que esse componente só vai ser iniciado no ambiente prod.
@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorEmail implements Notificador {

    @Setter
    private Boolean caixaAlta=false;
    @Getter
    private String hostSevidorSmtp;

    @Value("${notificador.email.host-servidor}") // fazendo uma injerção de valor
    private String host;
    @Value("${notificador.email.porta-servidor}")
    private Integer porta;


/*    public NotificadorEmail (String hostSevidorSmtp){
        this.hostSevidorSmtp = hostSevidorSmtp;
    }*/

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        if (this.caixaAlta){
            mensagem = mensagem.toUpperCase();
        }
        System.out.printf("Notificando %s através do e-mail %s usando o SMTP %s: %s\n",
                cliente.getNome(), cliente.getEmail(), this.getHostSevidorSmtp(), mensagem);
        System.out.println("Host: " + host);
        System.out.println("Porta: " + porta);
    }


}