package padroesprojeto.proxy.exemplo2.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import padroesprojeto.proxy.exemplo2.bdLib.DatabaseAccess;
import padroesprojeto.proxy.exemplo2.bdLib.RealDatabaseAccess;

// Proxy que implementa a interface DatabaseAccess
// A classe RealDatabaseAccess é a implementação real do serviço de banco de dados
// O proxy irá armazenar em cache os logs de acesso ao banco de dados
public class DatabaseAccessProxy implements DatabaseAccess {
    
    private RealDatabaseAccess realDatabaseAccess;
    private static DatabaseAccessProxy instance;
    private List<LogEntry> logEntries;
    
    private DatabaseAccessProxy() {
        this.logEntries = new ArrayList<>();
    }
    
    // Implementação do Singleton
    public static synchronized DatabaseAccessProxy getInstance() {
        if (instance == null) {
            instance = new DatabaseAccessProxy();
        }
        return instance;
    }
    
    // Método para inicializar o serviço real, se necessário
    private void initializeRealService() {
        if (realDatabaseAccess == null) {
            realDatabaseAccess = new RealDatabaseAccess();
        }
    }
    
    // Método para registrar ações no log
    // Adiciona um novo log com a data atual, ação e SQL executado
    private void log(String action, String sql) {
        LogEntry entry = new LogEntry(new Date(), action, sql);
        logEntries.add(entry);
        System.out.println(entry); // Também imprime no console
    }
    
    // Novo método para acessar os logs armazenados
    public List<LogEntry> getLogs() {
        return new ArrayList<>(logEntries); // Retorna cópia para evitar modificações externas
    }
    
    // Novo método para limpar os logs
    public void clearLogs() {
        logEntries.clear();
    }
    
    @Override
    public void query(String sql) {
        log("QUERY", sql);
        initializeRealService();
        realDatabaseAccess.query(sql);
    }
    
    @Override
    public void update(String sql) {
        log("UPDATE", sql);
        initializeRealService();
        realDatabaseAccess.update(sql);
    }
    
    @Override
    public void delete(String sql) {
        log("DELETE", sql);
        initializeRealService();
        realDatabaseAccess.delete(sql);
    }
    
}
