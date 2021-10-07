package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoDTOImputDisassembler;
import com.algaworks.algafood.api.modelDTO.FormaPagamentoDTO;
import com.algaworks.algafood.api.modelDTO.imput.FormaPagamentoDTOImput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {


    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    @Autowired
    private FormaPagamentoDTOImputDisassembler formaPagamentoDTOImputDisassembler;

    @GetMapping
    public List<FormaPagamentoDTO> listar() {
        return formaPagamentoDTOAssembler.toCollectionDTO(formaPagamentoService.listar());
    }

    @GetMapping("/{id}")
    public FormaPagamentoDTO buscar(@PathVariable Long id) {
        FormaPagamento formaPagamento = (FormaPagamento)formaPagamentoService.buscarOuFalhar(id);
        return formaPagamentoDTOAssembler.toDTO(formaPagamento);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoDTOImput formaPagamentoDTOImput) {
    return formaPagamentoDTOAssembler.toDTO(
            formaPagamentoService.salvar(
                    formaPagamentoDTOImputDisassembler.toDomainObject(formaPagamentoDTOImput)));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
    formaPagamentoService.excluir(id);
    }

    @PutMapping("/{id}")
    public FormaPagamentoDTO atualizar(@PathVariable Long id,
                                         @RequestBody @Valid FormaPagamentoDTOImput formaPagamentoDTOImput) {
        FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(id);
        formaPagamentoDTOImputDisassembler.copyToDomainObject(formaPagamentoDTOImput,formaPagamentoAtual);
        return formaPagamentoDTOAssembler.toDTO(formaPagamentoService.salvar(formaPagamentoAtual));
    }

}
