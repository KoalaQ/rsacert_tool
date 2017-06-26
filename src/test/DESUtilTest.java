package test;

import chinaums.CommonUtil;
import chinaums.DESUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by lyd on 2017-06-21.
 */
public class DESUtilTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str="张三";
        String strjm= DESUtil.desEncrypt(str,"UTF-8");
        String strjiem= DESUtil.desDecrypt(strjm,"UTF-8");
        System.out.println(str);
        System.out.println(strjm);
        System.out.println(strjiem);
        System.out.println();



    }
}
