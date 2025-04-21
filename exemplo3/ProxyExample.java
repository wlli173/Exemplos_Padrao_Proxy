package padroesprojeto.proxy.exemplo3;

import padroesprojeto.proxy.exemplo3.fileSystem.FileSystem;
import padroesprojeto.proxy.exemplo3.proxy.SecureFileSystemProxy;
import padroesprojeto.proxy.exemplo3.proxy.User;

public class ProxyExample {
    
    public static void main(String[] args) {

        // Cria usuários com diferentes permissões
        User admin = new User("Admin", true, false);
        User regularUser = new User("Usuário Regular", false, false);
        User readOnlyUser = new User("Usuário Read-Only", false, true);
        
        // Testa o sistema de arquivos com diferentes usuários
        System.out.println("=== Testando com usuário admin ===");
        testFileSystem(admin);
        
        System.out.println("\n=== Testando com usuário regular ===");
        testFileSystem(regularUser);
        
        System.out.println("\n=== Testando com usuário read-only ===");
        testFileSystem(readOnlyUser);

    }
    
    // Método para testar o sistema de arquivos com um usuário específico
    // O proxy irá controlar o acesso e as permissões de acordo com o usuário
    private static void testFileSystem(User user) {

        FileSystem fs = new SecureFileSystemProxy(user);
        
        try {

            System.out.println("\nOperações como " + user.getName() + ":");
            
            // Teste de escrita
            fs.writeFile("document.txt", "Conteúdo importante");
            fs.writeFile("config.admin", "Configurações sensíveis");
            
            // Teste de leitura
            System.out.println("Conteúdo lido: " + fs.readFile("document.txt"));
            System.out.println("Tentando ler arquivo admin:");
            System.out.println(fs.readFile("config.admin"));
            
            // Teste de listagem
            System.out.println("Arquivos: " + fs.listFiles());
            
            // Teste de exclusão
            fs.deleteFile("document.txt");
            fs.deleteFile("config.protected");
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        
        // Mostra logs de auditoria
        System.out.println("\nLogs de auditoria para " + user.getName() + ":");
        ((SecureFileSystemProxy)fs).getAuditLog().forEach(System.out::println);
    }

}
