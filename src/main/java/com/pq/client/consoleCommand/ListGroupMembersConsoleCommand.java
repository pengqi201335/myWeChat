package com.pq.client.consoleCommand;

import com.pq.protocol.groupChat.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 打印群聊成员列表执行器
 * @version 1.0
 * @author pengqi
 */
public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    /**
     * 控制台命令执行器接口方法，执行控制台特定命令
     *
     * @param ch  命令施加的channel对象
     */
    @Override
    public void exec(Channel ch) {
        Scanner cin = new Scanner(System.in);
        System.out.print("输入群号，获取群聊成员列表:");

        //群号
        String groupID = cin.next();

        //创建打印群组成员列表的请求对象
        ListGroupMembersRequestPacket requestPacket = new ListGroupMembersRequestPacket();
        requestPacket.setGroupID(groupID);

        ch.writeAndFlush(requestPacket);
    }
}
