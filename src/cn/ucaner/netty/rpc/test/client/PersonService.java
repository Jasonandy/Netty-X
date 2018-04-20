package cn.ucaner.netty.rpc.test.client;

import java.util.List;

/**
 * Created by luxiaoxun on 2016-03-10.
 */
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
	
    List<Person> GetTestPerson(String name, int num);
}
