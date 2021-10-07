package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.assembler.EstadoDTOImputDisassembler;
import com.algaworks.algafood.api.modelDTO.EstadoDTO;
import com.algaworks.algafood.api.modelDTO.imput.EstadoDTOImput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoDTOAssembler estadoDTOAssembler;

    @Autowired
    private EstadoDTOImputDisassembler estadoDTOImputDisassembler;

    @GetMapping
    public List<EstadoDTO> listar() {
        return estadoDTOAssembler.toCollectionDTO(estadoRepository.findAll());
    }

    @GetMapping("/{estadoId}")
    public EstadoDTO buscar(@PathVariable Long estadoId) {
        return estadoDTOAssembler.toDTO(estadoService.buscarOuFalhar(estadoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO adicionar(
            @RequestBody  @Valid EstadoDTOImput estadoDTOImput) {
        Estado estado = estadoDTOImputDisassembler.toDomainObject(estadoDTOImput);
        return estadoDTOAssembler.toDTO(estadoService.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public EstadoDTO atualizar(@PathVariable Long estadoId,
        @RequestBody @Valid EstadoDTOImput estadoDTOImput) {
        Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);
        estadoDTOImputDisassembler.copyToDomainObject(estadoDTOImput,estadoAtual);
        return estadoDTOAssembler.toDTO(estadoService.salvar(estadoAtual));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.excluir(estadoId);
    }
}
