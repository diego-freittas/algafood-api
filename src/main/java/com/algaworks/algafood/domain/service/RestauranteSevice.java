package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteSevice {

    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe cadastro de cozinha com código %d";
    public static final String MSG_RESTAURANTE_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CidadeService cidadeService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {

        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);

        Cidade cidade = cidadeService.buscarOuFalhar(restaurante.getEndereco().getCidade().getId());

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        Restaurante restauranteDeRetorno = restauranteRepository.save(restaurante);
        return restauranteDeRetorno;
    }

    public  List<Restaurante> findAll(){
      return   restauranteRepository.findAll();
    }

    public Optional<Restaurante> findById(Long id) {
        return restauranteRepository.findById(id);
    }
    @Transactional
    public void excluir(Long id) {
        try {
            restauranteRepository.deleteById(id);
            //Manda o JPA executar as operações de banco que estão na fila
             restauranteRepository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new RestauranteNaoEncontradoException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));
        }
    }

    public Restaurante buscarOuFalhar(Long id){
        return  restauranteRepository.findById(id)
                .orElseThrow(()->new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public void ativar (Long id){
        Restaurante restauranteAtual = this.buscarOuFalhar(id);
        restauranteAtual.ativar();
    }
    @Transactional
    public void inativar(Long id){
        Restaurante restauranteAtual = this.buscarOuFalhar(id);
        restauranteAtual.inativar();
    }

}
