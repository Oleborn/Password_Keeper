package oleborn.passwordkeeper.proccessfile.read;

import oleborn.passwordkeeper.model.AuthUser;
import oleborn.passwordkeeper.model.DataInFile;

public interface ReadFile {
    DataInFile parsing();
    void parsing(AuthUser authUser);
}
