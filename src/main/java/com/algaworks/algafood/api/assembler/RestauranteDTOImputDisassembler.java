package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.modelDTO.imput.RestauranteDTOImput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTOImputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteDTOImput restauranteDTOImput){
        return modelMapper.map(restauranteDTOImput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteDTOImput restauranteDTOImput,
                                   Restaurante restaurante){
        //Como essa Cozinha não esta sendo gerenciada pelo JPA não ocorre o erro, caso não
        //criamos uma nova Cozinha o JPA entende que estamos alterando o id de uma cozinha
        //existente.
        // identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2

        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteDTOImput,restaurante);
    }
}
