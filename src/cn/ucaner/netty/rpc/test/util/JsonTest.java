package cn.ucaner.netty.rpc.test.util;

import java.util.UUID;

import cn.ucaner.netty.rpc.protocol.JsonUtil;
import cn.ucaner.netty.rpc.protocol.RpcRequest;
import cn.ucaner.netty.rpc.protocol.RpcResponse;
import cn.ucaner.netty.rpc.protocol.SerializationUtil;
import cn.ucaner.netty.rpc.test.client.Person;
import cn.ucaner.netty.rpc.test.server.HelloServiceImpl;

/**
* @Package：cn.ucaner.netty.rpc.test.util   
* @ClassName：JsonTest   
* @Description：   <p> JsonTest </p>
* @Author： - jsc - https://github.com/luxiaoxun/NettyRpc    
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class JsonTest {
    public static void main(String[] args){
        RpcResponse response = new RpcResponse();
        response.setRequestId(UUID.randomUUID().toString());
        response.setError("Error msg");
        System.out.println(response.getRequestId());

        byte[] datas = JsonUtil.serialize(response);
        System.out.println("Json byte length: " + datas.length);

        byte[] datas2 = SerializationUtil.serialize(response);
        System.out.println("Protobuf byte length: " + datas2.length);

        RpcResponse resp = (RpcResponse)JsonUtil.deserialize(datas,RpcResponse.class);
        System.out.println(resp.getRequestId());
    }

    @SuppressWarnings("unused")
	private static void TestJsonSerialize(){
        RpcRequest request = new RpcRequest();
        request.setClassName(HelloServiceImpl.class.getName());
        request.setMethodName(HelloServiceImpl.class.getDeclaredMethods()[0].getName());
        Person person = new Person("lu","xiaoxun");
        request.setParameters(new Object[]{person});
        request.setRequestId(UUID.randomUUID().toString());
        System.out.println(request.getRequestId());

        byte[] datas = JsonUtil.serialize(request);
        System.out.println("Json byte length: " + datas.length);

        byte[] datas2 = SerializationUtil.serialize(request);
        System.out.println("Protobuf byte length: " + datas2.length);

        RpcRequest req = (RpcRequest)JsonUtil.deserialize(datas,RpcRequest.class);
        System.out.println(req.getRequestId());
    }

}
