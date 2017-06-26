package util;

import java.io.*;

/**
 * Created by lyd on 2017-06-20.
 */
public class FileUtil {
    public static void writeFile(String filePath,String fileName,String content) throws IOException {
        findOrCreateFile(filePath + File.separator+fileName);
        FileWriter file = new FileWriter(filePath + File.separator+fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(file);
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
    public static void writeFile(String filePath,String fileName,byte[] content) throws IOException {
        findOrCreateFile(filePath + File.separator+fileName);
        FileOutputStream fio = new FileOutputStream(filePath + File.separator+fileName);
        fio.write(content);
        fio.flush();
        fio.close();
    }
    public static String readFile(String filePath,String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath + File.separator+fileName));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            sb.append(readLine);
        }
        br.close();
        return sb.toString();
    }
    public static byte[] readFileByte(String filePath,String fileName) throws IOException {

        File file = new File(filePath + File.separator+fileName);
        byte[] bytes= new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(bytes);
        return bytes;
    }
    /**
     * 用于创建父目录
     * @param filePathName
     * @return
     */
    private static void findOrCreateFile(String filePathName) {
        File file = null;
        file = new File(filePathName);
        if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
        }
    }
}
