package com.strongduanmu;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public final class SM4CryptographicDemo {
    
    @SneakyThrows
    public static void main(String[] args) {
        // 注册 BouncyCastle
        Security.addProvider(new BouncyCastleProvider());
        byte[] securityKey = Hex.decodeHex("4D744E003D713D054E7E407C350E447E");
        String originalValue = "GraalVM SM4 Test.";
        System.out.println("Original Value: " + originalValue);
        // 加密处理
        Cipher encryptCipher = Cipher.getInstance("SM4/ECB/PKCS5Padding", BouncyCastleProvider.PROVIDER_NAME);
        encryptCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(securityKey, "SM4"));
        String encryptValue = Hex.encodeHexString((encryptCipher.doFinal(originalValue.getBytes())));
        System.out.println("Encrypt Value: " + encryptValue);
        // 解密处理
        Cipher decryptCipher = Cipher.getInstance("SM4/ECB/PKCS5Padding", BouncyCastleProvider.PROVIDER_NAME);
        decryptCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(securityKey, "SM4"));
        String decryptValue = new String((decryptCipher.doFinal(Hex.decodeHex(encryptValue))));
        System.out.println("Decrypt Value: " + decryptValue);
    }
}
