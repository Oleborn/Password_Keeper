package oleborn.passwordkeeper.auth;

import jakarta.annotation.Resource;
import oleborn.passwordkeeper.model.AuthUser;
import oleborn.passwordkeeper.proccessfile.ProcessingFile;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private ProcessingFile processingFile;

    @Override
    public void authUser(AuthUser user) {
        processingFile.readFileNoSession(user);
    }
}
