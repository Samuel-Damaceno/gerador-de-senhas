package com.gerenciador_senha.singleton.model;

public class SenhaNormal implements ISenha{

    private final String Senha;
    private static int contador = 0;

  
    public SenhaNormal() {
        contador ++;
        Senha = String.format("N - %03d" , contador);
    }

    @Override
    public String gerarSenha() {
        return Senha;
    }
     
    public String getSenha() {
        return Senha;
    }
}