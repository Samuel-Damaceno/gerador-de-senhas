package com.gerenciador_senha.singleton.service;

import java.util.ArrayList;

import com.gerenciador_senha.singleton.factory.SenhaCreator;
import com.gerenciador_senha.singleton.model.ISenha;

/**
 * Classe responsável pelo gerenciamento de senhas da aplicação.
 * Implementa o Padrão de Projeto Singleton, garantindo que exista apenas
 * uma única instância desta classe em toda a memória da aplicação.
 */
public class GerenciadorSenha {

    // 1. Atributo estático que armazenará a única instância da classe (Singleton)
    private static GerenciadorSenha instancia;
    private String senhaAtual;       // Guarda a última senha que foi gerada

    // Lista para armazenar o histórico recente das últimas senhas geradas
    private ArrayList<String> historico = new ArrayList<>();

    /**
     * 2. Construtor PRIVADO.
     * Impede que outras partes do sistema criem novas instâncias com 'new GerenciadorSenha()'.
     */
    private GerenciadorSenha() {
    }

    /**
     * 3. Ponto central de acesso do Singleton.
     * Se a instância ainda não existir em memória, ela é criada.
     * Caso contrário, a instância existente é retornada.
     *
     * @return A única instância de GerenciadorSenha.
     */
    public static GerenciadorSenha getInstancia() {
        if (instancia == null) {
            instancia = new GerenciadorSenha();
        }
        return instancia;
    }

    //*******************

     public String gerarSenha (SenhaCreator creator){
         ISenha senhaGerada = creator.criarTipo(); //gera senhaNormal ou senhaPreferencial
         senhaAtual = senhaGerada.gerarSenha();
         historico.add(senhaAtual);

         if (historico.size() > 5){
             historico.remove(0);
         }
        return senhaAtual;

     }

    //*******************

    public String getSenhaAtual(){
        return senhaAtual;
    }

    public ArrayList<String> getHistorico(){
        return historico;
    }

}