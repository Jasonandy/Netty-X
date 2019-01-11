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
package cn.ucaner.netty.rpc.test.app;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.ucaner.netty.rpc.client.RPCFuture;
import cn.ucaner.netty.rpc.client.RpcClient;
import cn.ucaner.netty.rpc.client.proxy.IAsyncObjectProxy;
import cn.ucaner.netty.rpc.test.client.HelloService;
import cn.ucaner.netty.rpc.test.client.Person;
import cn.ucaner.netty.rpc.test.client.PersonService;
import cn.ucaner.netty.rpc.test.interfaces.HelloNettyRpc;

/**
* @Package：cn.ucaner.netty.rpc.test.app   
* @ClassName：ServiceTest   
* @Description：   <p> ServiceTest </p>
* @Author： - https://github.com/luxiaoxun/NettyRpc   
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:/rpc/client/client-rpc.xml","classpath:/rpc/rpc.xml","classpath*:/spring-framework.xml"})
@ContextConfiguration(locations ="classpath:/rpc/client/client-rpc.xml")
public class ServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);
	
    @Autowired
    private RpcClient rpcClient;

    /**
     * @Description: Test echo for Rpc success!
     * @Autor: Jason - jasonandy@hotmail.com
     */
    @Test
    public void helloEcho() {
        HelloService helloService = RpcClient.create(HelloService.class);
        String result = helloService.echo("Rpc.");
        logger.info("result - {}",result);//cn.ucaner.netty.rpc.test.app.ServiceTest  - result - null
        //cn.ucaner.netty.rpc.test.app.ServiceTest  - result -  - > I'm Fine Thks.
    }
    
    @Test
    public void HelloNettyRpcEcho() {
    	HelloNettyRpc helloNettyRpc = RpcClient.create(HelloNettyRpc.class);
        String result = helloNettyRpc.echo("Rpc.");
        String rpcStr = helloNettyRpc.helloWorld();
        logger.info("result - {}",result);//cn.ucaner.netty.rpc.test.app.ServiceTest  - result - null
        logger.info("result - {}",rpcStr);
        //cn.ucaner.netty.rpc.test.app.ServiceTest  - result -  - > I'm Fine Thks.
    }
    
    
    
    @Test
    public void helloTest1() {
        HelloService helloService = RpcClient.create(HelloService.class);
        String result = helloService.hello("Jason");
        Assert.assertEquals("Hello! Jason", result);
        //Assert.assertEquals("Hello! World", result);
    }
    

    @Test
    public void helloTest2() {
        HelloService helloService = rpcClient.create(HelloService.class);
        Person person = new Person("Jason", "Andy");
        String result = helloService.hello(person);
        Assert.assertEquals("Hello! Yong Jason", result);
    }

    @Test
    public void helloPersonTest() {
        PersonService personService = rpcClient.create(PersonService.class);
        int num = 5;
        List<Person> persons = personService.GetTestPerson("Jaonandy", num);
        List<Person> expectedPersons = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            expectedPersons.add(new Person(Integer.toString(i), "Jasonandy"));
        }
        assertThat(persons, equalTo(expectedPersons));

        for (int i = 0; i < persons.size(); ++i) {
            System.out.println(persons.get(i));
        }
    }

    @Test
    public void helloFutureTest1() throws ExecutionException, InterruptedException {
        IAsyncObjectProxy helloService = rpcClient.createAsync(HelloService.class);
        RPCFuture result = helloService.call("hello", "World");
        Assert.assertEquals("Hello! World", result.get());
    }

    @Test
    public void helloFutureTest2() throws ExecutionException, InterruptedException {
        IAsyncObjectProxy helloService = rpcClient.createAsync(HelloService.class);
        Person person = new Person("Jason", "Andy");
        RPCFuture result = helloService.call("hello", person);
        Assert.assertEquals("Hello! Andy Jason", result.get());
    }

    @Test
    public void helloPersonFutureTest1() throws ExecutionException, InterruptedException {
        IAsyncObjectProxy helloPersonService = rpcClient.createAsync(PersonService.class);
        int num = 5;
        RPCFuture result = helloPersonService.call("GetTestPerson", "xiaoming", num);
        List<Person> persons = (List<Person>) result.get();
        List<Person> expectedPersons = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            expectedPersons.add(new Person(Integer.toString(i), "xiaoming"));
        }
        assertThat(persons, equalTo(expectedPersons));

        for (int i = 0; i < num; ++i) {
            System.out.println(persons.get(i));
        }
    }

    @After
    public void setTear() {
        if (rpcClient != null) {
            rpcClient.stop();
        }
    }

}
