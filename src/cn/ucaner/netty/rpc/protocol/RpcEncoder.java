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

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = SerializationUtil.serialize(in);
            //byte[] data = JsonUtil.serialize(in); // Not use this, have some bugs
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
