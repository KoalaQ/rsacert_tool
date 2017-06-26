package chinaums;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 共通方法类
 *
 * @author wudan
 */
public class DESUtil {

    /**
     * DES方法为字符串加密
     *
     * @param value
     * @return
     */
    public static String desEncrypt(String value, String charsetName) {

        byte[] b;
        byte[] c;
        String str = "";
        try {
            b = DESForJava.encryption(value, "12345678",charsetName);
            c = DESForJava.bcd_to_asc(b);
            str = new String(c);
        } catch (UnsupportedEncodingException e) {
            return "error";
        }

        return str;
    }
    public static String desEncrypt(String value) {

        byte[] b;
        byte[] c;
        String str = "";
        try {
            b = DESForJava.encryption(value, "12345678");
            c = DESForJava.bcd_to_asc(b);
            str = new String(c);
        } catch (UnsupportedEncodingException e) {
            return "error";
        }

        return str;
    }
    /**
     * DES 方法解密
     *
     * @param value
     * @return
     */
    public static String desDecrypt(String value, String charsetName) {

        byte[] b;
        byte[] c;
        int i;
        String str = "";
        try {
            c = value.getBytes();
            i = c.length;
            b = DESForJava.asc_to_bcd(c, i);
            str = DESForJava.decryption(b, "12345678",charsetName);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return str;
    }
    public static String desDecrypt(String value) {

        byte[] b;
        byte[] c;
        int i;
        String str = "";
        try {
            c = value.getBytes();
            i = c.length;
            b = DESForJava.asc_to_bcd(c, i);
            str = DESForJava.decryption(b, "12345678");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return str;
    }
    /**
     * 比较两个时间大小（格式：HH:MM）
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int comTime(String time1, String time2) {
        Date date1 = new Date();
        Date date2 = new Date();
        DateFormat formart = new SimpleDateFormat("HH:mm");
        try {
            date1 = formart.parse(time1);
            date2 = formart.parse(time2);
        } catch (Exception e) {
            System.out.println("date init fail!");
            e.printStackTrace();
        }

        return date1.compareTo(date2);

    }




}

