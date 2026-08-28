package com.gerenciador_senha.singleton.model;

public class SenhaPreferencial implements ISenha{

    @Override
    public String gerarSenha() {
        return "P-" + System.currentTimeMillis();
    }
}
