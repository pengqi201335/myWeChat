package com.pq.client.consoleCommand;

import com.pq.protocol.logout.LogoutRequestPacket;
import io.netty.channel.Channel;

/**
 * 注销登录命令执行器
 * @version 1.0
 * @author pengqi
 */
public class LogoutConsoleCommand implements ConsoleCommand{
    /**
     * 控制台命令执行器接口方法，执行控制台特定命令
     *
     * @param ch  命令施加的channel对象
     */
    @Override
    public void exec(Channel ch) {
        //创建注销登录请求对象
        LogoutRequestPacket lrp = new LogoutRequestPacket();
        lrp.setPLACEHOLDER("placeholder for serialization");

        //向服务端发送注销登录请求
        ch.writeAndFlush(lrp);
    }
}
