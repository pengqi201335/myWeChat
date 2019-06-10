package com.pq.client.consoleCommand;

import com.pq.protocol.message.MessageRequestPacket;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand{
    /**
     * 控制台命令执行器接口方法，执行控制台特定命令
     *
     * @param ch  命令施加的channel对象
     */
    @Override
    public void exec(Channel ch) {
        Scanner cin = new Scanner(System.in);
        System.out.print("接收方用户ID:");
        String toUserID = cin.next();   //消息接收方ID
        //消息发送方用户名
        System.out.print(new Date().toString().substring(11,19)+" 【"+SessionUtil.getSession(ch).getUserName()+"】 ");
        String message = cin.next();    //消息内容

        //创建消息对象
        MessageRequestPacket mrp = new MessageRequestPacket(toUserID,message);

        //向服务端发送消息转发请求
        ch.writeAndFlush(mrp);

    }
}
