package test;

import util.GenerateRsaUtil;
import util.RSASignatureUtil;

import java.util.Map;

/**
 * Created by lyd on 2017-06-20.
 */
public class SignatureTest {

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

        String str="原字符串";
        System.err.println("--------------base64签名过程-------------------");
        String signstr= RSASignatureUtil.signBase64(str,base64privatekey, RSASignatureUtil.SIGN_ALGORITHMS_SHA256WithRSA);
        System.err.println("签名原串："+str);
        System.err.println("签名串："+signstr);
        System.err.println("验签结果："+ RSASignatureUtil.doCheckBase64(str,signstr,base64pubkey, RSASignatureUtil.SIGN_ALGORITHMS_SHA256WithRSA));

        System.err.println("--------------签名过程-------------------");
         byte[] signbyte= RSASignatureUtil.sign(str,privatekey, RSASignatureUtil.SIGN_ALGORITHMS_SHA256WithRSA);
        System.err.println("签名原串："+str);
        System.err.println("签名串："+signbyte);
         str="原字符串2";
        System.err.println("验签结果："+ RSASignatureUtil.doCheck(str,signbyte,pubkey, RSASignatureUtil.SIGN_ALGORITHMS_SHA256WithRSA));

    }
}
