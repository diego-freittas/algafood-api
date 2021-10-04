package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaExeption;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormaPagamentoService {
    public static final String MSG_FORMA_PAGAMENTO_EM_USO = "A Forma de Pagamento de código %d não pode ser removida, pois está em uso";


    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }
    public List<FormaPagamento> listar(){
        return formaPagamentoRepository.findAll();
    }

    @Transactional
    public void excluir(Long id){
        try {
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();
        }catch (EmptyResultDataAccessException e){
            throw new FormaPagamentoNaoEncontradaExeption(id);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
        }
    }

    public FormaPagamento buscarOuFalhar(Long id){
        return formaPagamentoRepository.findById(id)
                .orElseThrow(()-> new FormaPagamentoNaoEncontradaExeption(id));
    }


}
