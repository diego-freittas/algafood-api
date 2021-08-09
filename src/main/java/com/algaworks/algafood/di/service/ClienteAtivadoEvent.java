package com.algaworks.algafood.di.service;

import com.algaworks.algafood.di.modelo.Cliente;
import lombok.Getter;
import org.springframework.expression.spel.CodeFlow;
import org.springframework.stereotype.Component;

//@Component
public class ClienteAtivadoEvent {

    @Getter
    private Cliente cliente;

    public ClienteAtivadoEvent(Cliente cliente){
        super();
        this.cliente = cliente;
    }

}
