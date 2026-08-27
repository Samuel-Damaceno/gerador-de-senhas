package com.gerenciador_senha.singleton.model;

public class SenhaNormal implements ISenha{

    @Override
    public String gerarTicket(){
        return "N-" + System.currentTimeMillis(); //Concatenação entre o tipo de senha e o milissegundo do tempo atual.
    }



}
