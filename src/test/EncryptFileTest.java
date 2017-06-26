package test;

import util.FileUtil;
import util.GenerateRsaUtil;
import util.RSAEncryptUtil;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * Created by lyd on 2017-06-20.
 */
public class EncryptFileTest {
    public static void main(String[] args) throws Exception {
        String filepath="d:/tmp/";
        System.out.println("--------------公钥加密私钥解密base64过程-------------------");
        Map<String,String> bsee64Map= GenerateRsaUtil.getBase64RsaLicKey();
        String base64pubkey=bsee64Map.get(GenerateRsaUtil.PUBLIC_KEY);
        String base64privatekey=bsee64Map.get(GenerateRsaUtil.PRIVATE_KEY);
        System.err.println("公钥：");
        System.err.println(bsee64Map.get(GenerateRsaUtil.PUBLIC_KEY));
        System.err.println("私钥：");
        System.err.println(bsee64Map.get(GenerateRsaUtil.PRIVATE_KEY));
        FileUtil.writeFile(filepath,"base64private",base64privatekey);
        FileUtil.writeFile(filepath,"base64pub",base64pubkey);
        String filebase64pubkey=FileUtil.readFile(filepath,"base64pub");
        String filebase64privatekey=FileUtil.readFile(filepath,"base64private");

        String str="Hello World中文试试!";
        RSAPrivateKey privateKey= RSAEncryptUtil.loadBase64PrivateKeyByStr(filebase64privatekey);
        RSAPublicKey publicKey = RSAEncryptUtil.loadBase64PublicKeyByStr(filebase64pubkey);
        byte[] encryptstr= RSAEncryptUtil.encrypt(publicKey,str.getBytes());
        byte[] decordstr= RSAEncryptUtil.decrypt(privateKey,encryptstr);
        System.err.println("原文："+str);
        System.err.println("加密："+new String(encryptstr));
        System.err.println("解密："+new String(decordstr));


        System.out.println("--------------公钥加密私钥解密过程-------------------");
        Map<String,byte[]> keyMap= GenerateRsaUtil.getRsaLicKey();
        byte[] pubkey=keyMap.get(GenerateRsaUtil.PUBLIC_KEY);
        byte[] privatekey=keyMap.get(GenerateRsaUtil.PRIVATE_KEY);
        //pubkey=(new String(pubkey)).getBytes();
        System.err.println("公钥：");
        System.err.println(pubkey);
        System.err.println("私钥：");
        System.err.println(privatekey);
        FileUtil.writeFile(filepath,"private",privatekey);
        FileUtil.writeFile(filepath,"pub",pubkey);
        byte[] filepubkey=FileUtil.readFileByte(filepath,"pub");
        byte[] fileprivatekey=FileUtil.readFileByte(filepath,"private");

         str="Hello World中文试试!";
         privateKey= RSAEncryptUtil.loadPrivateKeyByStr(fileprivatekey);
         publicKey = RSAEncryptUtil.loadPublicKeyByStr(filepubkey);
         encryptstr= RSAEncryptUtil.encrypt(publicKey,str.getBytes());
         decordstr= RSAEncryptUtil.decrypt(privateKey,encryptstr);
        System.err.println("原文："+str);
        System.err.println("加密："+new String(encryptstr));
        System.err.println("解密："+new String(decordstr));
    }
}
