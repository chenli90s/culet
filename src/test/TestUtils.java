package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import entity.ResultJson;
import utils.UUIDUtils;

/**
 * @author Chenli
 * @time 2017/3/2 11:05
 */
public class TestUtils {

    @Test
    public void testUUid(){
        String uuidHex = UUIDUtils.getUUIDHex();
        System.out.println(uuidHex);
    }

    @Test
    public void testJson() throws JsonProcessingException {
        ObjectMapper json = new ObjectMapper();
        ResultJson resultJson = new ResultJson(true, "测试");
        byte[] buf = json.writeValueAsBytes(resultJson);
        String s = json.writeValueAsString(resultJson);
        for (int i = 0 ; i<buf.length;i++ ){
           System.out.print(buf[i]);
        }

        System.out.println("\n"+s);
    }
}
