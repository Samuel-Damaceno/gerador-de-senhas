package com.gerenciador_senha.singleton.factory;

import com.gerenciador_senha.singleton.model.ISenha;
import com.gerenciador_senha.singleton.model.SenhaNormal;


public class SenhaNormalCreator extends SenhaCreator {

    @Override
    public ISenha criarTipo(){
        return new SenhaNormal();
    }


}
