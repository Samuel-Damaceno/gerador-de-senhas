package com.gerenciador_senha.singleton;

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
        ISenha senha1 = gerenciador.gerarSenha('N');
        System.out.println(senha1.getSenha()); // Deve exibir "N001"

        Senha senha2 = gerenciador.gerarSenha('N');
        System.out.println(senha2.getSenha()); // Deve exibir "N002"

        Senha senha3 = gerenciador.gerarSenha('P');
        System.out.println(senha3.getSenha()); // Deve exibir "P001"

        Senha senha4 = gerenciador.gerarSenha('N');
        System.out.println(senha4.getSenha()); // Deve exibir "N003"

        Senha senha5 = gerenciador.gerarSenha('P');
        System.out.println(senha5.getSenha()); // Deve exibir "P002"


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
        for (Senha senha : gerenciador.getHistorico()) {
            // Chama o método getSenha() para exibir a senha formatada (ex: N001, P001)
            System.out.println("- " + senha.getSenha());
        }
    }
}