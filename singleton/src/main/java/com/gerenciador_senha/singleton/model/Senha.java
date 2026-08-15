package com.gerenciador_senha.singleton.model;

public class Senha {

    private int numero;
    private char tipo;

    public Senha(int numero, char tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }

    public String getSenha() {
        return String.format("%c%03d", tipo, numero);
    }

    public int getNumero() {
        return numero;
    }

    public char getTipo() {
        return tipo;
    }
}