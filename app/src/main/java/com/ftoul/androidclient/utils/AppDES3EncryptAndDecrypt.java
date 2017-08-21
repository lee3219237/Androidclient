package com.ftoul.androidclient.utils;




import android.util.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 *
 * @Title   报文体中的数据用3DES加密和base64处理
 * @Author 覃忠君 on 2016/11/5.
 * @Copyright  蜂投网
 */
public class AppDES3EncryptAndDecrypt {
    private static final boolean tag = true;

    private static final String ALGORITHM = "DESede";
    private static final String DES3_KEY = "A1B2C3D4E5F6G7H8I9J0K1L2";

    /**
     * 数据加密
     * @param src 明文
     * @return 密文
     * @throws Exception
     */
//    public static String DES3EncryptMode(String src) throws Exception {
//        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
//        return new String(Base64.encodeBase64(cipher.doFinal(src.getBytes("UTF-8"))));
//    }

    public static String DES3EncryptMode(String src) throws Exception {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        return new String(Base64.encode(cipher.doFinal(src.getBytes("UTF-8")),Base64.NO_WRAP));
    }

    /**
     * 数据解密
     * @param src 密文
     * @return 明文
     * @throws Exception
     */
//    public static String DES3DecryptMode(String src) throws Exception {
//        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
//        byte [] dd=cipher.doFinal(Base64.decodeBase64(src.getBytes("UTF-8")));
//        return new String(dd,"UTF-8");
//    }
    public static String DES3DecryptMode(String src) throws Exception {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
        byte [] dd=cipher.doFinal(Base64.decode(src.getBytes("UTF-8"),Base64.NO_WRAP));
        return new String(dd,"UTF-8");
    }

    private static Cipher getCipher(int encryptMode) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        DESedeKeySpec desKeySpec = new DESedeKeySpec(DES3_KEY.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        cipher.init(encryptMode, secretKey);
        return cipher;
    }
//    /**
//     * 数据加密
//     * @param src
//     * @return
//     * @throws Exception
//     */
//    public static String encryptDES3Mode(String src) {
//        if(tag==false){ //非生产环境
//            return src;
//        }
//        Cipher cipher = null;
//        try {
//            cipher = getCipher(Cipher.ENCRYPT_MODE);
//            return new String(Base64.encode(cipher.doFinal(src.getBytes("UTF-8")),Base64.NO_WRAP));
//        } catch (Exception e) {
//            android.util.Log.e("decryptDES3Mode 数据解密异常！",e.getMessage());
//        }
//        return src;
//    }
//
//    private static Cipher getCipher(int encryptMode) throws Exception {
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        DESedeKeySpec desKeySpec = new DESedeKeySpec(DES3_KEY.getBytes());
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
//        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
//        cipher.init(encryptMode, secretKey);
//        return cipher;
//    }
//
//    /**
//     * 数据解密
//     * @param src
//     * @return
//     * @throws Exception
//     */
//    public static String  decryptDES3Mode(String src) {
//        if(tag==false){ //非生产环境
//            return src;
//        }
//        Cipher cipher = null;
//        try {
//            cipher = getCipher(Cipher.DECRYPT_MODE);
//            return new String(Base64.encode(cipher.doFinal(src.getBytes("UTF-8")),Base64.NO_WRAP));
//        } catch (Exception e) {
//            android.util.Log.e("decryptDES3Mode 数据解密异常！",e.getMessage());
//        }
//        return src;
//    }
}
