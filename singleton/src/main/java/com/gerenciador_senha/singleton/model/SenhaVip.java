package com.gerenciador_senha.singleton.model;

public class SenhaVip implements ISenha {
 
private final String Senha;
    private static int contador = 0;

  
    public SenhaVip() {
        contador ++;
       Senha = String.format("V - %03d" , contador);
    }

    @Override
    public String gerarSenha() {
        return Senha;
    }

    public String getSenha() {
        return Senha;
    }
}