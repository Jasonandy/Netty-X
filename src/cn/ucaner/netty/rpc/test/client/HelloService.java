package cn.ucaner.netty.rpc.test.client;

/**
* @Package：cn.ucaner.netty.rpc.test.client   
* @ClassName：HelloService   
* @Description：   <p> HelloService </p>
* @Author： - Jason   
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public interface HelloService {
	
    String hello(String name);

    String hello(Person person);
}
