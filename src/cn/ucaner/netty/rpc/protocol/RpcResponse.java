package cn.ucaner.netty.rpc.protocol;

/**
* @Package：cn.ucaner.netty.rpc.protocol   
* @ClassName：RpcResponse   
* @Description：   <p> RPC Response</p>
* @Author： - huangyong https://github.com/luxiaoxun/NettyRpc    
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class RpcResponse {
	
	/**
	 * request Identify
	 */
    private String requestId;
    
    /**
     * error message
     */
    private String error;
    
    /**
     * result Object 
     */
    private Object result;

    public boolean isError() {
        return error != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
