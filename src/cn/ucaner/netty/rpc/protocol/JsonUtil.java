/******************************************************************************
* ~ Copyright (c) 2018 [jasonandy@hotmail.com | https://github.com/Jasonandy] *
* ~                                                                           *
* ~ Licensed under the Apache License, Version 2.0 (the "License”);           * 
* ~ you may not use this file except in compliance with the License.          *
* ~ You may obtain a copy of the License at                                   *
* ~                                                                           *
* ~    http://www.apache.org/licenses/LICENSE-2.0                             *
* ~                                                                           *
* ~ Unless required by applicable law or agreed to in writing, software       *
* ~ distributed under the License is distributed on an "AS IS" BASIS,         *
* ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
* ~ See the License for the specific language governing permissions and       *
* ~ limitations under the License.                                            *
******************************************************************************/
package cn.ucaner.netty.rpc.protocol;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
* @Package：cn.ucaner.netty.rpc.protocol   
* @ClassName：JsonUtil   
* @Description：   <p> JsonUtil  Based On com.fasterxml.jackson </p>
* @Author： - luxiaoxun - https://github.com/luxiaoxun/NettyRpc    
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class JsonUtil {
	
    private static ObjectMapper objMapper = new ObjectMapper();

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objMapper.setDateFormat(dateFormat);
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        objMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false);
        objMapper.disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
        objMapper.disable(SerializationFeature.CLOSE_CLOSEABLE);
        objMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
    }

    /**
     * 
     * @Description: serialize 对象序列化成数组
     * @param obj
     * @return byte[]
     * @Autor: Jason - jasonandy@hotmail.com
     */
    public static <T> byte[] serialize(T obj) {
        byte[] bytes = new byte[0];
        try {
            bytes = objMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return bytes;
    }

    /**
     * @Description: 数组反序列化为Object
     * @param data
     * @param cls
     * @return T
     * @Autor: Jason - jasonandy@hotmail.com
     */
    public static <T> T deserialize(byte[] data, Class<T> cls) {
        T obj = null;
        try {
            obj = objMapper.readValue(data, cls);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return obj;
    }

    /**
     * @Description: JSON convert to Object
     * @param json
     * @param cls
     * @return type
     * @Autor: Jason - jasonandy@hotmail.com
     */
    public static <type> type jsonToObject(String json, Class<?> cls) {
        type obj = null;
        JavaType javaType = objMapper.getTypeFactory().constructType(cls);
        try {
            obj = objMapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return obj;
    }

    /**
     * @Description: jsonToObjectList 
     * @param json
     * @param collectionClass
     * @param elementClass
     * @return type
     * @Autor: Jason - jasonandy@hotmail.com
     */
    public static <type> type jsonToObjectList(String json, Class<?> collectionClass, Class<?>... elementClass) {
        type obj = null;
        JavaType javaType = objMapper.getTypeFactory().constructParametricType(
                collectionClass, elementClass);
        try {
            obj = objMapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return obj;
    }

    /**
     * 
     * @Description: jsonToObjectHashMap 
     * @param json
     * @param keyClass
     * @param valueClass
     * @return type
     * @Autor: Jason - jasonandy@hotmail.com
     */
    public static <type> type jsonToObjectHashMap(String json, Class<?> keyClass, Class<?> valueClass) {
        type obj = null;
        JavaType javaType = objMapper.getTypeFactory().constructParametricType(HashMap.class, keyClass, valueClass);
        try {
            obj = objMapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return obj;
    }

    /**
     * @Description: objectToJson 
     * @param o
     * @return String
     * @Autor: Jason - jasonandy@hotmail.com
     */
    public static String objectToJson(Object o) {
        String json = "";
        try {
            json = objMapper.writeValueAsString(o);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return json;
    }
    
    /**
     * @Description: Just For Test
     */
    public static void main(String[] args) {
		
    	String objectToJson = objectToJson("HelloWorld!");
    	System.out.println(objectToJson);
    	//Output "HelloWorld!"
	}
}
