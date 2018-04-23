package cn.ucaner.netty.rpc.registry;

/**
* @Package：cn.ucaner.netty.rpc.registry   
* @ClassName：Constant   
* @Description：   <p> ZooKeeper constant</p>
* @Author： - huangyong  https://github.com/luxiaoxun/NettyRpc     
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public interface Constant {

	/**
	 * zookeeper 连接超时时间
	 */
    int ZK_SESSION_TIMEOUT = 5000;

    /**
     * zookeeper 注册路径
     */
    String ZK_REGISTRY_PATH = "/registry";
    
    /**
     * zookeeper 数据路径
     */
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";
}
