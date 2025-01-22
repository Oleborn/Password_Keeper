package oleborn.passwordkeeper.proccessfile.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import oleborn.passwordkeeper.encoding.EncodingService;
import oleborn.passwordkeeper.model.DataInFile;
import oleborn.passwordkeeper.model.UserSession;
import oleborn.passwordkeeper.util.UtilsEnum;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class WriteFileImpl implements WriteFile {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private EncodingService encodingService;

    @Resource
    private UserSession userSession;

    @Override
    public void writeToFile(DataInFile dataInFile) {
        try {
            // Сериализуем объект в JSON
            String json = objectMapper.writeValueAsString(dataInFile);
            // Шифруем JSON-строку
            String encryptedData = encodingService.encrypt(json, userSession.getUser().getUserKey());
            // Записываем зашифрованные данные в файл
            Files.write(Paths.get(
                    UtilsEnum.FILEPATH.getString().formatted(userSession.getUser().getName())),
                    encryptedData.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при записи файла", e);
        }
    }
}
