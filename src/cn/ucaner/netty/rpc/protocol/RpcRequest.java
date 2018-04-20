package cn.ucaner.netty.rpc.protocol;

/**
* @Package：cn.ucaner.netty.rpc.protocol   
* @ClassName：RpcRequest   
* @Description：   <p> RpcRequest </p>
* @Author： - huangyong    https://github.com/luxiaoxun/NettyRpc 
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class RpcRequest {
	
	/*
	 * identify Request 
	 */
    private String requestId;
    
    /**
     * Service className
     */
    private String className;
    
    /**
     * Service Methods
     */
    private String methodName;
    
    /**
     * Methods parmas 
     */
    private Class<?>[] parameterTypes;
    
    /**
     * params Type.
     */
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}