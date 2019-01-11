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
package cn.ucaner.netty.example.tradition.oio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
* @Package：cn.ucaner.netty.example.tradition.oio   
* @ClassName：OioServer   
* @Description：   <p> 传统socket服务端 </p>
* @Author： - Jason   
* @Modify By：   
* @ModifyTime：  2018年4月15日
* @Modify marker：   
* @version    V1.0
 */
public class OioServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		/**
		 * 线程池  可以同时支持多个socket的服务   -- 提高负载能力.
		 */
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		
		/**
		 * 创建socket服务,监听10086端口
		 */
		ServerSocket server=new ServerSocket(10086);
		System.out.println("服务器启动!");
		
		while(true){
			/**
			 * 获取一个套接字（阻塞）    OIO & NOT NO BROKEN IO 
			 */
			final Socket socket = server.accept(); //这里会阻塞     telnet 127.0.0.1 10086   
			System.out.println("来个一个新客户端!");
			newCachedThreadPool.execute(new Runnable() {//加入线程池之后可以服务   一个线程只能为一个Socket服务.
				@Override
				public void run() {
					//业务处理
					handler(socket);
				}
			});
			
		}
	}
	
	/**
	 * @Description: 读取数据
	 * @param socket  Socket 套接字里面处理数据
	 * @Autor: @Jason - jasonandy@hotmail.com
	 */
	public static void handler(Socket socket){
			try {
				byte[] bytes = new byte[1024];
				InputStream inputStream = socket.getInputStream();
				while(true){
					//读取数据（阻塞）
					int read = inputStream.read(bytes);
					if(read != -1){
						System.out.println(new String(bytes, 0, read));
					}else{
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					System.out.println(new String("socket关闭"));
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
}
