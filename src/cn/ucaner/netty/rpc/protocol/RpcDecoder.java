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

    private Class<?> genericClass;

    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

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

        Object obj = SerializationUtil.deserialize(data, genericClass);
        //Object obj = JsonUtil.deserialize(data,genericClass); // Not use this, have some bugs
        out.add(obj);
    }

}