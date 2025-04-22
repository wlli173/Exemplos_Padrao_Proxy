package exemplo3.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import exemplo3.fileSystem.FileSystem;
import exemplo3.fileSystem.RealFileSystem;

//Implementa o padrão Proxy para controle de acesso e cache em um sistema de arquivos
// O proxy implementa a interface FileSystem e controla o acesso ao RealFileSystem
// Adiciona funcionalidades como cache, auditoria e controle de acesso
// O proxy também implementa lazy initialization, criando o RealFileSystem apenas quando necessário
public class SecureFileSystemProxy implements FileSystem{
    
    private RealFileSystem realFileSystem;
    private Map<String, String> cache = new HashMap<>();
    private List<String> auditLog = new ArrayList<>();
    private User currentUser;
    
    public SecureFileSystemProxy(User currentUser) {
        this.currentUser = currentUser;
    }
    
    // Métodos de pré-processamento

    // Verifica permissões de leitura e escrita
    private boolean hasReadPermission(String filename) {
        if (filename.endsWith(".admin") && !currentUser.isAdmin()) {
            audit("Tentativa de acesso não autorizado a " + filename);
            return false;
        }
        return true;
    }
    
    // Verifica se o usuário tem permissão de escrita
    private boolean hasWritePermission() {
        if (currentUser.isReadOnly()) {
            audit("Tentativa de escrita por usuário read-only");
            return false;
        }
        return true;
    }
    
    // Valida o nome do arquivo
    private boolean validateFilename(String filename) {
        if (filename == null || filename.isEmpty()) {
            audit("Tentativa com nome de arquivo inválido");
            return false;
        }
        return true;
    }
    
    // Métodos de pós-processamento

    // Registra ações de auditoria
    private void audit(String action) {
        String logEntry = "AUDIT [" + System.currentTimeMillis() + "]: " + 
                         currentUser.getName() + " - " + action;
        auditLog.add(logEntry);
        System.out.println(logEntry);
    }
    
    // Notifica mudanças de arquivo
    private void notifyFileChange(String filename, String operation) {
        System.out.println("NOTIFICAÇÃO: Arquivo " + filename + 
                         " " + operation + " por " + currentUser.getName());
    }
    
    // Implementação dos métodos da interface com pré/pós-processamento

    // Lê o conteúdo de um arquivo, com cache e controle de acesso
    @Override
    public String readFile(String filename) {
        // Verifica se o arquivo é protegido e se o usuário tem permissão
        // Proxy acrescenta funcionalidades de controle de acesso e cache
        // O proxy também implementa lazy initialization, criando o RealFileSystem apenas quando necessário

        // Pré-processamento

        if (!validateFilename(filename)) {
            throw new IllegalArgumentException("Nome de arquivo inválido");
        }
        
        if (!hasReadPermission(filename)) {
            throw new SecurityException("Permissão negada para leitura");
        }
        
        // Verifica cache antes de chamar o objeto real
        if (cache.containsKey(filename)) {
            audit("Leitura do cache: " + filename);
            return cache.get(filename);
        }
        
        // Chamada ao objeto real (lazy initialization)
        if (realFileSystem == null) {
            realFileSystem = new RealFileSystem();
        }
        
        String content = realFileSystem.readFile(filename);
        
        // Pós-processamento
        cache.put(filename, content); // Atualiza cache
        audit("Leitura de arquivo: " + filename);
        
        return content;
    }
    
    // Escreve o conteúdo em um arquivo, com controle de acesso e cache
    @Override
    public void writeFile(String filename, String content) {
        // Pré-processamento
        if (!validateFilename(filename)) {
            throw new IllegalArgumentException("Nome de arquivo inválido");
        }
        
        if (!hasWritePermission()) {
            throw new SecurityException("Permissão negada para escrita");
        }
        
        if (content == null || content.length() > 1024 * 1024) {
            audit("Tentativa de escrita com conteúdo inválido ou muito grande");
            throw new IllegalArgumentException("Conteúdo inválido");
        }
        
        // Chamada ao objeto real (lazy initialization)
        if (realFileSystem == null) {
            realFileSystem = new RealFileSystem();
        }
        
        realFileSystem.writeFile(filename, content);
        
        // Pós-processamento
        cache.remove(filename); // Invalida cache
        audit("Escrita em arquivo: " + filename);
        notifyFileChange(filename, "modificado");
    }
    
    // Exclui um arquivo, com controle de acesso e cache
    @Override
    public void deleteFile(String filename) {
        // Pré-processamento
        if (!validateFilename(filename)) {
            throw new IllegalArgumentException("Nome de arquivo inválido");
        }
        
        if (!hasWritePermission()) {
            throw new SecurityException("Permissão negada para exclusão");
        }
        
        if (filename.endsWith(".protected")) {
            audit("Tentativa de excluir arquivo protegido: " + filename);
            throw new SecurityException("Não é permitido excluir arquivos protegidos");
        }
        
        // Chamada ao objeto real (lazy initialization)
        if (realFileSystem == null) {
            realFileSystem = new RealFileSystem();
        }
        
        realFileSystem.deleteFile(filename);
        
        // Pós-processamento
        cache.remove(filename); // Invalida cache
        audit("Exclusão de arquivo: " + filename);
        notifyFileChange(filename, "excluído");
    }
    
    // Listar arquivos, com controle de acesso
    @Override
    public List<String> listFiles() {
        // Chamada ao objeto real (lazy initialization)
        if (realFileSystem == null) {
            realFileSystem = new RealFileSystem();
        }
        
        List<String> files = realFileSystem.listFiles();
        
        // Pós-processamento: filtra arquivos restritos
        if (!currentUser.isAdmin()) {
            files.removeIf(name -> name.endsWith(".admin"));
        }
        
        audit("Listagem de arquivos");
        return files;
    }
    
    // Métodos adicionais do proxy

    // Retorna a audit log
    // Permite acesso a logs de auditoria para fins de monitoramento
    public List<String> getAuditLog() {
        return new ArrayList<>(auditLog);
    }
    
    // Limpa o cache
    public void clearCache() {
        cache.clear();
        audit("Cache limpo");
    }

}