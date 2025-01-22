package oleborn.passwordkeeper.proccessfile;

import jakarta.annotation.Resource;
import oleborn.passwordkeeper.model.AuthUser;
import oleborn.passwordkeeper.model.DataInFile;
import oleborn.passwordkeeper.proccessfile.read.ReadFile;
import oleborn.passwordkeeper.proccessfile.write.WriteFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static oleborn.passwordkeeper.util.UtilsEnum.FILEPATH;

@Component
public class ProcessingFileImpl implements ProcessingFile {

    @Resource
    private WriteFile writeFile;

    @Resource
    private ReadFile readFile;

    @Override
    public void createFile(String name) {
        File file = new File(name);
        try {
            boolean newFile = file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DataInFile readFile(String name) {
        return readFile.parsing();
    }

    @Override
    public DataInFile readFileNoSession(AuthUser authUser) {
        return readFile.parsing(authUser);
    }

    @Override
    public void updateFile(DataInFile dataInFile) {
        writeFile.writeToFile(dataInFile);
    }

    @Override
    public void deleteFile(String name) {

    }

    @Override
    public boolean ensureDirectoryExists(String name) {
        try {
            // Формируем путь к файлу (уже включает имя файла и расширение)
            Path filePath = Paths.get(FILEPATH.getString().formatted(name));

            // Получаем путь к родительской директории
            // Создаем директорию, если её нет
            Files.createDirectories(filePath.getParent());

            // Создаем файл, если его нет
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании директории или файла", e);
        }
        return false;
    }

    @Override
    public void saveFile(String directory, String fileName, byte[] data) {
        ensureDirectoryExists(fileName);
        Path filePath = Paths.get(directory, fileName);
        try {
            Files.write(filePath, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
