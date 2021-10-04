package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.assembler.CidadeDTOImputDisassembler;
import com.algaworks.algafood.api.modelDTO.CidadeDTO;
import com.algaworks.algafood.api.modelDTO.imput.CidadeDTOImput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeDTOAssembler cidadeDTOAssembler;

    @Autowired
    private CidadeDTOImputDisassembler cidadeDTOImputDisassembler;

    @GetMapping
    public List<CidadeDTO> listar() {
        return cidadeDTOAssembler.toCollectionDTO(cidadeService.findAll());
    }

    @GetMapping("/{cidadeId}")
    public CidadeDTO buscar(@PathVariable Long cidadeId) {
        return cidadeDTOAssembler.toDTO(cidadeService.buscarOuFalhar(cidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@RequestBody @Valid CidadeDTOImput cidadeDTOImput) {
        try {
            Cidade cidade = cidadeDTOImputDisassembler.toDomainObject(cidadeDTOImput);
            return cidadeDTOAssembler.toDTO(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @PutMapping("/{cidadeId}")
    public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeDTOImput cidadeDTOImput) {
        Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
        cidadeDTOImputDisassembler.copyToDomainObject(cidadeDTOImput,cidadeAtual);
        try {
            return cidadeDTOAssembler.toDTO(cidadeService.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);
    }


}
