package cn.ucaner.netty.rpc.test.app;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import cn.ucaner.netty.rpc.client.AsyncRPCCallback;
import cn.ucaner.netty.rpc.client.RPCFuture;
import cn.ucaner.netty.rpc.client.RpcClient;
import cn.ucaner.netty.rpc.client.proxy.IAsyncObjectProxy;
import cn.ucaner.netty.rpc.registry.ServiceDiscovery;
import cn.ucaner.netty.rpc.test.client.Person;
import cn.ucaner.netty.rpc.test.client.PersonService;

/**
* @Package：cn.ucaner.netty.rpc.test.app   
* @ClassName：PersonCallbackTest   
* @Description：   <p> PersonCallbackTest </p>
* @Author： - luxiaoxun   https://github.com/luxiaoxun/NettyRpc
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class PersonCallbackTest {
    public static void main(String[] args) {
        ServiceDiscovery serviceDiscovery = new ServiceDiscovery("127.0.0.1:2181");
        final RpcClient rpcClient = new RpcClient(serviceDiscovery);
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            IAsyncObjectProxy client = rpcClient.createAsync(PersonService.class);
            int num = 5;
            RPCFuture helloPersonFuture = client.call("GetTestPerson", "good", num);
            helloPersonFuture.addCallback(new AsyncRPCCallback() {
                @Override
                public void success(Object result) {
                    List<Person> persons = (List<Person>) result;
                    for (int i = 0; i < persons.size(); ++i) {
                        System.out.println(persons.get(i));
                    }
                    countDownLatch.countDown();
                }

                @Override
                public void fail(Exception e) {
                    System.out.println(e);
                    countDownLatch.countDown();
                }
            });

        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rpcClient.stop();

        System.out.println("End");
    }
}
