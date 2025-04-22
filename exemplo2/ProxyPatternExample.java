package exemplo2;

import java.util.List;
import exemplo2.proxy.DatabaseAccessProxy;
import exemplo2.proxy.LogEntry;

public class ProxyPatternExample {

    public static void main(String[] args) {

        System.out.println("Iniciando aplicação...");
        
        // Obtém a instância do proxy (Singleton)
        DatabaseAccessProxy proxy = DatabaseAccessProxy.getInstance();
        
        System.out.println("\nExecutando operações no banco de dados:");

        // Executa operações no banco de dados através do proxy
        proxy.query("SELECT * FROM users");
        proxy.update("UPDATE users SET name = 'John' WHERE id = 1");
        proxy.delete("DELETE FROM users WHERE id = 2");
        
        // Exibe os logs armazenados no proxy
        System.out.println("\nRecuperando e exibindo logs armazenados:");
        
        List<LogEntry> logs = proxy.getLogs();
        for (LogEntry log : logs) {
            System.out.println(log);
        }
        
        System.out.println("\nExecutando mais uma operação:");
        proxy.query("SELECT COUNT(*) FROM products");
        
        System.out.println("\nExibindo logs atualizados:");
        logs = proxy.getLogs();

        for (LogEntry log : logs) {
            System.out.println(log);
        }
        
    }

}
