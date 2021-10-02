package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.assembler.CozinhaDTOImputDisassembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.imput.CozinhaDTOImput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {


    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaDTOAssembler cozinhaDTOAssembler;

    @Autowired
    private CozinhaDTOImputDisassembler cozinhaDTOImputDisassembler;

    //@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE }) // "produces" serve para :Esta metodo só produz o formato especifico de conteudo
    @GetMapping
    public List<CozinhaDTO> listar() {
        return cozinhaDTOAssembler.toCollectionDTO(cozinhaService.findAll());
    }

    @GetMapping("/{id}")
    public Cozinha buscar(@PathVariable Long id) {

        return cozinhaService.buscarOuFalhar(id);
    }
    @GetMapping("/buscar-nome/{nome}")
    public ResponseEntity<CozinhaDTO> buscarPorNome(@PathVariable String nome) {
        Optional<Cozinha> cozinha = cozinhaService.findByNome(nome);

        if (cozinha.isPresent()) {
            return ResponseEntity.ok(cozinhaDTOAssembler.toDTO(cozinha.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaDTOImput cozinhaDTOImput) {
        Cozinha cozinha = cozinhaDTOImputDisassembler.toDomainObject(cozinhaDTOImput);
        return cozinhaDTOAssembler.toDTO(cozinhaService.salvar(cozinha));
    }

    @PutMapping("/{id}")
    public CozinhaDTO atualizar(@RequestBody  @Valid CozinhaDTOImput cozinhaDTOImput, @PathVariable Long id) {
        Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);
        cozinhaDTOImputDisassembler.copyToDomainObject(cozinhaDTOImput,cozinhaAtual);
        //BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        return cozinhaDTOAssembler.toDTO(cozinhaService.salvar(cozinhaAtual));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)// Caso o método não gere um erro retornamos NO_CONTENT
    public void remover(@PathVariable Long id) {
                cozinhaService.excluir(id);
    }

}