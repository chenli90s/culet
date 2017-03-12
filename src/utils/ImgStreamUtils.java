package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sun.misc.BASE64Encoder;

/**
 * @author Chenli
 * @time 2017/3/10 15:58
 */

public class ImgStreamUtils {

    private static FileInputStream inputStream;
    private static ByteArrayOutputStream outputStream;
    private static FileOutputStream output;

    public static String baseImg(String path){
        try {
            BASE64Encoder base64Encoder =  new BASE64Encoder();
            inputStream = new FileInputStream(path);
            outputStream = new ByteArrayOutputStream();
           /* int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf)) != -1){
                outputStream.write(buf,0,len);
            }
            byte[] bytes = outputStream.toByteArray();*/
            base64Encoder.encode(inputStream, outputStream);
            String baseCode = outputStream.toString("UTF-8");
            return baseCode;
        }catch (Exception e){
            e.printStackTrace();
            return "图片还未上传";
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String setHeadImgPath(String head) {
        try {
            File file = new File(head);
            inputStream = new FileInputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            final String filename = "../img/"+file.getName();
            output = new FileOutputStream(filename);
            while ((len = inputStream.read(buf)) != -1){
                output.write(buf,0,len);
            }
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000*30);
                        File filecatch = new File(filename);
                        filecatch.delete();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            return filename;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "图片还未上传";
    }
}
