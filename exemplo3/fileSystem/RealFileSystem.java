package exemplo3.fileSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Implementação concreta do sistema de arquivos
// Esta classe representa o sistema de arquivos real que será acessado pelo proxy
public class RealFileSystem implements FileSystem {
    
    private Map<String, String> files = new HashMap<>();
    
    // Leitura de arquivos
    @Override
    public String readFile(String filename) {
        System.out.println("RealFileSystem: Lendo arquivo " + filename);
        return files.get(filename);
    }
    
    // Escrita de arquivos
    @Override
    public void writeFile(String filename, String content) {
        System.out.println("RealFileSystem: Escrevendo no arquivo " + filename);
        files.put(filename, content);
    }
    
    // Exclusão de arquivos
    @Override
    public void deleteFile(String filename) {
        System.out.println("RealFileSystem: Deletando arquivo " + filename);
        files.remove(filename);
    }
    
    // Listagem de arquivos
    @Override
    public List<String> listFiles() {
        System.out.println("RealFileSystem: Listando arquivos");
        return new ArrayList<>(files.keySet());
    }
    
}
