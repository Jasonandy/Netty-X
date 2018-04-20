package cn.ucaner.netty.rpc.test.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
* @Package：cn.ucaner.netty.rpc.test.server   
* @ClassName：RpcBootstrap   
* @Description：   <p> RpcBootstrap </p>
* @Author： - Jason   
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class RpcBootstrap {

    @SuppressWarnings("resource")
	public static void main(String[] args) {
    	//classpath:/rpc/server/server-rpc.xml
        new ClassPathXmlApplicationContext("server-rpc.xml");
    }
}
