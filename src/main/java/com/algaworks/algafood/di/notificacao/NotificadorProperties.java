package com.algaworks.algafood.di.notificacao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("notificador.email")
@Getter
@Setter
public class NotificadorProperties {

    private String hostServidor;
    private Integer portaServidor = 25;

}
