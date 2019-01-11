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
package cn.ucaner.netty.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.ucaner.netty.rpc.protocol.RpcDecoder;
import cn.ucaner.netty.rpc.protocol.RpcEncoder;
import cn.ucaner.netty.rpc.protocol.RpcRequest;
import cn.ucaner.netty.rpc.protocol.RpcResponse;
import cn.ucaner.netty.rpc.registry.ServiceRegistry;

/**
* @Package：cn.ucaner.netty.rpc.server   
* @ClassName：RpcServer   
* @Description：   <p> RpcServer http://www.cnblogs.com/luxiaoxun/p/5272384.html 
* </br> https://my.oschina.net/huangyong/blog/361751 
* 
* </br> InitializingBean- https://www.cnblogs.com/study-everyday/p/6257127.html
* 		为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候会执行该方法
* 
* </br> ApplicationContextAware  - https://blog.csdn.net/bailinbbc/article/details/76446594
* 		在某些特殊的情况下，Bean需要实现某个功能，但该功能必须借助于Spring容器才能实现，
* 		此时就必须让该Bean先获取Spring容器，然后借助于Spring容器实现该功能。
* 		为了让Bean获取它所在的Spring容器，可以让该Bean实现ApplicationContextAware接口。
* 
* </br> https://yq.aliyun.com/articles/50480
* </p>
* @Author： -huangyong, luxiaoxun    https://github.com/luxiaoxun/NettyRpc  
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private String serverAddress;//服务地址
    
    private ServiceRegistry serviceRegistry;//ServiceRegistry 服务注册地址

    private Map<String, Object> handlerMap = new HashMap<>();// 存放接口名与服务对象之间的映射关系
    
    private static ThreadPoolExecutor threadPoolExecutor;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;

    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
        this.serverAddress = serverAddress;
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * RpcService 检测到注解   加载注解过的Interface到applicationContext   add By Jason
     *  				  --  服务在启动的时候扫描得到所有的服务接口及其实现
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                logger.info("Loading service: {}", interfaceName);
                handlerMap.put(interfaceName, serviceBean);
            }
        }
    }

    /**
     * afterPropertiesSet 所有的属性被初始化后调用,但是会在init前调用。
     * @Lazy(false)  - 这样spring容器初始化的时候afterPropertiesSet就会被调用
     * 调用afterPropertiesSet方法,通过这个方法,你可以检查你的bean是否正确地被初始化了.
     * 当然,你也可以用init-method方法.这两种方式可以同时使用,调用的顺序为init-method后调用.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }

    public void stop() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    public static void submit(Runnable task) {
        if (threadPoolExecutor == null) {
            synchronized (RpcServer.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L,
                            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
                }
            }
        }
        threadPoolExecutor.submit(task);
    }

    public RpcServer addService(String interfaceName, Object serviceBean) {
        if (!handlerMap.containsKey(interfaceName)) {
            logger.info("Loading service: {}", interfaceName);
            handlerMap.put(interfaceName, serviceBean);
        }

        return this;
    }

    /**
     * @Description: afterPropertiesSet 
     * @throws Exception void
     * @Autor: Jason - jasonandy@hotmail.com
     */
    public void start() throws Exception {
        if (bossGroup == null && workerGroup == null) {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                                    .addLast(new RpcDecoder(RpcRequest.class)) //将 RPC 请求进行解码（为了处理请求）
                                    .addLast(new RpcEncoder(RpcResponse.class))//RPC 响应进行编码（为了返回响应）
                                    .addLast(new RpcHandler(handlerMap));// 处理 RPC请求
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            String[] array = serverAddress.split(":");
            String host = array[0];
            int port = Integer.parseInt(array[1]);

            ChannelFuture future = bootstrap.bind(host, port).sync();
            logger.info("Server started on port {}", port);

            /**
             * 扫描到有注解的数据进行注册处理   add by Jason
             */
            if (serviceRegistry != null) {
                serviceRegistry.register(serverAddress);//注册服务地址
            }
            future.channel().closeFuture().sync();
        }
    }

}
