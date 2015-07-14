package ru.bingo.utils;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class ChiperUtils {

    private static String KEY = "a9[.pRj1";

    private static SecretKey secKey;
    private static Cipher encipher;
    private static Cipher decipher;

    static {
        try {
            secKey = new SecretKey() {
                @Override
                public String getAlgorithm() {
                    return "DES";
                }

                @Override
                public String getFormat() {
                    return "RAW";
                }

                @Override
                public byte[] getEncoded() {
                    return KEY.getBytes();
                }
            };
            encipher = Cipher.getInstance("DES");
            decipher = Cipher.getInstance("DES");
            encipher.init(Cipher.ENCRYPT_MODE, secKey);
            decipher.init(Cipher.DECRYPT_MODE, secKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String str) throws Exception {
        byte[] utf8 = str.getBytes("UTF8");
        byte[] enc = encipher.doFinal(utf8);
        return new sun.misc.BASE64Encoder().encode(enc);
    }

    public static String decrypt(String str) throws Exception {
        byte[] dec = new BASE64Decoder().decodeBuffer(str);
        byte[] utf8 = decipher.doFinal(dec);
        return new String(utf8, "UTF8");
    }

}
