/**
 * <html>
 * <body>
 *  <P> Copyright 2014 广东天泽阳光康众医疗投资管理有限公司. 粤ICP备09007530号-15</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年4月15日 上午11:48:02</p>
 *  <p> Created by DaoDou</p>
 *  </body>
 * </html>
 */
package cn.ucaner.netty.demo.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**     
 * @Package：cn.ucaner.netty.demo.client   
 * @ClassName：DiscardClientHandler   
 * @Description：   <p> DiscardClientHandler</p>
 * @Author： - Jason   
 * @Modify By：   
 * @ModifyTime：  2018年4月15日
 * @Modify marker：   
 * @version    V1.0
 */
public class DiscardClientHandler extends ChannelHandlerAdapter{

	@Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        try {  
            ByteBuf buf = (ByteBuf) msg;  
            byte[] data = new byte[buf.readableBytes()];  
            buf.readBytes(data);  
            System.out.println("Client：" + new String(data).trim());  
        } finally {  
            ReferenceCountUtil.release(msg);  
        }  
    }  
	  
	  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        cause.printStackTrace();  
        ctx.close();  
    }  
}
