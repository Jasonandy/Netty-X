package cn.ucaner.netty.rpc.test.client;

import java.util.List;

/**
* @Package：cn.ucaner.netty.rpc.test.client   
* @ClassName：PersonService   
* @Description：   <p> PersonService </p>
* @Author： - luxiaoxun     https://github.com/luxiaoxun/NettyRpc
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public interface PersonService {
	
	/**
	 * @Description: GetTestPerson
	 * @param name
	 * @param num
	 * @return List<Person>
	 */
    List<Person> GetTestPerson(String name, int num);
    
    /**
     * @Description: sayHelloNettyRpc
     * @Autor: Jason - jasonandy@hotmail.com
     */
    void sayHelloNettyRpc(int num);
}
