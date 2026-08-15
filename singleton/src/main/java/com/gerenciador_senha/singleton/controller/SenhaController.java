package com.gerenciador_senha.singleton.controller;

import com.gerenciador_senha.singleton.model.Senha;
import com.gerenciador_senha.singleton.service.GerenciadorSenha;

import org.springframework.web.bind.annotation.*;

@RestController
public class SenhaController {

    @PostMapping("/senha/{tipo}")
    public String gerarSenha(@PathVariable char tipo) {

        GerenciadorSenha gerenciador =
                GerenciadorSenha.getInstancia();

        Senha senha = gerenciador.gerarSenha(tipo);

        return senha.getSenha();
    }
}