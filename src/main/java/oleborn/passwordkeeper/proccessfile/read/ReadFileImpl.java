package oleborn.passwordkeeper.proccessfile.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import oleborn.passwordkeeper.encoding.EncodingService;
import oleborn.passwordkeeper.model.AuthUser;
import oleborn.passwordkeeper.model.DataInFile;
import oleborn.passwordkeeper.model.UserSession;
import oleborn.passwordkeeper.util.UtilsEnum;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ReadFileImpl implements ReadFile {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private EncodingService encodingService;

    @Resource
    private UserSession userSession;

    @Override
    public DataInFile parsing() {
        try {
            // Читаем зашифрованные данные из файла
            byte[] encryptedBytes = Files.readAllBytes(Paths.get(UtilsEnum.FILEPATH.getString().formatted(userSession.getUser().getName())));
            String encryptedData = new String(encryptedBytes);
            // Дешифруем данные
            String decryptedData = encodingService.decrypt(encryptedData, userSession.getUser().getUserKey());
            // Десериализуем JSON-строку в объект DataInFile
            return objectMapper.readValue(decryptedData, DataInFile.class);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при чтении файла", e);
        }
    }

    @Override
    public DataInFile parsing(AuthUser authUser) {
        try {
            // Читаем зашифрованные данные из файла
            byte[] encryptedBytes = Files.readAllBytes(Paths.get(UtilsEnum.FILEPATH.getString().formatted(authUser.getName())));
            String encryptedData = new String(encryptedBytes);
            // Дешифруем данные
            String decryptedData = encodingService.decrypt(encryptedData,
                    encodingService.generateKeyFromPassword(authUser.getUserKey())
            );
            // Десериализуем JSON-строку в объект DataInFile
            return objectMapper.readValue(decryptedData, DataInFile.class);
        } catch (Exception e) {
            throw new RuntimeException("Вы использовали неверный пароль", e);
        }
    }
}
