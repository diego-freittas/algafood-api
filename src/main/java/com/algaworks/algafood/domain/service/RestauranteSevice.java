package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
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

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private ProdutoService produtoService;

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

    public List<Restaurante> findAll() {
        return restauranteRepository.findAll();
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

    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restauranteAtual = this.buscarOuFalhar(id);
        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restauranteAtual = this.buscarOuFalhar(id);
        restauranteAtual.inativar();
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void deletarUmProduto(Long restauranteId, Long produtoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Produto produto = produtoService.deletarUmProdutoDeFormaLogica(restauranteId, produtoId);
    }

    @Transactional
    public void associarProduto(Long restauranteId, Long produtoId) {
        Restaurante restaurante = this.buscarOuFalhar(restauranteId);
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        restaurante.adicionarProduto(produto);
    }

    @Transactional
    public void fechar(Long id) {
        Restaurante restauranteAtual = this.buscarOuFalhar(id);
        restauranteAtual.fechar();
    }

    @Transactional
    public void abrir(Long id) {
        Restaurante restauranteAtual = this.buscarOuFalhar(id);
        restauranteAtual.abrir();
    }

}
