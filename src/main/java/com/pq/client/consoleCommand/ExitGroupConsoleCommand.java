package com.pq.client.consoleCommand;

import com.pq.protocol.groupChat.ExitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 申请退群的控制台命令执行器
 * @version 1.0
 * @author pengqi
 */
public class ExitGroupConsoleCommand implements ConsoleCommand {
    /**
     * 控制台命令执行器接口方法，执行控制台特定命令
     *
     * @param ch  命令施加的channel对象
     */
    @Override
    public void exec(Channel ch) {
        Scanner cin = new Scanner(System.in);
        System.out.print("请输入您想要退出的群号:");

        //申请退出的群号
        String groupID = cin.nextLine();

        //创建一个申请退群命令对象
        ExitGroupRequestPacket egrp = new ExitGroupRequestPacket();
        egrp.setGroupID(groupID);

        //向服务端申请退群
        ch.writeAndFlush(egrp);
    }
}
