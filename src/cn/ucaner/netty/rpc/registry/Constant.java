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
    String ZK_DATA_PATH = ZK_REGISTRY_PATH.concat("/data");
}
