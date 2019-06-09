package com.pq.client.consoleCommand;

import com.pq.protocol.groupChat.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 发送群聊消息命令执行器
 * @version 1.0
 * @author pengqi
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {
    /**
     * 控制台命令执行器接口方法，执行控制台特定命令
     *
     * @param ch 命令施加的channel对象
     */
    @Override
    public void exec(Channel ch) {
        Scanner cin = new Scanner(System.in);
        System.out.print("输入您所在群聊的群号:");
        String groupID = cin.next();
        System.out.print("发送消息:");
        String message = cin.next();

        //创建群聊消息请求对象
        GroupMessageRequestPacket requestPacket = new GroupMessageRequestPacket();
        requestPacket.setGroupID(groupID);
        requestPacket.setMessage(message);

        //向服务端请求发送群消息
        ch.writeAndFlush(requestPacket);
    }
}
