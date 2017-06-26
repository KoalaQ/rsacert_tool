package test;

import util.FileUtil;
import util.GenerateRsaUtil;
import util.RSASignatureUtil;

import java.util.Map;

/**
 * Created by lyd on 2017-06-20.
 */
public class SignatureFileTest {

    public static void main(String[] args) throws Exception {
        String filepath="d:/tmp/";








        String str="原字符串";
        System.err.println("--------------base64签名过程-------------------");
        Map<String,String> bsee64Map= GenerateRsaUtil.getBase64RsaLicKey();
        String base64pubkey=bsee64Map.get(GenerateRsaUtil.PUBLIC_KEY);
        String base64privatekey=bsee64Map.get(GenerateRsaUtil.PRIVATE_KEY);
        System.err.println("公钥：");
        System.err.println(bsee64Map.get(GenerateRsaUtil.PUBLIC_KEY));
        System.err.println("私钥：");
        System.err.println(bsee64Map.get(GenerateRsaUtil.PRIVATE_KEY));


        FileUtil.writeFile(filepath,"base64privatesign",base64privatekey);
        FileUtil.writeFile(filepath,"base64pubsign",base64pubkey);
        String filebase64pubkey=FileUtil.readFile(filepath,"base64pubsign");
        String filebase64privatekey=FileUtil.readFile(filepath,"base64privatesign");

        String signstr= RSASignatureUtil.signBase64(str,filebase64privatekey, RSASignatureUtil.SIGN_ALGORITHMS_MD5);
        System.err.println("签名原串："+str);
        System.err.println("签名串："+signstr);
        System.err.println("验签结果："+ RSASignatureUtil.doCheckBase64(str,signstr,filebase64pubkey, RSASignatureUtil.SIGN_ALGORITHMS_MD5));


        System.err.println("--------------签名过程-------------------");
        Map<String,byte[]> keyMap= GenerateRsaUtil.getRsaLicKey();
        byte[] pubkey=keyMap.get(GenerateRsaUtil.PUBLIC_KEY);
        byte[] privatekey=keyMap.get(GenerateRsaUtil.PRIVATE_KEY);
        //pubkey=(new String(pubkey)).getBytes();
        System.err.println("公钥：");
        System.err.println(pubkey);
        System.err.println("私钥：");
        System.err.println(privatekey);

        FileUtil.writeFile(filepath,"privatesign",privatekey);
        FileUtil.writeFile(filepath,"pubsign",pubkey);
        byte[] filepubkey=FileUtil.readFileByte(filepath,"pubsign");
        byte[] fileprivatekey=FileUtil.readFileByte(filepath,"privatesign");


         byte[] signbyte= RSASignatureUtil.sign(str,fileprivatekey, RSASignatureUtil.SIGN_ALGORITHMS_MD5);
        System.err.println("签名原串："+str);
        System.err.println("签名串："+signbyte);
         str="原字符串2";
        System.err.println("验签结果："+ RSASignatureUtil.doCheck(str,signbyte,filepubkey, RSASignatureUtil.SIGN_ALGORITHMS_MD5));

    }
}
