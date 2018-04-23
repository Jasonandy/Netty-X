package cn.ucaner.netty.rpc.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import cn.ucaner.netty.rpc.protocol.RpcDecoder;
import cn.ucaner.netty.rpc.protocol.RpcEncoder;
import cn.ucaner.netty.rpc.protocol.RpcRequest;
import cn.ucaner.netty.rpc.protocol.RpcResponse;

/**
* @Package：cn.ucaner.netty.rpc.client   
* @ClassName：RpcClientInitializer   
* @Description：   <p> RpcClientInitializer </p>
* @Author： - luxiaoxun    - https://github.com/luxiaoxun/NettyRpc 
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class RpcClientInitializer extends ChannelInitializer<SocketChannel> {
	
	/**
	 * initChannel  初始化通道 
	 */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(new RpcEncoder(RpcRequest.class));
        cp.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
        cp.addLast(new RpcDecoder(RpcResponse.class));
        cp.addLast(new RpcClientHandler());
    }
}
