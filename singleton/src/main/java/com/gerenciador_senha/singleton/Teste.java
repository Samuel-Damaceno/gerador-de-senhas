package com.gerenciador_senha.singleton;

import com.gerenciador_senha.singleton.factory.SenhaNormalCreator;
import com.gerenciador_senha.singleton.factory.SenhaPreferencialCreator;
import com.gerenciador_senha.singleton.model.ISenha;
import com.gerenciador_senha.singleton.service.GerenciadorSenha;

/**
 * Classe utilitária para testar o funcionamento do sistema via consola.
 * Permite verificar a geração de senhas e provar na prática que o padrão Singleton
 * está a manter uma única instância na memória.
 */
public class Teste {

    public static void main(String[] args) {

        // 1. Obtém a instância única do GerenciadorSenha
        GerenciadorSenha gerenciador = GerenciadorSenha.getInstancia();

        System.out.println("Gerando senhas:");

        // Gera e exibe senhas normais ('N') e preferenciais ('P')
        ISenha senha1 = gerenciador.gerarSenha(new SenhaNormalCreator());
        System.out.println(senha1.gerarSenha()); 

        ISenha senha2 = gerenciador.gerarSenha(new SenhaNormalCreator());
        System.out.println(senha2.gerarSenha()); 

        ISenha senha3 = gerenciador.gerarSenha(new SenhaPreferencialCreator());
        System.out.println(senha3.gerarSenha()); 

        ISenha senha4 = gerenciador.gerarSenha(new SenhaNormalCreator());
        System.out.println(senha4.gerarSenha()); 

        ISenha senha5 = gerenciador.gerarSenha(new SenhaPreferencialCreator());
        System.out.println(senha5.gerarSenha());


        System.out.println("\nTestando Singleton:");

        // 2. Tenta obter uma "segunda" referência do GerenciadorSenha
        GerenciadorSenha gerenciador2 = GerenciadorSenha.getInstancia();

        // O operador '==' compara os endereços de memória dos dois objetos
        if (gerenciador == gerenciador2) {
            System.out.println("Os dois gerenciadores são a mesma instância.");
        } else {
            System.out.println("ERRO: foram criadas duas instâncias.");
        }

        // 3. Exibe o histórico de senhas geradas até o momento
        System.err.println("\nTestando o histórico:");
        // Percorre cada objeto 'senha' dentro da lista 'historico'
        // Lembrete: o histórico armazena as senhas em ordem de criação
        // script.js inverte essa ordem para gerar um histórico correto para o frontend.
       System.out.println("\nTestando o histórico:");
        for (ISenha senha : gerenciador.getHistorico()) {
            // Chama o método gerarSenha() para exibir o texto da senha guardada no histórico
            System.out.println("- " + senha.gerarSenha());
        }
    }
}