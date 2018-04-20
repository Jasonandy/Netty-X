package cn.ucaner.netty.rpc.test.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ucaner.netty.rpc.registry.ServiceRegistry;
import cn.ucaner.netty.rpc.server.RpcServer;
import cn.ucaner.netty.rpc.test.client.HelloService;

/**
* @Package：cn.ucaner.netty.rpc.test.server   
* @ClassName：RpcBootstrapWithoutSpring   
* @Description：   <p> RpcBootstrapWithoutSpring </p>
* @Author： - Jason   
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class RpcBootstrapWithoutSpring {
	
    private static final Logger logger = LoggerFactory.getLogger(RpcBootstrapWithoutSpring.class);

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1:8280";
        ServiceRegistry serviceRegistry = new ServiceRegistry("67.218.158.137:2181");
        RpcServer rpcServer = new RpcServer(serverAddress, serviceRegistry);
        HelloService helloService = new HelloServiceImpl();
        rpcServer.addService("cn.ucaner.netty.rpc.test.client.HelloService", helloService);
        try {
            rpcServer.start();
        } catch (Exception ex) {
            logger.error("Exception: {}", ex);
        }
    }
}
