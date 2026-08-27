package com.gerenciador_senha.singleton.service;

import java.util.ArrayList;
import com.gerenciador_senha.singleton.model.Senha;
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

    // Contadores para controlar a numeração sequencial das senhas
    private int numeroNormal;       // Contador para senhas do tipo Normal
    private int numeroPreferencial; // Contador para senhas do tipo Preferencial

    private Senha senhaAtual;       // Guarda a última senha que foi gerada

    // Lista para armazenar o histórico recente das últimas senhas geradas
    private ArrayList<Senha> historico = new ArrayList<>();

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

    /**
     * Gera uma nova senha com base no tipo solicitado ('N' ou 'P').
     *
     * @param tipo Caractere representando o tipo de atendimento ('N' - Normal, 'P' - Preferencial).
     * @return O objeto Senha recém-criado.
     * @throws IllegalArgumentException Se o tipo for diferente de 'N' ou 'P'.
     */


     public String gerarSenha (SenhaCreator creator){
         ISenha senha = creator.criarSenha();
         senhaAtual = senha.

     }








        // Adiciona a nova senha gerada ao histórico
        adicionarHistorico(senhaAtual);

        return senhaAtual;
    }

    /**
     * Adiciona a senha gerada à lista de histórico.
     * Mantém no histórico no máximo as últimas 5 senhas geradas.
     *
     * @param senha A senha a ser registrada no histórico.
     */
    private void adicionarHistorico(Senha senha) {
        historico.add(senha);

        // Se o histórico ultrapassar 5 itens, remove o mais antigo (o primeiro da lista, índice 0)
        if (historico.size() > 5) {
            historico.remove(0);
        }
    }

    // --- GETTERS ---

    /**
     * Retorna a última senha gerada no sistema.
     */
    public Senha getSenhaAtual() {
        return senhaAtual;
    }

    /**
     * Retorna a lista com o histórico das últimas senhas geradas.
     */
    public ArrayList<Senha> getHistorico() {
        return historico;
    }
}