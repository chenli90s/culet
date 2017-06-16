package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import entity.UploadFilesJson;

/**
 * @author Chenli
 * @time 2017/3/10 9:34
 */
public class UploadFilesUtils {
    /**
     *
     * @param
     * @param path  upload/--/--/
     * @return
     * @throws IOException
     */
    public static String upLoadFiles(MultipartFile[] uploadFile, String path) throws IOException {
        if (uploadFile != null) {
            MultipartFile[] filess = uploadFile;
            int count = 0;
            String[] name = new String[filess.length];
            for (MultipartFile file : filess) {
                if (!file.isEmpty()) {
                    String imgName = file.getOriginalFilename();
                    String filename = UUIDUtils.getUUIDHex() + imgName.substring(imgName.indexOf("."));
                    //System.out.println(path);
                    boolean mkdirs = new File(path).mkdirs();
                    String filepath = path + filename;
                    try {
                        File files = new File(filepath);
                        if (!files.exists()) {
                            boolean newFile = files.createNewFile();
                            //System.out.println(newFile);
                        }
                        file.transferTo(files);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    name[count] = "/upload/announce/"+filename;
                    count++;
                }
            }
            return new ObjectMapper().writeValueAsString(new UploadFilesJson(0,name));
        }
        return new ObjectMapper().writeValueAsString(new UploadFilesJson(1,null));
    }

}
