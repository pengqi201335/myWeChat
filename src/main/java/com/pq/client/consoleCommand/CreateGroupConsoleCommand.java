package com.pq.client.consoleCommand;

import com.pq.protocol.groupChat.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 创建群组的命令执行器对象
 * @version 1.0
 * @author pengqi
 */
public class CreateGroupConsoleCommand implements ConsoleCommand{
    private static final String USER_ID_SPLITER = ",";

    /**
     * 控制台命令执行器接口方法，执行控制台特定命令
     *
     * @param ch  命令施加的channel对象
     */
    @Override
    public void exec(Channel ch) {
        Scanner cin = new Scanner(System.in);
        System.out.print("【拉人群聊】输入您想要添加的群成员ID，用户ID之间以','分隔：");
        String userIDs = cin.nextLine();
        //创建开启群组命令对象
        CreateGroupRequestPacket cgrp = new CreateGroupRequestPacket();
        //设置群成员列表
        cgrp.setGroupMemberIdList(Arrays.asList(userIDs.split(USER_ID_SPLITER)));
        ch.writeAndFlush(cgrp);
    }
}
