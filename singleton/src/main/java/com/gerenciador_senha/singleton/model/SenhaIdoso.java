package com.gerenciador_senha.singleton.model;

public class SenhaIdoso implements ISenha{

    @Override
    public String gerarSenha() {
        return "I-" + System.currentTimeMillis();
    }
}
