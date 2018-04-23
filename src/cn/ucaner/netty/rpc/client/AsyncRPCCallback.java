package cn.ucaner.netty.rpc.client;

/**
* @Package：cn.ucaner.netty.rpc.client   
* @ClassName：AsyncRPCCallback     异步远程调用
* @Description：   <p> AsyncRPCCallback </p>
* @Author： - luxiaoxun   - https://github.com/luxiaoxun/NettyRpc  
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public interface AsyncRPCCallback {

	/**
	 * @Description: success 
	 */
    void success(Object result);

    /**
     * @Description: fail 
     */
    void fail(Exception e);

}
