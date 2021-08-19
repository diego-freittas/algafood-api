package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.model.Estado;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    //@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE }) // "produces" serve para :Esta metodo só produz o formato especifico de conteudo
    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml(){
        return new CozinhasXmlWrapper(cozinhaRepository.listar());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
        Cozinha cozinha = cozinhaRepository.buscar(id);

        if (cozinha != null){
            return ResponseEntity.ok(cozinha);
        }
        return ResponseEntity.notFound().build();
        //return ResponseEntity.status(HttpStatus.OK).body(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        Cozinha cozinhaSalva = cozinhaRepository.salvar(cozinha);
        return cozinhaSalva;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> atualizar(@RequestBody Cozinha cozinha,
                                             @PathVariable Long id){
        Cozinha cozinhaAtual = cozinhaRepository.buscar(id);
        //cozinhaAtual.setNome(cozinha.getNome());

        if(cozinhaAtual != null) {
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
            cozinhaRepository.salvar(cozinhaAtual);
            return ResponseEntity.ok(cozinhaAtual);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cozinha> remover (@PathVariable Long id){
        try {
            Cozinha cozinha = cozinhaRepository.buscar(id);
            if (cozinha != null){
                cozinhaRepository.remover(cozinha);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }catch (DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}