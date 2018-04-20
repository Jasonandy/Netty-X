package cn.ucaner.netty.rpc.client.proxy;

import cn.ucaner.netty.rpc.client.RPCFuture;

/**
* @Package：cn.ucaner.netty.rpc.client.proxy   
* @ClassName：IAsyncObjectProxy   
* @Description：   <p> IAsyncObjectProxy </p>
* @Author： - luxiaoxun   - https://github.com/luxiaoxun/NettyRpc     
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public interface IAsyncObjectProxy {
	
    public RPCFuture call(String funcName, Object... args);
    
}