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
package cn.ucaner.netty.jason.client;

import cn.ucaner.netty.jason.decoder.JasonDecoder;
import cn.ucaner.netty.jason.encoder.JasonEncoder;
import cn.ucaner.netty.jason.handler.JasonClientHandler;
import cn.ucaner.netty.jason.header.JasonHeader;
import cn.ucaner.netty.jason.message.JasonMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

/**
 * @projectName：Netty-X
 * @Package：cn.ucaner.netty.jason.client
 * @Description： <p> JasonClient </p>
 * @Author： - Jason
 * @CreatTime：2019/6/17 - 18:25
 * @Modify By：
 * @ModifyTime： 2019/6/17
 * @Modify marker：
 */
public class JasonClient {

    public static void main(String args[]) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 添加编码器
                    pipeline.addLast(new JasonEncoder());
                    // 添加解码器
                    pipeline.addLast(new JasonDecoder());
                    // 业务处理类（只打印了消息内容）
                    pipeline.addLast(new JasonClientHandler());
                }
            });

            // 连接服务端
            Channel ch = b.connect("127.0.0.1", 8888).sync().channel();
            int version = 1;
            String sessionId = UUID.randomUUID().toString();
            String str = "Hello!";

            // 发送1000000条消息
            for (int i = 0; i < 10; i++) {
                String content = str + "----" + i;
                JasonHeader header = new JasonHeader(version, content.length(), sessionId);
                JasonMessage message = new JasonMessage(header, content);
                ch.writeAndFlush(message);
            }
            ch.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
