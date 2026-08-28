package com.gerenciador_senha.singleton.controller;

import com.gerenciador_senha.singleton.service.GerenciadorSenha;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST responsável por expor as rotas (endpoints) da API de senhas.
 * É esta classe que recebe as requisições HTTP vindas do frontend (JavaScript/Fetch)
 * e devolve as respostas no formato JSON.
 */
@RestController // Indica ao Spring que esta classe é um controller REST (retorna dados em JSON/XML, não páginas HTML)
@RequestMapping("/api/senhas") // Define a URL base para todos os endpoints deste controller (ex: http://localhost:8080/api/senhas)
@CrossOrigin(origins = "*") // Permite que a API receba chamadas de diferentes origens (evita erros de CORS durante os testes)
public class SenhaController {

    // Obtém a instância única do serviço de gerenciamento de senhas (Singleton)
    private final GerenciadorSenha gerenciador = GerenciadorSenha.getInstancia();

    /**
     * Endpoint para gerar uma nova senha.
     * Mapeia requisições HTTP do tipo POST para a rota: /api/senhas/{tipo}
     *
     * @param tipo Caractere recebido na URL que representa o tipo ('N' para Normal, 'P' para Preferencial).
     * @return O objeto Senha recém-gerado, que o Spring converte automaticamente em JSON.
     */
    @PostMapping("/{tipo}")
    public Senha gerarSenha(@PathVariable char tipo) {
        // Converte o caractere recebido na URL para maiúsculo e chama o serviço de senhas
        return gerenciador.gerarSenha(Character.toUpperCase(tipo));
    }

    /**
     * Endpoint para consultar o histórico das últimas senhas geradas.
     * Mapeia requisições HTTP do tipo GET para a rota: /api/senhas/historico
     *
     * @return Lista (List) contendo os últimos objetos Senha gerados.
     */
    @GetMapping("/historico")
    public List<Senha> buscarHistorico() {
        return gerenciador.getHistorico();
    }

    /**
     * Endpoint para consultar a senha que acabou de ser gerada (senha atual).
     * Mapeia requisições HTTP do tipo GET para a rota: /api/senhas/atual
     *
     * @return O objeto Senha atual do sistema.
     */
    @GetMapping("/atual")
    public Senha buscarSenhaAtual() {
        return gerenciador.getSenhaAtual();
    }
}