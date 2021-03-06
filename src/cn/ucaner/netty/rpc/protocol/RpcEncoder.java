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
import io.netty.handler.codec.MessageToByteEncoder;

/**
* @Package：cn.ucaner.netty.rpc.protocol   
* @ClassName：RpcEncoder   
* @Description：   <p> RPC Encoder 
* </br>RpcEncoder提供 RPC 编码，只需扩展 Netty 的MessageToByteEncoder抽象类的encode方法即可
* </p>
* @Author： - huangyong    https://github.com/luxiaoxun/NettyRpc    
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class RpcEncoder extends MessageToByteEncoder<Object> {

	//泛型类
    private Class<?> genericClass;

    //泛型指定
    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    /**
     * encode 对象encode处理  
     * 序列化（对象 -> 字节数组）
     */
    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
        	//序列化为数据 
            byte[] data = SerializationUtil.serialize(in);
            //byte[] data = JsonUtil.serialize(in); // Not use this, have some bugs
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
