package singleton.src.main.java.com.gerenciador_senha.singleton.model;
public class Senha {

    private int numero;
    private char tipo;

    Senha(int numero, char tipo) { //Construtor não público (apenas Classes dos mesmos pacotes podem acessar)
        this.numero = numero;
        this.tipo = tipo;
    }

    public String getSenha() {
        return String.format("%03d%c", numero, tipo);
    }

    public int getNumero() {
        return numero;
    }

    public char getTipo() {
        return tipo;
    }
}