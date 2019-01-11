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
