package com.gerenciador_senha.singleton.factory;
import com.gerenciador_senha.singleton.model.ISenha;
import com.gerenciador_senha.singleton.model.SenhaIdoso;

public class SenhaIdosoCreator extends SenhaCreator{

    @Override
    public ISenha criarTipo(){
        return new SenhaIdoso();
    }

}
