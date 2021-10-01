package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.imput.RestauranteDTOImput;
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
                                   Restaurante restaurante){}
}
