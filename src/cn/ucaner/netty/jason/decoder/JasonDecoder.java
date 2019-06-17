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
package cn.ucaner.netty.jason.decoder;

import cn.ucaner.netty.jason.header.JasonHeader;
import cn.ucaner.netty.jason.message.JasonMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @projectName：Netty-X
 * @Package：cn.ucaner.netty.jason.decoder
 * @Description： <p> JasonDecoder </p>
 * @Author： - Jason
 * @CreatTime：2019/6/17 - 18:11
 * @Modify By：
 * @ModifyTime： 2019/6/17
 * @Modify marker：
 */
public class JasonDecoder extends ByteToMessageDecoder {

    /**
     * header的长度
     */
    private final static int HEADER_LENGTH = 44;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 长度不足，退出
        if (in.readableBytes() < HEADER_LENGTH) {
            return;
        }
        // 获取协议的版本
        int version = in.readInt();
        // 获取消息长度
        int contentLength = in.readInt();
        // 获取SessionId
        byte[] sessionByte = new byte[36];
        in.readBytes(sessionByte);
        String sessionId = new String(sessionByte);
        // 组装协议头
        JasonHeader header = new JasonHeader(version, contentLength, sessionId);

        // 长度不足重置读index，退出
        if (in.readableBytes() < contentLength) {
            in.setIndex(in.readerIndex() - HEADER_LENGTH, in.writerIndex());
            return;
        }

        byte[] content = new byte[contentLength];
        // 读取消息内容
        in.readBytes(content);
        JasonMessage message = new JasonMessage(header, new String(content));
        out.add(message);
    }
}
