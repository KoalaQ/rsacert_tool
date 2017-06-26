package util;

import sun.security.tools.KeyTool;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lyd on 2017-06-21.
 */
public class CertUtil {
    // 组装证书
    /**
     * CN拥有者名字 ，OU组织机构名， O组织名， L城市 ，ST州或省， C国家代码
     */
    private static String issuer = "C=CN,ST=SD,L=JN,O=JNSB,OU=JNSB,CN=JNSB";
    private static String algorithm = "RSA";


    private static boolean generateCert(String filePath, String pubkyename, String privatekeyname, String algorithm, int keysize,
                                     String alias, String storePwd, String keyPwd, String issuer, int effectime) throws Exception {
        //生成私钥
        StringBuffer genkeycmd = new StringBuffer();
        genkeycmd.append("keytool -genkey -v ");
        genkeycmd.append(" -alias ").append(alias);
        genkeycmd.append(" -keyalg  ").append(algorithm);
        genkeycmd.append(" -keysize ").append(keysize);
        genkeycmd.append(" -validity  ").append(effectime);
        genkeycmd.append(" -keystore  ").append(filePath+ File.separator+privatekeyname);
        genkeycmd.append(" -keypass ").append(keyPwd).append(" -storepass ").append(storePwd);
        genkeycmd.append(" -dname \"").append(issuer).append("\"");
        Process ps=Runtime.getRuntime().exec(genkeycmd.toString());
        //生成公钥
        final StringBuffer privatekeycmd = new StringBuffer();
        privatekeycmd.append(" keytool -export -alias ").append(alias);
        privatekeycmd.append(" -file ").append(filePath+ File.separator+pubkyename);
        privatekeycmd.append(" -keystore ").append(filePath+ File.separator+privatekeyname);
        privatekeycmd.append(" -storepass ").append(storePwd);
        Process ps2=null;
        if(ps.waitFor()==0){
            ps2=Runtime.getRuntime().exec(privatekeycmd.toString());
            if(ps2.waitFor()==0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }

    /**
     * 生成rsa证书。需要保证文件不存在，如果存在会导致生成失败
     * @param filePath  证书目录
     * @param pubkyename  公钥证书名称
     * @param privatekeyname  私钥证书名称
     * @param alias  别名
     * @param storePwd  存储密码
     * @param keyPwd  私钥密码
     * @param effecday   有效时长，天
     * @throws Exception
     */
    public static boolean generateCert(String filePath, String pubkyename, String privatekeyname,
                                    String alias, String storePwd, String keyPwd, int effecday) throws Exception {
        return generateCert(filePath,pubkyename,privatekeyname,algorithm,2048,
                alias,storePwd,keyPwd,issuer,effecday);
    }
    public static void main(String[] args) throws Exception {
        generateCert("d:\\ccert","pubkey2.cer","prikey2.cer",algorithm,1024,
                "test","123456","123456",issuer,365);

    }
}
