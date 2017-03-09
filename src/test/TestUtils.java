package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;

import entity.ResultJson;
import entity.User;
import utils.JsonUtils;
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

    @Test
    public void testString2Json() throws IOException {
        String str = JsonUtils.object2JsonStr(new ResultJson(true,
                "测试","chenli",new User("chenli",
                "64497","sdad",false)));
        JsonNode jsonNode = JsonUtils.string2Json(str);
        User user = (User) JsonUtils.string2Object(jsonNode.get("data"), User.class);
        System.out.println(jsonNode.get("data"));
        System.out.println(user.getUsername());
    }
}
