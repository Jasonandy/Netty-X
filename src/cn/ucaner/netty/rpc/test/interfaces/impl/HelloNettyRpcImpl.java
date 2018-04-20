/**
 * <html>
 * <body>
 *  <P> Copyright 1994-2018 JasonInternational</p>
 *  <p> All rights reserved.</p>
 *  <p> Created by Jason</p>
 *  <p> https://github.com/Jasonandy/Netty-X </p>
 *  </body>
 * </html>
 */
package cn.ucaner.netty.rpc.test.interfaces.impl;

import cn.ucaner.netty.rpc.server.RpcService;
import cn.ucaner.netty.rpc.test.interfaces.HelloNettyRpc;

/**     
 * @Package：cn.ucaner.netty.rpc.test.interfaces.impl   
 * @ClassName：HelloNettyRpcImpl   
 * @Description：   <p> HelloNettyRpcImpl 接口实现类测试</p>
 * @Author： - Jason   
 * @Modify By：   
 * @Modify marker：   
 * @version    V1.0
 */
@RpcService(HelloNettyRpc.class) // 指定远程接口
public class HelloNettyRpcImpl implements HelloNettyRpc{

	@Override
	public String echo(String ping) {
		return ping != null? " - > I'm Fine Thks." : "Hello. \r I'm NettyRpc";
	}

	@Override
	public String helloWorld() {
		return "HelloWorld!";
	}

}
