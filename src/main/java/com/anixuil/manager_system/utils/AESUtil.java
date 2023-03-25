package com.anixuil.manager_system.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AESUtil {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    // appKey,每隔一段时间进行替换即可
   // 可以设计成保存到数据库中或者那里，然后进行每隔一段时间进行替换，增加保密的安全性
    private static final String appKey = "fa8f92af-fa83-443a-9626-e32b64481320";

    public static String encrypt(String content, String appKey) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE,getSecretKey(appKey));  //初始化为加密模式
            byte[] result = cipher.doFinal(byteContent);    //加密
            return Base64.encodeBase64String(result);   //通过base64返回
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static SecretKeySpec getSecretKey(String appKey) {
        // 返回生成指定算法密钥生成器的keygenerator对象
        KeyGenerator keyGenerator = null;

        try{
            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(appKey.getBytes());

            //AES要求密钥长度为128
            keyGenerator.init(128,secureRandom);

            //生成一个密钥
            SecretKey secretKey = keyGenerator.generateKey();

            return new SecretKeySpec(secretKey.getEncoded(),KEY_ALGORITHM);     //转换为aes专用密钥
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //解密
    public static String decrypt(String content,String appKey){
        try{
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            //使用密钥初始化,设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE,getSecretKey(appKey));
            //执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));

            return new String(result,"utf-8");
        }catch (Exception e){
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE,null,e);
        }
        return null;
    }

}

