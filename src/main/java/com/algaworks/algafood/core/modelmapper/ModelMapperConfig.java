package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.modelDTO.EnderecoDTO;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){

        var modelMapper = new ModelMapper();

//        modelMapper().createTypeMap(Restaurante.class, RestauranteDTO.class)
//                .addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setTaxaFrete);

        var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoDTO.class);

        enderecoToEnderecoDTOTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoDTODest, value) -> enderecoDTODest.getCidade().setEstado(value));

        return modelMapper;
    }
}
