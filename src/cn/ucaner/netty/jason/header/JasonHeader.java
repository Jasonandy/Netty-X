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
package cn.ucaner.netty.jason.header;

/**
 * @projectName：Netty-X
 * @Package：cn.ucaner.netty.jason.header
 * @Description： <p> JasonHeader - 消息的头部 </p>
 * @Author： - Jason
 * @CreatTime：2019/6/17 - 18:06
 * @Modify By：
 * @ModifyTime： 2019/6/17
 * @Modify marker：
 */
public class JasonHeader {

    /**
     * 协议版本
     */
    private int version;

    /**
     * 消息内容长度
     */
    private int contentLength;

    /**
     *  服务名称
     */
    private String sessionId;

    public JasonHeader(int version, int contentLength, String sessionId) {
        this.version = version;
        this.sessionId = sessionId;
        this.contentLength = contentLength;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
