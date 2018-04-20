package cn.ucaner.netty.rpc.test.server;

import java.util.ArrayList;
import java.util.List;

import cn.ucaner.netty.rpc.server.RpcService;
import cn.ucaner.netty.rpc.test.client.Person;
import cn.ucaner.netty.rpc.test.client.PersonService;

/**
 * Created by luxiaoxun on 2016-03-10.
 */
/**
* @Package：cn.ucaner.netty.rpc.test.server   
* @ClassName：PersonServiceImpl   
* @Description：   <p> PersonServiceImpl </p>
* @Author： - luxiaoxun    - https://github.com/luxiaoxun/NettyRpc
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
@RpcService(PersonService.class)
public class PersonServiceImpl implements PersonService {

    @Override
    public List<Person> GetTestPerson(String name, int num) {
        List<Person> persons = new ArrayList<>(num);
        for (int i = 0; i < num; ++i) {
            persons.add(new Person(Integer.toString(i), name));
        }
        return persons;
    }
}
