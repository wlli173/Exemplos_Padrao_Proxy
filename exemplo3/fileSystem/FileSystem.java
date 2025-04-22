package exemplo3.fileSystem;

import java.util.List;

// Interface que define as operações do sistema de arquivos
// Esta interface será implementada pelo RealFileSystem e utilizada pelo proxy
public interface FileSystem {
    String readFile(String filename);
    void writeFile(String filename, String content);
    void deleteFile(String filename);
    List<String> listFiles();
}
