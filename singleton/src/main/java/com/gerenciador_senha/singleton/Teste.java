import singleton.src.main.java.com.gerenciador_senha.singleton.model.Senha;
import singleton.src.main.java.com.gerenciador_senha.singleton.service.GerenciadorSenha;

public class Teste {

    public static void main(String[] args) {

        GerenciadorSenha gerenciador = GerenciadorSenha.getInstancia();

        System.out.println("Gerando senhas:");

        Senha senha1 = gerenciador.gerarSenha('N');
        System.out.println(senha1.getSenha());

        Senha senha2 = gerenciador.gerarSenha('N');
        System.out.println(senha2.getSenha());

        Senha senha3 = gerenciador.gerarSenha('P');
        System.out.println(senha3.getSenha());

        Senha senha4 = gerenciador.gerarSenha('N');
        System.out.println(senha4.getSenha());

        Senha senha5 = gerenciador.gerarSenha('P');
        System.out.println(senha5.getSenha());

        System.out.println("\nTestando Singleton:");

        GerenciadorSenha gerenciador2 = GerenciadorSenha.getInstancia();

        if (gerenciador == gerenciador2) {
            System.out.println("Os dois gerenciadores são a mesma instância.");
        } else {
            System.out.println("ERRO: foram criadas duas instâncias.");
        }

        gerenciador.mostrarHistorico();
    }
}