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
package cn.ucaner.netty.rpc.test.interfaces;

/**     
 * @Package：cn.ucaner.netty.rpc.test.interfaces   
 * @ClassName：HelloNettyRpc   
 * @Description：   <p> Rpc. test Interface </p>
 * @Author： - Jason   
 * @Modify By：   
 * @Modify marker：   
 * @version    V1.0
 */
public interface HelloNettyRpc {
	
	/**
	 * @Description: echo
	 * @param ping
	 * @return String
	 * @Autor: Jason - jasonandy@hotmail.com
	 */
	public String echo(String ping);
	
	
	/**
	 * @Description: 定义接口（服务）    --- 远程impl 实现  - [tips: package need same.]
	 * @return String
	 * @Autor: Jason - jasonandy@hotmail.com
	 */
	public String helloWorld();
}
