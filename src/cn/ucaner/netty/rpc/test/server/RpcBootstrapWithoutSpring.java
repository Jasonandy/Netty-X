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
        
        //registry HelloNettyRpc 
        //HelloNettyRpcImpl helloNettyRpc = new HelloNettyRpcImpl();
        //rpcServer.addService("cn.ucaner.netty.rpc.test.interfaces.HelloNettyRpc", helloNettyRpc);
        try {
            rpcServer.start();
        } catch (Exception ex) {
            logger.error("Exception: {}", ex);
        }
    }
}
