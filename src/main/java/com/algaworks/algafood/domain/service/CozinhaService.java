package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CozinhaService {

    public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void excluir(Long cozinhaId) {
        try {
            cozinhaRepository.deleteById(cozinhaId);
            cozinhaRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaException(cozinhaId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }

    public Optional<Cozinha> findByNome(String nome){
        return cozinhaRepository.findByNome(nome);
    }

    public List<Cozinha> findAll() {
        return cozinhaRepository.findAll();
    }

    public Cozinha buscarOuFalhar(Long id){
        return cozinhaRepository.findById(id)
                .orElseThrow(()-> new CozinhaNaoEncontradaException(id));
    }

    public long count(){
        return cozinhaRepository.count();
    }
}
