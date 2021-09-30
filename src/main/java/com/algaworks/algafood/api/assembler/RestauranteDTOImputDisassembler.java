package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.imput.RestauranteDTOImput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTOImputDisassembler {

    public Restaurante toDomainObject(RestauranteDTOImput restauranteDTOImput){

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteDTOImput.getNome());
        restaurante.setTaxaFrete(restauranteDTOImput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteDTOImput.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        return restaurante;
    }
}
