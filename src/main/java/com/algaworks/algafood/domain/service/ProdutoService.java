package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    public static final String MSG_PRODUTO_EM_USO = "O produto de código %d não pode ser removido, pois está em uso";


    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto buscarOuFalhar(Long produtoId) {

            return produtoRepository.findById(produtoId)
                    .orElseThrow(()-> new ProdutoNaoEncontradoException(produtoId));
    }

    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    public List<Produto> listar(){
        return produtoRepository.findAll();
    }

    @Transactional
    public void excluir (Long id){
        try {
            produtoRepository.deleteById(id);
            produtoRepository.flush();
        }catch (EmptyResultDataAccessException e){
            throw new ProdutoNaoEncontradoException(id);
        }catch (DataIntegrityViolationException e ){
            throw new EntidadeEmUsoException(
                    String.format(MSG_PRODUTO_EM_USO, id));
        }
    }
    public Produto deletarUmProdutoDeFormaLogica(Long id){
        Produto produto = this.buscarOuFalhar(id);
        produto.inativar();
        return produto;
    }
}

