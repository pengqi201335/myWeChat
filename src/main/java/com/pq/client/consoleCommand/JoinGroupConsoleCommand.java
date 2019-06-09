package com.pq.client.consoleCommand;

import com.pq.protocol.groupChat.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 加群命令执行器
 * @version 1.0
 * @author pengqi
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {
    /**
     * 控制台命令执行器接口方法，执行控制台特定命令
     *
     * @param ch  命令施加的channel对象
     */
    @Override
    public void exec(Channel ch) {
        Scanner cin = new Scanner(System.in);
        System.out.print("搜索群号：");
        String groupID = cin.nextLine();

        //创建申请入群命令对象
        JoinGroupRequestPacket jgrp = new JoinGroupRequestPacket();
        jgrp.setGroupID(groupID);

        //向服务端发送入群申请
        ch.writeAndFlush(jgrp);
    }
}
