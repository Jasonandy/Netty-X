/**
 * <html>
 * <body>
 *  <P> Copyright 1994-2018 JasonInternational </p>
 *  <p> All rights reserved.</p>
 *  <p> Created by Jason</p>
 *  </body>
 * </html>
 */
package cn.ucaner.netty.demo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;


/**
 * 服务端处理通道.这里只是打印一下请求的内容，并不对请求进行任何的响应 DiscardServerHandler 继承自
 * ChannelHandlerAdapter， 这个类实现了ChannelHandler接口， ChannelHandler提供了许多事件处理的接口方法，
 * 然后你可以覆盖这些方法。 现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法。
 */
public class DiscardServerHandler extends ChannelHandlerAdapter{

	
	 /**
     * 这里我们覆盖了chanelRead()事件处理方法。 每当从客户端收到新的数据时， 这个方法会在收到消息时被调用，
     * 这个例子中，收到的消息的类型是ByteBuf
     * @param ctx
     *            通道处理的上下文信息
     * @param msg
     *            接收的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{

        try {
        	
        	//做类型转换，将msg转换成Netty的ByteBuf对象。
        	//ByteBuf类似于JDK中的java.nio.ByteBuffer 对象，不过它提供了更加强大和灵活的功能。
            ByteBuf buf = (ByteBuf) msg;
            
            // 打印客户端输入，传输过来的的字符
            System.out.print(buf.toString(CharsetUtil.UTF_8));
            
            //通过ByteBuf的readableBytes方法可以获取缓冲区可读的字节数，
            //根据可读的字节数创建byte数组
            byte[] data = new byte[buf.readableBytes()]; 
            
            //通过ByteBuf的readBytes方法将缓冲区中的字节数组复制到新建的byte数组中
            buf.readBytes(data); 
            
            //通过new String构造函数获取请求消息。
            String request = new String(data, "utf-8");  
            
            System.out.println("1.Server: " + request);  
            System.out.println("2.This server receive Message by Jason : " + request);
            
            String command = "I WANT KNOW WHO IS JASON".equalsIgnoreCase(request)?
            "Jason is the CEO of JasonInternational" :"Sorry ! This is a bad Command!";
            
            ctx.writeAndFlush(Unpooled.copiedBuffer(command.getBytes()));
            
            String response = "yesp I accept you message . Connection is established!";  
            
            //通过ChannelHandlerContext的write方法异步发送应答消息给客户端。
            ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));  
            
            //((ChannelFuture) ctx).addListener(ChannelFutureListener.CLOSE);  
        } finally {
            /**
             * ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放。
             * 请记住处理器的职责是释放所有传递到处理器的引用计数对象。
             */
            // 抛弃收到的数据
            ReferenceCountUtil.release(msg);
        }

    }
    

    /***
     * 这个方法会在发生异常时触发
     * 
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**
         * exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，即当 Netty 由于 IO
         * 错误或者处理器在处理事件时抛出的异常时。在大部分情况下，捕获的异常应该被记录下来 并且把关联的 channel
         * 给关闭掉。然而这个方法的处理方式会在遇到不同异常的情况下有不 同的实现，比如你可能想在关闭连接之前发送一个错误码的响应消息。
         */
        // 出现异常就关闭
        cause.printStackTrace();
        ctx.close();
    }

}
