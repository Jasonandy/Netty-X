/**
 * <html>
 * <body>
 *  <P> Copyright 1994-2018 JasonInternational </p>
 *  <p> All rights reserved.</p>
 *  <p> Created by Jason</p>
 *  </body>
 * </html>
 */
package cn.ucaner.netty.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**     
 * @Package：cn.ucaner.netty.demo.client   
 * @ClassName：DiscardClient   
 * @Description：   <p> DiscardClient</p>
 * @Author： - Jason   
 * @Modify By：   
 * @ModifyTime：  2018年4月15日
 * @Modify marker：   
 * @version    V1.0
 */
public class DiscardClient {
	
	 public static void main(String[] args) throws InterruptedException {  
		 
		// 配置客户端NIO线程组
	    // 首先创建客户端处理I/O读写的NioEventLoop Group线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
			//继续创建客户端辅助启动类Bootstrap，随后需要对其进行配置。
			//与服务端不同的是，它的Channel需要设置为NioSocketChannel
			//然后为其添加handler，此处为了简单直接创建匿名内部类，实现initChannel方法，
			//其作用是当创建NioSocketChannel成功之后，
			//在初始化它的时候将它的ChannelHandler设置到ChannelPipeline中，用于处理网络I/O事件。
			Bootstrap bootstrap = new Bootstrap();  
			bootstrap.group(workerGroup)  
			        .channel(NioSocketChannel.class)  
			        .handler(new ChannelInitializer<SocketChannel>() {  
			            @Override  
			            protected void initChannel(SocketChannel socketChannel) throws Exception {  
			                socketChannel.pipeline().addLast(new DiscardClientHandler());  
			            }  
			        }); 
			
			// 发起异步连接操作
            //客户端启动辅助类设置完成之后，调用connect方法发起异步连接，
            //然后调用同步方法等待连接成功。
			ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync(); 
			
			
			//future.channel().writeAndFlush(Unpooled.copiedBuffer("HelloWorld!".getBytes()));

			future.channel().closeFuture().sync();
		} finally {
            // 优雅退出，释放NIO线程组
            //在退出之前，释放NIO线程组的资源。
			workerGroup.shutdownGracefully(); 
        }
        
    }  

}
