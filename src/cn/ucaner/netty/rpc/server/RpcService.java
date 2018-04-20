package cn.ucaner.netty.rpc.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
* @Package：cn.ucaner.netty.rpc.server   
* @ClassName：RpcService   
* @Description：   <p> RPC annotation for RPC service http://www.cnblogs.com/luxiaoxun/p/5272384.html 
* </br>RpcService注解定义在服务接口的实现类上，需要对该实现类指定远程接口，因为实现类可能会实现多个接口，一定要告诉框架哪个才是远程接口
* </p>
* @Author： - huangyong    https://github.com/luxiaoxun/NettyRpc    
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component // 表明可被 Spring 扫描
public @interface RpcService {
	
    Class<?> value();
}
