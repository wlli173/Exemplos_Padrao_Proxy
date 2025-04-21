package padroesprojeto.proxy.exemplo3.proxy;

public class User {

    private String name;
    private boolean admin;
    private boolean readOnly;
    
    public User(String name, boolean admin, boolean readOnly) {
        this.name = name;
        this.admin = admin;
        this.readOnly = readOnly;
    }
    
    public String getName() { 
        return name; 
    }
    public boolean isAdmin() { 
        return admin; 
    }
    public boolean isReadOnly() { 
        return readOnly; 
    }

}
