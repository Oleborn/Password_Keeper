package oleborn.passwordkeeper.proccessfile.write;

import oleborn.passwordkeeper.model.DataInFile;

import java.nio.file.Path;

public interface WriteFile {
    void writeToFile(DataInFile dataInFile);
}
