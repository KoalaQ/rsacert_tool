package util;



import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.*;

/**
 * 功能说明: 签名工具类<br>
 * 开发时间: 2017/3/16<br>
 * <br>
 */
public class SignatureUtils {
    private static String CERTIFICATEFACTORYTYPES="X.509";
    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS_SHA256WithRSA = "SHA256WithRSA";
    public static final String KEYSTORETYPES = "jks";


    /**
     * 用于给待签名或者验签的信息进行ascii码从小到大的排序并生成指定格式的字符串key1=value1&key2=value2
     * 注意key的排序无视大小写
     * @param mapPars：参数列表
     * @param excludeBlankValue：是否跳过空值
     * @return key1=value1&key2=value2，全部转换为大写
     */
    public static String sortParamForSign(Map<String, String> mapPars, boolean excludeBlankValue){
        TreeMap<String, String> param = new TreeMap<String, String>(new Comparator<String>(){
            //无视key的大小写进行比较
            @Override
            public int compare(String key1, String key2) {
                return key1.compareToIgnoreCase(key2);
            }
        });
        param.putAll(mapPars);
        StringBuffer result = new StringBuffer();
        for(Map.Entry<String,String> entry : param.entrySet()){
            if(result.length() > 0){
                result.append("&");
            }
            if(isNullOrEmpty(entry.getValue())){
                if(excludeBlankValue){
                    continue;
                } else{
                    entry.setValue("");
                }
            }
            result.append(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
        return result.toString().toUpperCase();
    }
    public static String sortParamForSign(Map<String, String> mapPars){
        return sortParamForSign(mapPars, false);
    }

    /**
     * 字节数组转换成字符串，直接用new String会乱码
     */
    public static String bytesToHex(byte[] bytes) {
        final String hexStr = "0123456789ABCDEF";
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(hexStr.charAt((b >> 4) & 0x0f));
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(hexStr.charAt(b & 0x0f));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 十六进制字符串转换成字节数组
     */
    public static byte[] hexToBytes(String hexString){
        final String hexStr = "0123456789ABCDEF";
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length()/2;
        byte[] bytes = new byte[len];
        byte high = 0;//字节高四位
        byte low = 0;//字节低四位
        for(int i=0;i<len;i++){
            //右移四位得到高位
            high = (byte)((hexStr.indexOf(hexString.charAt(2*i)))<<4);
            low = (byte)hexStr.indexOf(hexString.charAt(2*i+1));
            bytes[i] = (byte) (high|low);//高地位做或运算
        }
        return bytes;
    }



    /**
     * 获取公钥和私钥
     * @param keysize：秘钥长度
     * @param algorithm: 生成秘钥的算法，默认使用RSA，合法的算法名称请参考JDK文档
     *                  <a href="{@docRoot}/../technotes/guides/security/StandardNames.html#KeyPairGenerator">
     *                  也可使用
     */
    public static KeyPair getKeyPair(int keysize, String algorithm){
        KeyPair keyPair = null;
        try {
            //实例化一个公钥/私钥对生成器
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
            //设置公钥/私钥对的长度
            keyGen.initialize(keysize);
            //生成一个RSA算法的公钥/私钥
            keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyPair;
    }
    public static KeyPair getKeyPair(int keysize){
        return getKeyPair(keysize, CERTIFICATEFACTORYTYPES);
    }
    public static KeyPair getKeyPair() {
        return getKeyPair(2048);
    }


    /**
     * 对原文进行签名
     * @param text：原文
     * @param privateKey：私钥
     * @param algorithm：签名使用的算法，默认使用SHA256WithRSA，合法的算法名称请参考JDK文档
     *                  <a href="{@docRoot}/../technotes/guides/security/StandardNames.html#Signature">
     *                   也可使用{@link }
     * @return byte数组，若要转换成字符串必须用{@link SignatureUtils#bytesToHex(byte[])}，否则会乱码
     */
    public static byte[] signature(byte[] text, PrivateKey privateKey, String algorithm) throws Exception {
        byte[] result = null;
        Signature sign = Signature.getInstance(algorithm);
        sign.initSign(privateKey);
        sign.update(text);
        result = sign.sign();
        return result;
    }
    public static byte[] signature(byte[] text, PrivateKey privateKey) throws Exception {
        return signature(text, privateKey, SIGN_ALGORITHMS_SHA256WithRSA);
    }
    public static String signature(String text, PrivateKey privateKey) throws Exception {
        return bytesToHex(signature(text.getBytes(), privateKey, SIGN_ALGORITHMS_SHA256WithRSA));
    }
    public static String signature(String text,String filePath, String storePwd, String keyPwd) throws Exception {
        PrivateKey privateKey=getPrivateKeyFromAbsolutePath(filePath,storePwd,keyPwd);
        return bytesToHex(signature(text.getBytes("utf-8"), privateKey, SIGN_ALGORITHMS_SHA256WithRSA));
    }
    /**
     * @param text：原文
     * @param signature：签名后的字节数组
     * @param publicKey：公钥
     * @param algorithm：签名使用的算法，默认使用SHA256WithRSA，合法的算法名称请参考JDK文档
     *                  <a href="{@docRoot}/../technotes/guides/security/StandardNames.html#Signature">
     *                   也可使用{@link }
     * @return true：验签成功；false：验签失败
     */
    public static boolean verify(byte[] text, byte[] signature, PublicKey publicKey, String algorithm) throws Exception {
        boolean result = false;
        Signature sign = Signature.getInstance(algorithm);
        sign.initVerify(publicKey);
        sign.update(text);
        result = sign.verify(signature);

        return result;
    }
    public static boolean verify(byte[] text, byte[] signature, PublicKey publicKey) throws Exception {
        return verify(text, signature, publicKey, SIGN_ALGORITHMS_SHA256WithRSA);
    }
    public static boolean verify(String text, String signature, PublicKey publicKey) throws Exception {
        return verify(text.getBytes(), hexToBytes(signature), publicKey, SIGN_ALGORITHMS_SHA256WithRSA);
    }
    public static boolean verify(String text, String signature,String filePath) throws Exception {
        PublicKey publicKey=getPublicKeyFromAbsolutePath(filePath);
        return verify(text.getBytes("utf-8"), hexToBytes(signature), publicKey, SIGN_ALGORITHMS_SHA256WithRSA);
    }
    /**
     * 根据文件获取私钥
     * @param filePath：从盘符开始的绝对路径
     * @param storePwd：storepass
     * @param keyPwd：keypass
     * @param algorithm：keystore类型，参考{@link }
     */
    public static PrivateKey getPrivateKeyFromAbsolutePath(String filePath, String storePwd, String keyPwd, String algorithm) throws Exception {
        PrivateKey privateKey = null;

        KeyStore keyStore = KeyStore.getInstance(algorithm);
        FileInputStream inputFile = new FileInputStream(filePath);
        keyStore.load(inputFile, storePwd.toCharArray());
        inputFile.close();
        Enumeration elements = keyStore.aliases();
        String element = (String) elements.nextElement();
        privateKey = (PrivateKey)keyStore.getKey(element, keyPwd.toCharArray());
        return privateKey;
    }
    public static PrivateKey getPrivateKeyFromAbsolutePath(String filePath, String storePwd, String keyPwd) throws Exception {
        return getPrivateKeyFromAbsolutePath(filePath, storePwd, keyPwd, KEYSTORETYPES);
    }

    /**
     * 根据文件获取私钥
     * @param filePath：文件在resources下的路径，如/KeyStore/cpcn/jnsb.keystore
     * @param storePwd：storepass
     * @param keyPwd：keypass
     * @param algorithm：keystore类型，参考{@link }
     */
    public static PrivateKey getPrivateKeyFromResource(String filePath, String storePwd, String keyPwd, String algorithm)throws Exception{
        PrivateKey privateKey = null;

        KeyStore keyStore = KeyStore.getInstance(algorithm);
        InputStream inputFile = SignatureUtils.class.getClassLoader().getResourceAsStream(filePath);
        keyStore.load(inputFile, storePwd.toCharArray());
        inputFile.close();
        Enumeration elements = keyStore.aliases();
        String element = (String) elements.nextElement();
        privateKey = (PrivateKey)keyStore.getKey(element, keyPwd.toCharArray());

        return privateKey;
    }
    public static PrivateKey getPrivateKeyFromResource(String filePath, String storePwd, String keyPwd) throws Exception {
        return getPrivateKeyFromResource(filePath, storePwd, keyPwd, KEYSTORETYPES);
    }

    /**
     * 根据证书获取公钥
     * @param filePath：从盘符开始的绝对路径
     */
    public static PublicKey getPublicKeyFromAbsolutePath(String filePath){
        PublicKey publicKey = null;
        try{
            CertificateFactory cf = CertificateFactory.getInstance(CERTIFICATEFACTORYTYPES);
            FileInputStream in = new FileInputStream(filePath);
            Certificate c = cf.generateCertificate(in);
            publicKey = c.getPublicKey();
        } catch(Exception e){
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * 根据证书获取公钥
     * @param filePath：文件在resources下的路径，如/KeyStore/cpcn/jnsb.crt
     */
    public static PublicKey getPublicKeyFromResource(String filePath){
        PublicKey publicKey = null;
        try{
            CertificateFactory cf = CertificateFactory.getInstance(CERTIFICATEFACTORYTYPES);
            InputStream inputFile = SignatureUtils.class.getClassLoader().getResourceAsStream(filePath);
            Certificate c = cf.generateCertificate(inputFile);
            publicKey = c.getPublicKey();
        } catch(Exception e){
            e.printStackTrace();
        }
        return publicKey;
    }


    /**
     * 检测字符串是否为空
     */
    private static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
