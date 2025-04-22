package exemplo2.bdLib;

// Classe que implementa a interface DatabaseAccess
// Esta classe simula o acesso a um banco de dados real
public class RealDatabaseAccess implements DatabaseAccess {

    public RealDatabaseAccess() {
        System.out.println("RealDatabaseAccess: Carregando driver e estabelecendo conexão...");
        // Simula um processo pesado de inicialização
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void query(String sql) {
        System.out.println("Executing query: " + sql);
    }

    @Override
    public void update(String sql) {
        System.out.println("Executing update: " + sql);
    }

    @Override
    public void delete(String sql) {
        System.out.println("Executing delete: " + sql);
    }
    
}
