package com.gerenciador_senha.singleton.service;


import java.util.ArrayList;
import com.gerenciador_senha.singleton.model.Senha;


public class GerenciadorSenha {

    private static GerenciadorSenha instancia;  //Instância única, 

    private int numeroNormal;         //Valor da senha da fila Normal;
    private int numeroPreferencial;   //Valor da senha da fila Preferencial.

    private Senha senhaAtual;         //Guarda a senha atual

    private ArrayList<Senha> historico = new ArrayList<>();

    private GerenciadorSenha() { //Construtor vazio e privado.
    }

    public static GerenciadorSenha getInstancia() { //Verifica se existe uma instância, caso contrário cria uma nova.

        if (instancia == null) {
            instancia = new GerenciadorSenha();
        }

        return instancia;
    }

    public Senha gerarSenha(char tipo) {

        if (tipo != 'N' && tipo != 'P') {
            throw new IllegalArgumentException("Tipo de senha inválido.");
        } else if (tipo == 'N') {
            numeroNormal++;
            senhaAtual = new Senha(numeroNormal, tipo);

        } else if (tipo == 'P') {
            numeroPreferencial++;
            senhaAtual = new Senha(numeroPreferencial, tipo);
        } 

        adicionarHistorico(senhaAtual);

        return senhaAtual;
    }

    private void adicionarHistorico(Senha senha) {
        historico.add(senha);

        if (historico.size() > 5) {
            historico.remove(0);
        }
    }

    public Senha getSenhaAtual() {
        return senhaAtual;
    }

    public void mostrarHistorico() {

        System.out.println("\nHistórico:");

        for (Senha senha : historico) {
            System.out.println(senha.getSenha());
        }
    }

    public ArrayList<Senha> getHistorico(){
        return historico;
    }
}