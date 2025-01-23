package oleborn.passwordkeeper.proccessfile;

import oleborn.passwordkeeper.model.AuthUser;
import oleborn.passwordkeeper.model.DataInFile;

import java.nio.file.Path;

public interface ProcessingFile {
    void createFile(String name);
    DataInFile readFile(String name);
    void readFileNoSession(AuthUser authUser);
    void updateFile(DataInFile dataInFile);
    void deleteFile(String name);
    boolean ensureDirectoryExists(String name);

    void saveFile(String directory, String fileName, byte[] data);
}
