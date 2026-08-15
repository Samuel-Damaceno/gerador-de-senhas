package com.gerenciador_senha.singleton.controller;

import com.gerenciador_senha.singleton.model.Senha;
import com.gerenciador_senha.singleton.service.GerenciadorSenha;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/senhas")
@CrossOrigin(origins = "*")
public class SenhaController {

    private final GerenciadorSenha gerenciador =
            GerenciadorSenha.getInstancia();


    @PostMapping("/{tipo}")
    public Senha gerarSenha(@PathVariable char tipo) {

        return gerenciador.gerarSenha(
                Character.toUpperCase(tipo)
        );
    }


    @GetMapping("/historico")
    public List<Senha> buscarHistorico() {

        return gerenciador.getHistorico();
    }


    @GetMapping("/atual")
    public Senha buscarSenhaAtual() {

        return gerenciador.getSenhaAtual();
    }
}