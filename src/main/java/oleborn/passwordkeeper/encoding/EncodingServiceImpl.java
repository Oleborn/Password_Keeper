package oleborn.passwordkeeper.encoding;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Service
public class EncodingServiceImpl implements EncodingService {

    // Константа, указывающая алгоритм шифрования (AES)
    private static final String ALGORITHM = "AES";
    // Константа, указывающая режим преобразования (в данном случае просто AES)
    private static final String TRANSFORMATION = "AES";

    @Override
    public SecretKey generateKeyFromPassword(String password) {
        SecretKey key = null;
        try {
            // Создаем объект MessageDigest для алгоритма SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Хешируем пароль (строку) в массив байтов
            byte[] keyBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            // Обрезаем массив байтов до нужного размера (128 бит = 16 байт для AES-128)
            keyBytes = Arrays.copyOf(keyBytes, 16); // 16 байт = 128 бит
            // Создаем и возвращаем SecretKeySpec на основе хешированных байтов
            key = new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    @Override
    public String encrypt(String data, SecretKey key) {
        String result = null;

        try {
            // Создаем объект Cipher для алгоритма AES
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // Инициализируем Cipher в режиме шифрования с указанным ключом
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // Шифруем данные (преобразуем строку в байты и шифруем)
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            // Кодируем зашифрованные байты в строку Base64 и возвращаем результат
            result = Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String decrypt(String encryptedData, SecretKey key) {
        String result = null;

        try {
            // Создаем объект Cipher для алгоритма AES
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // Инициализируем Cipher в режиме дешифрования с указанным ключом
            cipher.init(Cipher.DECRYPT_MODE, key);
            // Декодируем строку Base64 в байты и дешифруем их
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            // Преобразуем дешифрованные байты в строку и возвращаем результат\
            result = new String(decryptedBytes);
        }
        catch (Exception e) {
            throw new RuntimeException("Вы использовали неверный пароль");
        }
        return result;
    }
}
