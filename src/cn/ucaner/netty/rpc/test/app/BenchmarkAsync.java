package cn.ucaner.netty.rpc.test.app;

import java.util.concurrent.TimeUnit;

import cn.ucaner.netty.rpc.client.RPCFuture;
import cn.ucaner.netty.rpc.client.RpcClient;
import cn.ucaner.netty.rpc.client.proxy.IAsyncObjectProxy;
import cn.ucaner.netty.rpc.registry.ServiceDiscovery;
import cn.ucaner.netty.rpc.test.client.HelloService;

/**
* @Package：cn.ucaner.netty.rpc.test.app   
* @ClassName：BenchmarkAsync   
* @Description：   <p> luxiaoxun - https://github.com/luxiaoxun/NettyRpc </p>
* @Author： - Jason   
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public class BenchmarkAsync {
	
    public static void main(String[] args) throws InterruptedException {//67.218.158.137:2181 127.0.0.1:8280
       
    	ServiceDiscovery serviceDiscovery = new ServiceDiscovery("67.218.158.137:2181");
        
    	final RpcClient rpcClient = new RpcClient(serviceDiscovery);

        /**
         * 线程数
         */
        int threadNum = 10;
        
        /**
         * 请求数
         */
        final int requestNum = 20;
        
        /**
         * 线程组
         */
        Thread[] threads = new Thread[threadNum];

        /**
         * 起始时间
         */
        long startTime = System.currentTimeMillis();
        
        //benchmark for async call 
        for (int i = 0; i < threadNum; ++i) {
            threads[i] = new Thread(new Runnable() {
            	
                @SuppressWarnings("static-access")
				@Override
                public void run() {
                    for (int i = 0; i < requestNum; i++) {
                        try {
                            IAsyncObjectProxy client = rpcClient.createAsync(HelloService.class);
                            //RPCFuture helloFuture = client.call("hello", Integer.toString(i));
                            RPCFuture helloFuture = client.call("echo", Integer.toString(i));
                            //超时时间 获取请求后响应的结果数据  -  MILLISECONDS 3 sencond
                            String result = (String) helloFuture.get(3000, TimeUnit.MILLISECONDS);
                            //System.out.println(result);
                            if (!result.equals("Hello! " + i))
                                System.out.println("error = " + result);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
            });
            threads[i].start();
        }
        
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        
        /**
         * 总花费时间
         */
        long timeCost = (System.currentTimeMillis() - startTime);
        
        String msg = String.format("Async call total-time-cost:%sms, req/s=%s", timeCost, ((double) (requestNum * threadNum)) / timeCost * 1000);
        
        System.out.println(msg);
        
        rpcClient.stop();
        
        //Output 
        //Async call total-time-cost:727ms, req/s=275.1031636863824 
        
        //Method:echo
        //Async call total-time-cost:724ms, req/s=276.24309392265195

    }
}
