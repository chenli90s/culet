package utils;

import java.util.UUID;

/**
 * @author Chenli
 * @time 2017/3/2 10:57
 */
public class UUIDUtils {

    public static String getUUIDHex(){
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replace("-", "");
        return s;
    }
}
