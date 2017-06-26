package util;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyd on 2017-06-20.
 */
public class GenerateRsaUtil {
    private static final String KEY_ALGORITHM = "RSA";
    //public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";
    //获得公钥
    public static Map<String,byte[]> getRsaLicKey(int keysize) throws Exception {
        Map<String,byte[]> retMap=new HashMap<String,byte[]>();
        Map<String,Object> keyMap=initKey(keysize);
        Key pubkey = (Key) keyMap.get(PUBLIC_KEY);
        Key privatekey = (Key) keyMap.get(PRIVATE_KEY);
        retMap.put(PUBLIC_KEY,pubkey.getEncoded());
        retMap.put(PRIVATE_KEY,privatekey.getEncoded());
        return retMap;
    }
    public static Map<String,byte[]> getRsaLicKey() throws Exception {
        return getRsaLicKey(1024);
    }
    //获得公钥
    public static Map<String,String> getBase64RsaLicKey(int keysize) throws Exception {
        Map<String,String> retMap=new HashMap<String,String>();
        Map<String,Object> keyMap=initKey(keysize);
        Key pubkey = (Key) keyMap.get(PUBLIC_KEY);
        Key privatekey = (Key) keyMap.get(PRIVATE_KEY);
        retMap.put(PUBLIC_KEY,BASE64Util.encryptBASE64(pubkey.getEncoded()));
        retMap.put(PRIVATE_KEY,BASE64Util.encryptBASE64(privatekey.getEncoded()));
        return retMap;
        //return encryptBASE64(key.getEncoded());
    }
    public static Map<String,String> getBase64RsaLicKey() throws Exception {
        return getBase64RsaLicKey(1024);
        //return encryptBASE64(key.getEncoded());
    }
    //map对象中存放公私钥
    private static Map<String, Object> initKey(int keysize) throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(keysize);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
}
