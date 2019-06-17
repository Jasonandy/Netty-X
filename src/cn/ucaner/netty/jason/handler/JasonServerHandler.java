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
package cn.ucaner.netty.jason.handler;

import cn.ucaner.netty.jason.message.JasonMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @projectName：Netty-X
 * @Package：cn.ucaner.netty.jason.handler
 * @Description： <p> JasonServerHandler </p>
 * @Author： - Jason
 * @CreatTime：2019/6/17 - 18:24
 * @Modify By：
 * @ModifyTime： 2019/6/17
 * @Modify marker：
 */
public class JasonServerHandler extends SimpleChannelInboundHandler<JasonMessage> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, JasonMessage jasonMessage) throws Exception {
        // 简单地打印出server接收到的消息
        System.out.println("接收:"+jasonMessage);
    }
}
