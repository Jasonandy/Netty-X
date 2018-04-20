package cn.ucaner.netty.rpc.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
* @Package：cn.ucaner.netty.rpc.server   
* @ClassName：RpcService   
* @Description：   <p> RPC annotation for RPC service http://www.cnblogs.com/luxiaoxun/p/5272384.html </p>
* @Author： - huangyong    https://github.com/luxiaoxun/NettyRpc    
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {
	
    Class<?> value();
}
