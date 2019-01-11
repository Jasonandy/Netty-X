/******************************************************************************
* ~ Copyright (c) 2018 [jasonandy@hotmail.com | https://github.com/Jasonandy] *
* ~                                                                           *
* ~ Licensed under the Apache License, Version 2.0 (the "License”);           * 
* ~ you may not use this file except in compliance with the License.          *
* ~ You may obtain a copy of the License at                                   *
* ~                                                                           *
* ~    http://www.apache.org/licenses/LICENSE-2.0                             *
* ~                                                                           *
* ~ Unless required by applicable law or agreed to in writing, software       *
* ~ distributed under the License is distributed on an "AS IS" BASIS,         *
* ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
* ~ See the License for the specific language governing permissions and       *
* ~ limitations under the License.                                            *
******************************************************************************/
package cn.ucaner.netty.rpc.test.server;

import java.util.ArrayList;
import java.util.List;

import cn.ucaner.netty.rpc.server.RpcService;
import cn.ucaner.netty.rpc.test.client.Person;
import cn.ucaner.netty.rpc.test.client.PersonService;

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

	@Override
	public void sayHelloNettyRpc(int num) {
		for (int i = 0; i < num; i++) {
			System.out.println("HelloNettyRpc : "+i);
		}
	}
}
