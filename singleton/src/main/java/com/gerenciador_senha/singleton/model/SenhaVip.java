package com.gerenciador_senha.singleton.model;

public class SenhaVip implements ISenha {
 
    @Override
    public String gerarSenha(){
        return "V-" + System.currentTimeMillis(); //Concatenação entre o tipo de senha e o milissegundo do tempo atual.
    }


}
