package com.strongduanmu;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.word.WordFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

@SuppressWarnings("unused")
public final class SM4CryptographicNativeLibrary {
    
    static {
        // 注册 BouncyCastle
        Security.addProvider(new BouncyCastleProvider());
    }
    
    @SneakyThrows
    @CEntryPoint(name = "sm4_encrypt")
    private static CCharPointer sm4Encrypt(final IsolateThread thread, final CCharPointer plainValue, final CCharPointer cipherValue) {
        String encryptValue = Hex.encodeHexString(getCipher(Cipher.ENCRYPT_MODE).doFinal(CTypeConversion.toJavaString(plainValue).getBytes()));
        // C 语言中会在字符串结尾自动添加 \0 转义符，所以这里需要 +1
        int encryptValueLength = encryptValue.getBytes().length + 1;
        CTypeConversion.toCString(encryptValue, cipherValue, WordFactory.unsigned(encryptValueLength));
        return cipherValue;
    }
    
    @SneakyThrows
    @CEntryPoint(name = "sm4_decrypt")
    private static CCharPointer sm4Decrypt(final IsolateThread thread, final CCharPointer cipherValue, final CCharPointer plainValue) {
        String decryptValue = new String(getCipher(Cipher.DECRYPT_MODE).doFinal(Hex.decodeHex(CTypeConversion.toJavaString(cipherValue))));
        // C 语言中会在字符串结尾自动添加 \0 转义符，所以这里需要 +1
        int decryptValueLength = decryptValue.getBytes().length + 1;
        CTypeConversion.toCString(decryptValue, plainValue, WordFactory.unsigned(decryptValueLength));
        return plainValue;
    }
    
    @SneakyThrows
    private static Cipher getCipher(final int cipherMode) {
        Cipher result = Cipher.getInstance("SM4/ECB/PKCS5Padding", BouncyCastleProvider.PROVIDER_NAME);
        result.init(cipherMode, new SecretKeySpec(Hex.decodeHex("4D744E003D713D054E7E407C350E447E"), "SM4"));
        return result;
    }
}
