package test;

import util.GenerateRsaUtil;
import util.RSAEncryptUtil;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * 加密测试
 */
public class EncryptTest {

    public static void main(String[] args) throws Exception {
       Map<String,String> bsee64Map= GenerateRsaUtil.getBase64RsaLicKey();
        String base64pubkey=bsee64Map.get(GenerateRsaUtil.PUBLIC_KEY);
        String base64privatekey=bsee64Map.get(GenerateRsaUtil.PRIVATE_KEY);
       System.err.println("公钥：");
       System.err.println(bsee64Map.get(GenerateRsaUtil.PUBLIC_KEY));
       System.err.println("私钥：");
       System.err.println(bsee64Map.get(GenerateRsaUtil.PRIVATE_KEY));

       Map<String,byte[]> keyMap= GenerateRsaUtil.getRsaLicKey();
        byte[] pubkey=keyMap.get(GenerateRsaUtil.PUBLIC_KEY);
        byte[] privatekey=keyMap.get(GenerateRsaUtil.PRIVATE_KEY);
        //pubkey=(new String(pubkey)).getBytes();
        System.err.println("公钥：");
        System.err.println(pubkey);
        System.err.println("私钥：");
        System.err.println(privatekey);

        String str="Hello World中文试试!";
        System.err.println("--------------公钥加密私钥解密过程-------------------");
        RSAPrivateKey privateKey= RSAEncryptUtil.loadPrivateKeyByStr(privatekey);
        RSAPublicKey publicKey = RSAEncryptUtil.loadPublicKeyByStr(pubkey);
        byte[] encryptstr= RSAEncryptUtil.encrypt(publicKey,str.getBytes());
        byte[] decordstr= RSAEncryptUtil.decrypt(privateKey,encryptstr);
        System.err.println("原文："+str);
        System.err.println("加密："+new String(encryptstr));
        System.err.println("解密："+new String(decordstr));

        System.err.println("--------------公钥加密私钥解密base64过程-------------------");
         privateKey= RSAEncryptUtil.loadBase64PrivateKeyByStr(base64privatekey);
         publicKey = RSAEncryptUtil.loadBase64PublicKeyByStr(base64pubkey);
         encryptstr= RSAEncryptUtil.encrypt(publicKey,str.getBytes());
         decordstr= RSAEncryptUtil.decrypt(privateKey,encryptstr);
        System.err.println("原文："+str);
        System.err.println("加密："+new String(encryptstr));
        System.err.println("解密："+new String(decordstr));

        System.err.println("--------------私钥加密公钥解密过程-------------------");
        str="Hello World2中文啦22!";
         privateKey= RSAEncryptUtil.loadPrivateKeyByStr(privatekey);
         publicKey = RSAEncryptUtil.loadPublicKeyByStr(pubkey);
         encryptstr= RSAEncryptUtil.encrypt(privateKey,str.getBytes());
         decordstr= RSAEncryptUtil.decrypt(publicKey,encryptstr);
        System.err.println("原文："+str);
        System.err.println("加密："+new String(encryptstr));
        System.err.println("解密："+new String(decordstr));

        System.err.println("--------------私钥加密公钥解密base64过程-------------------");
        privateKey= RSAEncryptUtil.loadBase64PrivateKeyByStr(base64privatekey);
        publicKey = RSAEncryptUtil.loadBase64PublicKeyByStr(base64pubkey);
        encryptstr= RSAEncryptUtil.encrypt(privateKey,str.getBytes());
        decordstr= RSAEncryptUtil.decrypt(publicKey,encryptstr);
        System.err.println("原文："+str);
        System.err.println("加密："+new String(encryptstr));
        System.err.println("解密："+new String(decordstr));
    }
}
