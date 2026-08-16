package com.gerenciador_senha.singleton.model;

/**
 * Classe que representa uma Senha no sistema de atendimento.
 * Ela armazena o número sequencial da senha e o tipo de atendimento (Normal ou Preferencial).
 */
public class Senha {

    // Atributos privados para garantir o Encapsulamento (proteção dos dados)
    private int numero; // Armazena o número sequencial (ex: 1, 2, 3...)
    private char tipo;   // Armazena a categoria da senha ('N' para Normal, 'P' para Preferencial)

    /**
     * Construtor padrão (sem parâmetros).
     * Necessário para que bibliotecas de serialização/conversão de JSON (como o Jackson do Spring)
     * consigam instanciar este objeto automaticamente.
     */
    public Senha() {
    }

    /**
     * Construtor completo.
     * Utilizado para criar um novo objeto Senha já definindo o número e o tipo.
     * * @param numero Número sequencial da senha.
     * @param tipo   Tipo de atendimento ('N' ou 'P').
     */
    public Senha(int numero, char tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }

    /**
     * Método responsável por formatar a senha para exibição na tela/JSON.
     * Exemplo: se o tipo for 'N' e o número for 5, ele retornará "N005".
     * * @return String com a senha formatada com a letra e 3 dígitos numéricos.
     */
    public String getSenha() {
        // %c representa o caractere 'tipo'
        // %03d formata o número inteiro com 3 dígitos, preenchendo com zeros à esquerda
        return String.format("%c%03d", tipo, numero);
    }

    // --- GETTERS E SETTERS ---
    // Permitem ler (get) e alterar (set) os valores dos atributos privados com segurança.

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
}