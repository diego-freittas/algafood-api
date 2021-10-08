package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioDTOImputDisassembler;
import com.algaworks.algafood.api.modelDTO.UsuarioCadastroDTO;
import com.algaworks.algafood.api.modelDTO.UsuarioSemSenhaDTO;
import com.algaworks.algafood.api.modelDTO.imput.SenhaInput;
import com.algaworks.algafood.api.modelDTO.imput.UsuarioCadastroDTOImput;
import com.algaworks.algafood.api.modelDTO.imput.UsuarioCadastroSenhaDTOImput;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;

    @Autowired
    private UsuarioDTOImputDisassembler usuarioDTOImputDisassembler;


    @GetMapping
    public List<UsuarioSemSenhaDTO> listar()
    {
        return usuarioDTOAssembler.toCollectionUsuarioSemSenhaDTO(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public UsuarioSemSenhaDTO buscar(@PathVariable Long id) {
        return usuarioDTOAssembler.toUsuarioSemSenhaDTO(usuarioService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioSemSenhaDTO adicionar(@RequestBody @Valid UsuarioCadastroSenhaDTOImput usuarioCadastroDTOImput) {

        Usuario usuario = usuarioDTOImputDisassembler.toDomainObject(usuarioCadastroDTOImput);
        return usuarioDTOAssembler.toUsuarioSemSenhaDTO(usuarioService.salvar(usuario));
    }

    @PutMapping("/{id}")
    public UsuarioSemSenhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioCadastroDTOImput usuarioCadastroDTOImput) {

        Usuario usuarioAtual = usuarioService.buscarOuFalhar(id);
        usuarioDTOImputDisassembler.copyToDomainObject(usuarioCadastroDTOImput,usuarioAtual);
        try {
            return usuarioDTOAssembler.toUsuarioSemSenhaDTO(usuarioService.salvar(usuarioAtual));
        } catch (UsuarioNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        usuarioService.excluir(id);
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senha){
        usuarioService.alterarSenha(id,senha.getSenhaAtual(),senha.getNovaSenha());
    }


}
