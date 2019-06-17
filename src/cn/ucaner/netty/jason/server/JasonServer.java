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
package cn.ucaner.netty.jason.server;

import cn.ucaner.netty.jason.decoder.JasonDecoder;
import cn.ucaner.netty.jason.encoder.JasonEncoder;
import cn.ucaner.netty.jason.handler.JasonServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @projectName：Netty-X
 * @Package：cn.ucaner.netty.jason.server
 * @Description： <p> JasonServer </p>
 * @Author： - Jason
 * @CreatTime：2019/6/17 - 18:23
 * @Modify By：
 * @ModifyTime： 2019/6/17
 * @Modify marker：
 */
public class JasonServer {

    /**
     * 指定端口号
     */
    private static final int PORT = 8888;

    public static void main(String args[]) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 指定socket的一些属性
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            serverBootstrap.group(bossGroup, workerGroup)
                    /**
                     * 指定是一个NIO连接通道
                     */
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>(){

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            // 添加编解码器, 由于ByteToMessageDecoder的子类无法使用@Sharable注解,
                            // 这里必须给每个Handler都添加一个独立的Decoder.
                            pipeline.addLast(new JasonEncoder());
                            pipeline.addLast(new JasonDecoder());

                            // 添加逻辑控制层
                            pipeline.addLast(new JasonServerHandler());

                        }

                    });

            // 绑定对应的端口号,并启动开始监听端口上的连接
            Channel ch = serverBootstrap.bind(PORT).sync().channel();

            System.out.printf("Jason:协议启动地址：127.0.0.1:%d/\n", PORT);

            // 等待关闭,同步端口
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
