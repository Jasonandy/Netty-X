package cn.ucaner.netty.rpc.client;

/**
* @Package：cn.ucaner.netty.rpc.client   
* @ClassName：AsyncRPCCallback   
* @Description：   <p> AsyncRPCCallback </p>
* @Author： - luxiaoxun   - https://github.com/luxiaoxun/NettyRpc  
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public interface AsyncRPCCallback {

    void success(Object result);

    void fail(Exception e);

}
