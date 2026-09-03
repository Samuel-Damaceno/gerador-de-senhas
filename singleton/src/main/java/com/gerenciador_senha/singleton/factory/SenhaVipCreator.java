package com.gerenciador_senha.singleton.factory;

import com.gerenciador_senha.singleton.model.ISenha;
import com.gerenciador_senha.singleton.model.SenhaVip;


public class SenhaVipCreator extends SenhaCreator {

    @Override
    public ISenha criarTipo(){
        return new SenhaVip();
    }


}
