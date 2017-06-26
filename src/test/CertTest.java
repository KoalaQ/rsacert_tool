package test;

import util.SignatureUtils;

import java.io.File;

/**
 * Created by lyd on 2017-06-21.
 */
public class CertTest {
    public static void main(String[] args) throws Exception {
        String str="测试字符123hhh";
        String singstr= SignatureUtils.signature(str,"d:"+ File.separator+"ccert"+ File.separator+"resp"+File.separator+"prikey.cer",
                "123456","123456");

        boolean verify=SignatureUtils.verify(str,singstr,
                "d:"+ File.separator+"ccert"+ File.separator+"resp"+File.separator+"pubkey.cer");

        System.out.println("源字符串："+str);
        System.out.println("签名串："+singstr);
        System.out.println("结果："+verify);
    }
}
