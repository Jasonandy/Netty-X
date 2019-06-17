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
package cn.ucaner.netty.jason.encoder;

import cn.ucaner.netty.jason.header.JasonHeader;
import cn.ucaner.netty.jason.message.JasonMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @projectName：Netty-X
 * @Package：cn.ucaner.netty.jason.encoder
 * @Description： <p> JasonEncoder </p>
 * @Author： - Jason
 * @CreatTime：2019/6/17 - 18:11
 * @Modify By：
 * @ModifyTime： 2019/6/17
 * @Modify marker：
 */
public class JasonEncoder extends MessageToByteEncoder<JasonMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, JasonMessage jasonMessage, ByteBuf byteBuf) throws Exception {
        // 将Message转换成二进制数据
        JasonHeader header = jasonMessage.getJasonHeader();
        // 写入Header信息
        byteBuf.writeInt(header.getVersion());
        byteBuf.writeInt(header.getContentLength());
        byteBuf.writeBytes(header.getSessionId().getBytes());
        // 写入消息主体信息
        byteBuf.writeBytes(jasonMessage.getContent().getBytes());
    }
}
