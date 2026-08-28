package com.gerenciador_senha.singleton.factory;
import com.gerenciador_senha.singleton.model.ISenha;
import com.gerenciador_senha.singleton.model.SenhaPreferencial;

public class SenhaPreferencialCreator extends SenhaCreator{

    @Override
    public ISenha criarTipo(){
        return new SenhaPreferencial();
    }

}
