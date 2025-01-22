package oleborn.passwordkeeper.proccessfile.read;

import oleborn.passwordkeeper.model.AuthUser;
import oleborn.passwordkeeper.model.DataInFile;

public interface ReadFile {
    DataInFile parsing();
    DataInFile parsing(AuthUser authUser);
}
