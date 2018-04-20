package cn.ucaner.netty.rpc.test.server;

import cn.ucaner.netty.rpc.server.RpcService;
import cn.ucaner.netty.rpc.test.client.HelloService;
import cn.ucaner.netty.rpc.test.client.Person;

/**
* @Package：cn.ucaner.netty.rpc.test.server   
* @ClassName：HelloServiceImpl   
* @Description：   <p> HelloServiceImpl </p>
* @Author： - Jason   
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    public HelloServiceImpl(){

    }

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }

    @Override
    public String hello(Person person) {
        return "Hello! " + person.getFirstName() + " " + person.getLastName();
    }
}
