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
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**     
 * @Package：cn.ucaner.netty.demo.client   
 * @ClassName：DiscardClient   
 * @Description：   <p> TODO</p>
 * @Author： - DaoDou   
 * @CreatTime：2018年4月15日 上午11:47:03   
 * @Modify By：   
 * @ModifyTime：  2018年4月15日
 * @Modify marker：   
 * @version    V1.0
 */
public class DiscardClient {
	
	 public static void main(String[] args) throws InterruptedException {  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        Bootstrap bootstrap = new Bootstrap();  
        bootstrap.group(workerGroup)  
                .channel(NioSocketChannel.class)  
                .handler(new ChannelInitializer<SocketChannel>() {  
                    @Override  
                    protected void initChannel(SocketChannel socketChannel) throws Exception {  
                        socketChannel.pipeline().addLast(new DiscardClientHandler());  
                    }  
                });  
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();  
        future.channel().writeAndFlush(Unpooled.copiedBuffer("777".getBytes()));  
        future.channel().closeFuture().sync();  
        workerGroup.shutdownGracefully();  
    }  

}
