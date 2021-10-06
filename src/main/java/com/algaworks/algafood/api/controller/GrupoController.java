package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.GrupoDTOImputDisassembler;
import com.algaworks.algafood.api.modelDTO.GrupoDTO;
import com.algaworks.algafood.api.modelDTO.imput.GrupoDTOImput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoDTOImputDisassembler grupoDTOImputDisassembler;

    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;

    @GetMapping
    public List<GrupoDTO> listar() {
        return grupoDTOAssembler.toCollectionDTO(grupoService.findAll());
    }

    @GetMapping("/{id}")
    public GrupoDTO buscar(@PathVariable Long id) {
        return grupoDTOAssembler.toDTO(grupoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoDTOImput grupoDTOImput) {
        try {
            Grupo grupo = grupoDTOImputDisassembler.toDomainObject(grupoDTOImput);
            return grupoDTOAssembler.toDTO(grupoService.salvar(grupo));
        } catch (GrupoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @PutMapping("/{id}")
    public GrupoDTO atualizar(@PathVariable Long id, @RequestBody @Valid GrupoDTOImput grupoDTOImput) {
        Grupo grupoAtual = grupoService.buscarOuFalhar(id);
        grupoDTOImputDisassembler.copyToDomainObject(grupoDTOImput,grupoAtual);
        try {
            return grupoDTOAssembler.toDTO(grupoService.salvar(grupoAtual));
        } catch (GrupoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        grupoService.excluir(id);
    }


}
