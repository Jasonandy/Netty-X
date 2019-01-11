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
package cn.ucaner.netty.rpc.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
* @Package：cn.ucaner.netty.rpc.protocol   
* @ClassName：RpcDecoder   
* @Description：   <p> RPC Decoder 
* </br> RpcDecoder提供 RPC 解码，只需扩展 Netty 的ByteToMessageDecoder抽象类的decode方法
* </br> https://my.oschina.net/huangyong/blog/361751
* </p>
* @Author： - huangyong    https://github.com/luxiaoxun/NettyRpc 
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class RpcDecoder extends ByteToMessageDecoder {

	/**
	 * 泛型类
	 */
    private Class<?> genericClass;

    /**
    * RpcDecoder.    -- 构造指定
    * @param genericClass
     */
    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    
    /**
     * 对Encode 数据进行 Decode.
     */
    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        /*if (dataLength <= 0) {
            ctx.close();
        }*/
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        //反序列化（字节数组 -> 对象） 将字节数组反序列化成class对应的Object
        Object obj = SerializationUtil.deserialize(data, genericClass);
        //Object obj = JsonUtil.deserialize(data,genericClass); // Not use this, have some bugs
        out.add(obj);
    }

}
