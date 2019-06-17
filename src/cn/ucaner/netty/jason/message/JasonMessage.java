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
package cn.ucaner.netty.jason.message;

import cn.ucaner.netty.jason.header.JasonHeader;

/**
 * @projectName：Netty-X
 * @Package：cn.ucaner.netty.jason.message
 * @Description： <p> JasonMessage </p>
 * @Author： - Jason
 * @CreatTime：2019/6/17 - 18:09
 * @Modify By：
 * @ModifyTime： 2019/6/17
 * @Modify marker：
 */
public class JasonMessage {

    private JasonHeader jasonHeader;

    private String content;

    public JasonMessage(JasonHeader jasonHeader, String content) {
        this.jasonHeader = jasonHeader;
        this.content = content;
    }

    public JasonHeader getJasonHeader() {
        return jasonHeader;
    }

    public void setJasonHeader(JasonHeader jasonHeader) {
        this.jasonHeader = jasonHeader;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("[version=%d,contentLength=%d,sessionId=%s,content=%s]", jasonHeader.getVersion(),
                jasonHeader.getContentLength(), jasonHeader.getSessionId(), content);
    }
}
