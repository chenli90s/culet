package utils;

import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

/**
 * @author Chenli
 * @version $Rev$
 * @time 2017/2/25 18:32
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class Encoder{



    public static String encoderByMd5(String str){
        String md5Str = str;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder = new BASE64Encoder();
            md5Str = base64Encoder
                    .encode(md5.digest(str.getBytes("utf-8")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return md5Str;
    }
}
