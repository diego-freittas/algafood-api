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
import java.util.Optional;

@Service
public class ProdutoService {

    public static final String MSG_PRODUTO_EM_USO = "O produto de código %d não pode ser removido, pois está em uso";


    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto buscarOuFalhar(Long restaturanteID,Long produtoId) {
            return (Produto) produtoRepository.findById(restaturanteID,produtoId)
                    .orElseThrow(()-> new ProdutoNaoEncontradoException(restaturanteID,produtoId));
    }

    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    public List<Produto> listar(){
        return produtoRepository.findAll();
    }

    @Transactional
    public void excluir (Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        try {
            produtoRepository.deleteById(id);
            produtoRepository.flush();
        }catch (EmptyResultDataAccessException e){
            throw new ProdutoNaoEncontradoException(produto.get().getRestaurante().getId(),id);
        }catch (DataIntegrityViolationException e ){
            throw new EntidadeEmUsoException(
                    String.format(MSG_PRODUTO_EM_USO, id));
        }
    }
    public Produto deletarUmProdutoDeFormaLogica(Long restaturanteID,Long produtoId){
        Produto produto = this.buscarOuFalhar(restaturanteID,produtoId);
        produto.inativar();
        return produto;
    }
}

