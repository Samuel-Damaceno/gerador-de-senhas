package com.gerenciador_senha.singleton;

import java.awt.Desktop;
import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/**
 * Classe principal da aplicação Spring Boot.
 * Além de inicializar o servidor web, ela escuta o evento de inicialização
 * para abrir o navegador automaticamente com a interface do projeto.
 */
@SpringBootApplication // Anotação principal do Spring Boot: ativa a autoconfiguração e a varredura de componentes (@Controller, @Service, etc.)
public class SingletonApplication {

    /**
     * Método principal (ponto de entrada) do programa em Java.
     * * @param args Parâmetros passados via linha de comando (se houver).
     */
    public static void main(String[] args) {
        // Inicializa o container do Spring Boot e sobe o servidor web embutido (Tomcat)
        SpringApplication.run(SingletonApplication.class, args);
    }

    /**
     * Método responsável por abrir o navegador padrão assim que o Spring Boot terminar de subir.
     * * A anotação @EventListener(ApplicationReadyEvent.class) garante que a função só será 
     * executada QUANDO o servidor já estiver 100% pronto e escutando na porta 8080.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void abrirNavegadorAoIniciar() {
        // Endereço local onde o servidor web está rodando
        String url = "http://localhost:8080/";
        
        try {
            // 1ª Tentativa: Usa a biblioteca AWT do Java (padrão e mais segura)
            // Verifica se o sistema operacional possui suporte a gerenciador de janelas/interface gráfica
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                // Abre a URL usando o navegador padrão do sistema
                Desktop.getDesktop().browse(new URI(url));
            } else {
                // 2ª Tentativa (Fallback): Executa comandos do próprio Sistema Operacional
                // Utilizado caso a biblioteca 'Desktop' falhe ou não seja suportada pelo ambiente
                Runtime runtime = Runtime.getRuntime();
                String os = System.getProperty("os.name").toLowerCase(); // Identifica o Sistema Operacional

                if (os.contains("win")) {
                    // Comando específico para abrir URL no Windows
                    runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
                } else if (os.contains("mac")) {
                    // Comando específico para abrir URL no macOS
                    runtime.exec("open " + url);
                } else if (os.contains("nix") || os.contains("nux")) {
                    // Comando específico para abrir URL no Linux
                    runtime.exec("xdg-open " + url);
                }
            }
        } catch (Exception e) {
            // Captura qualquer erro de permissão ou caminho e exibe no console sem derrubar o servidor
            System.err.println("Não foi possível abrir o navegador automaticamente: " + e.getMessage());
        }
    }
}