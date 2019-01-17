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
package cn.ucaner.netty.example.echo.server;

import io.netty.channel.ChannelHandlerAdapter;

/**
* @Package：cn.ucaner.netty.example.echo.server   
* @ClassName：ResponseServerHandler   
* @Description：   <p> 
* 服务端处理通道.
* 		ResponseServerHandler 继承自 ChannelHandlerAdapter，这个类实现了ChannelHandler接口，
* 		ChannelHandler提供了许多事件处理的接口方法，然后你可以覆盖这些方法。
* 现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法。用来对请求响应
* </p>
* @Author： - Jason    
* @CreatTime：2019年1月17日 上午8:31:25   
* @Modify By：   
* @ModifyTime：  2019年1月17日
* @Modify marker：   
* @version    V1.0
 */
public class ResponseServerHandler extends ChannelHandlerAdapter{

}
