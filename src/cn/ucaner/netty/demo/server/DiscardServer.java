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
package cn.ucaner.netty.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
* @Package：cn.ucaner.netty.demo.server   
* @ClassName：DiscardServer   
* @Description：   <p> 丢弃任何进入的数据 启动服务端的DiscardServerHandler </p>
* @Author： - Jason    
* @CreatTime：2019年1月10日 下午9:42:40   
* @Modify By：   
* @ModifyTime：  2019年1月10日
* @Modify marker：   
* @version    V1.0
 */
public class DiscardServer {

	/**
	 * 端口
	 */
	private int port;

	/**
	* DiscardServer 构造 
	* @param port   端口
	 */
    public DiscardServer(int port) {
        super();
        this.port = port;
    }
    
    /**
     * @Description: Run by Netty 
     * @throws Exception void
     * @Autor: Jason - jasonandy@hotmail.com
     */
    public void run() throws Exception {

        /***
         * NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
         * Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议。 在这个例子中我们实现了一个服务端的应用，
         * 因此会有2个NioEventLoopGroup会被使用。 第一个经常被叫做‘boss’，用来接收进来的连接。
         * 第二个经常被叫做‘worker’，用来处理已经被接收的连接， 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
         * 如何知道多少个线程已经被使用，如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现，
         * 并且可以通过构造函数来配置他们的关系。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();//用于处理服务器端接收客户端连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();//进行网络通信（读写）
        System.out.println("准备运行端口：" + port);
        try {
            /**
             * ServerBootstrap 是一个启动NIO服务的辅助启动类 你可以在这个服务中直接使用Channel
             * 创建ServerBootstrap对象，它是Netty用于启动NIO服务端的辅助启动类，目的是降低服务端的开发复杂度。
             */
            ServerBootstrap b = new ServerBootstrap();//辅助工具类，用于服务器通道的一系列配置 
            /**
             * 这一步是必须的，如果没有设置group将会报java.lang.IllegalStateException: group not
             * set异常
             */
            b = b.group(bossGroup, workerGroup);//绑定两个线程组  
            /***
             * ServerSocketChannel以NIO的selector为基础进行实现的，用来接收新的连接
             * 这里告诉Channel如何获取新的连接.
             */
            b = b.channel(NioServerSocketChannel.class);//指定NIO的模式
            /***
             * 这里的事件处理类经常会被用来处理一个最近的已经接收的Channel。 ChannelInitializer是一个特殊的处理类，
             * 他的目的是帮助使用者配置一个新的Channel。
             * 也许你想通过增加一些处理类比如NettyServerHandler来配置一个新的Channel
             * 或者其对应的ChannelPipeline来实现你的网络程序。 当你的程序变的复杂时，可能你会增加更多的处理类到pipline上，
             * 然后提取这些匿名类到最顶层的类上。
             */
            b = b.childHandler(new ChannelInitializer<SocketChannel>() { // (4) //配置具体的数据处理方式  
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new DiscardServerHandler());// demo1.discard
                    // ch.pipeline().addLast(new
                    // ResponseServerHandler());//demo2.echo
                    // ch.pipeline().addLast(new
                    // TimeServerHandler());//demo3.time
                }
            });
            /***
             * 你可以设置这里指定的通道实现的配置参数。 我们正在写一个TCP/IP的服务端，
             * 因此我们被允许设置socket的参数选项比如tcpNoDelay和keepAlive。
             * 请参考ChannelOption和详细的ChannelConfig实现的接口文档以此可以对ChannelOptions的有一个大概的认识。
             */
            b = b.option(ChannelOption.SO_BACKLOG, 128); //设置TCP缓冲区 
            
            
            b = b.option(ChannelOption.SO_SNDBUF, 32 * 1024);//设置发送数据缓冲大小  
    		b = b.option(ChannelOption.SO_RCVBUF, 32 * 1024);//设置接受数据缓冲大小  
            
            /***
             * option()是提供给NioServerSocketChannel用来接收进来的连接。
             * childOption()是提供给由父管道ServerChannel接收到的连接，
             * 在这个例子中也是NioServerSocketChannel。
             */
            b = b.childOption(ChannelOption.SO_KEEPALIVE, true); //保持连接  
            
            
            //绑定端口，同步等待成功
            //服务端启动辅助类配置完成之后，调用它的bind方法绑定监听端口
            //随后，调用它的同步阻塞方法sync等待绑定操作完成。
            //完成之后Netty会返回一个ChannelFuture，它的功能类似于JDK的java.util.concurrent.Future，主要用于异步操作的通知回调。
            ChannelFuture f = b.bind(port).sync();
            
            
            //等待服务端监听端口关闭
            //使用f.channel().closeFuture().sync()方法进行阻塞,等待服务端链路关闭之后main函数才退出。
            f.channel().closeFuture().sync();
            
        } finally {
        	
        	//优雅退出，释放线程池资源
            //调用NIO线程组的shutdownGracefully进行优雅退出，它会释放跟shutdownGracefully相关联的资源。
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    /**
     * @Description: Just for test. 
     * @param args
     * @throws Exception void
     * @Autor Jason- Jasonandy@hotmail.com
     */
    public static void main(String[] args) throws Exception {
    	
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8088;
        }
        new DiscardServer(port).run();
        System.out.println("server:run()");
    }
}
