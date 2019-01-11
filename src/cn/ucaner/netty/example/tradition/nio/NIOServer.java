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
package cn.ucaner.netty.example.tradition.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;


/**
* @Package：cn.ucaner.netty.example.tradition.nio   
* @ClassName：NIOServer   
* @Description：   <p> NIO服务端</p>
* @Author： - Jason   
* @Modify By：   
* @ModifyTime：  2018年4月15日
* @Modify marker：   
* @version    V1.0
 */
public class NIOServer {
	
	/**
	 * 通道管理器
	 */
	private Selector selector;
	
	/**
	 * @Description: 获得一个ServerSocket通道，并对该通道做一些初始化的工作
	 * @param port   绑定的端口号
	 * @throws IOException io异常
	 * @Autor: Jason - Jasonandy@hotmail.com
	 */
	public void initServer(int port) throws IOException {
		// 获得一个ServerSocket通道
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// 设置通道为非阻塞
		serverChannel.configureBlocking(false);
		// 将该通道对应的ServerSocket绑定到port端口
		serverChannel.socket().bind(new InetSocketAddress(port));
		// 获得一个通道管理器
		this.selector = Selector.open();
		// 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件,注册该事件后，
		// 当该事件到达时，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞。
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	/**
	 * @Description: 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
	 * @throws IOException 
	 * @Autor: Jason - Jasonandy@hotmail.com
	 */
	public void listen() throws IOException {
		System.out.println("...Started success Server! [启动完成]...");
		// 轮询访问selector
		while (true) {
			// 当注册的事件到达时，方法返回；否则,该方法会一直阻塞
			selector.select();
			// 获得selector中选中的项的迭代器，选中的项为注册的事件
			Iterator<?> ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				// 删除已选的key,以防重复处理
				ite.remove();
				handler(key);
			}
		}
	}

	/**
	 * @Description: 处理请求
	 * @param key
	 * @throws IOException 
	 * @Autor: Jason - Jasonandy@hotmail.com
	 */
	public void handler(SelectionKey key) throws IOException {
		
		// 客户端请求连接事件
		if (key.isAcceptable()) {
			handlerAccept(key);
			// 获得了可读的事件
		} else if (key.isReadable()) {
			handelerRead(key);
		}
	}

	
	/**
	 * @Description: 处理连接请求
	 * @param key
	 * @throws IOException void
	 * @Autor: Jason - Jasonandy@hotmail.com
	 */
	public void handlerAccept(SelectionKey key) throws IOException {
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		// 获得和客户端连接的通道
		SocketChannel channel = server.accept();
		// 设置成非阻塞
		channel.configureBlocking(false);
		// 在这里可以给客户端发送信息哦
		System.out.println("A new Client established!");
		// 在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
		channel.register(this.selector, SelectionKey.OP_READ);
	}
	
	/**
	 * @Description: 处理读的事件
	 * @param key
	 * @throws IOException 
	 * @Autor: Jason - Jasonandy@hotmail.com
	 */
	public void handelerRead(SelectionKey key) throws IOException {
		// 服务器可读取消息:得到事件发生的Socket通道
		SocketChannel channel = (SocketChannel) key.channel();
		// 创建读取的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int read = channel.read(buffer);
		if(read > 0){
			byte[] data = buffer.array();
			String msg = new String(data).trim();
			System.out.println("Server recived Message :" + msg);
			
			//回写数据
			ByteBuffer outBuffer = ByteBuffer.wrap(("Yeap I had recived you Message.  -From Jason \r\n"+ new Date().toString()+"\r\n").getBytes());
			channel.write(outBuffer);// 将消息回送给客户端
		}else{
			System.out.println("Client Closed!");
			key.cancel();
		}
	}

	/**
	 * @Description: 启动服务端测试
	 * @param args
	 * @throws IOException 
	 * @Autor: Jason - Jasonandy@hotmail.com
	 */
	public static void main(String[] args) throws IOException {
		NIOServer server = new NIOServer();
		server.initServer(10086);
		server.listen();
	}

}
