package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import entity.ResultJson;

/**
 * @author Chenli
 * @time 2017/3/3 20:39
 */
public class JsonUtils {

    public static String object2JsonStr(ResultJson o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }

    /**
     * @warn this method have error in object with a collection and map
     *       accur stackoverflowerror;
     * @param o
     * @return
     * @throws JsonProcessingException
     */
    public static String object2Json(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String string = mapper.writeValueAsString(mapper);
        return string;
    }

    public static byte[] object2JsonByte(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(o);
    }

    public static JsonNode string2Json(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(json);
    }

    public static Object string2Object(JsonNode json,Class clazz){
        ObjectMapper objectMapper = new ObjectMapper();
        Object o = null;
        try {
            o = objectMapper.readValue(json.toString(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static Object string2Object(String json,Class clazz){
        ObjectMapper objectMapper = new ObjectMapper();
        Object o = null;
        try {
            o = objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }
}
