import java.io.*;
import java.net.URLDecoder;

/**
 * @Author: qwerdf@QAQ
 * @Description:TODO
 * @Date: 2020/9/20
 * @Param null:
 * @return: null
 **/

public class FileReadAndWrite {
    public static void main(String[] args) {
        String inputPath = "F:\\ideaProjects\\JavaTest\\src\\main\\resources\\ratings.dat";
        String outputPath = "F:\\ideaProjects\\JavaTest\\src\\main\\resources\\out\\ratings.txt";
        readAndWriteByLine(inputPath,outputPath);
    }

    public static void readAndWriteByLine(String inputPath, String outputPath) {
        //File file = new File(inputPath);
        BufferedReader reader = null;//字符缓冲读入流
        BufferedWriter writer = null;//字符缓冲写出流

        //字节流
        //InputStream inputStream = new FileInputStream(" ");
        //OutputStream outputStream = new FileOutputStream(" ");
        try {
            reader = new BufferedReader(new FileReader(inputPath));
            writer = new BufferedWriter(new FileWriter(outputPath));
            String tempStr = null;
            int line = 1;
            while ((tempStr = reader.readLine()) != null) {
                Thread.sleep(10);
                tempStr = tempStr.replaceAll("::", ":");
                String decode = URLDecoder.decode(tempStr, new String("utf-8"));
                System.out.println(decode);
                writer.write(decode);
                writer.newLine();//跨平台换行
                line++;
            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}