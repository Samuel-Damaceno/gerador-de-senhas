package com.gerenciador_senha.singleton.model;

public class SenhaIdoso implements ISenha{

    private final String Senha;
    private static int contador = 0;

  
    public SenhaIdoso() {
        contador ++;
        Senha = String.format("I - %03d" , contador);
    }

    
    @Override
    public String gerarSenha() {
        return Senha;
    }

    public String getSenha() {
        return Senha;
    }
}
