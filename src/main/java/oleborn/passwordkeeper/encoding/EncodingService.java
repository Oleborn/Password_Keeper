package oleborn.passwordkeeper.encoding;

import javax.crypto.SecretKey;

public interface EncodingService {
    SecretKey generateKeyFromPassword(String password);
    String encrypt(String data, SecretKey key);
    String decrypt(String encryptedData, SecretKey key);

}
