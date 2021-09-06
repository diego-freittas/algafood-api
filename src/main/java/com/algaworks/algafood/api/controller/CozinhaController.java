package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {


    @Autowired
    private CozinhaService cozinhaService;

    //@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE }) // "produces" serve para :Esta metodo só produz o formato especifico de conteudo
    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaService.findAll();
    }

    @GetMapping("/{id}")
    public Cozinha buscar(@PathVariable Long id) {

        return cozinhaService.buscarOuFalhar(id);
    }
    @GetMapping("/buscar-nome/{nome}")
    public ResponseEntity<Cozinha> buscarPorNome(@PathVariable String nome) {
        Optional<Cozinha> cozinha = cozinhaService.findByNome(nome);

        if (cozinha.isPresent()) {
            return ResponseEntity.ok(cozinha.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cozinhaService.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public Cozinha atualizar(@RequestBody Cozinha cozinha, @PathVariable Long id) {
        Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);
        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        return cozinhaService.salvar(cozinhaAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)// Caso o método não gere um erro retornamos NO_CONTENT
    public void remover(@PathVariable Long id) {
                cozinhaService.excluir(id);
    }

}