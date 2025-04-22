package exemplo2.bdLib;

public interface DatabaseAccess {
    void query(String sql);
    void update(String sql);
    void delete(String sql);
}
