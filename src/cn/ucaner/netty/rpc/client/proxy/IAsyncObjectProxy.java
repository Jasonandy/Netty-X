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
package cn.ucaner.netty.rpc.client.proxy;

import cn.ucaner.netty.rpc.client.RPCFuture;

/**
* @Package：cn.ucaner.netty.rpc.client.proxy   
* @ClassName：IAsyncObjectProxy   
* @Description：   <p> IAsyncObjectProxy </p>
* @Author： - luxiaoxun   - https://github.com/luxiaoxun/NettyRpc     
* @Modify By：   
* @Modify marker：   
* @version    V1.0
 */
public interface IAsyncObjectProxy {
	
    public RPCFuture call(String funcName, Object... args);
    
}