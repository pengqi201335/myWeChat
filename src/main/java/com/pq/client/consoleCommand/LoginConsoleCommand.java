package com.pq.client.consoleCommand;

import com.pq.protocol.login.LogInRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 登录指令执行器
 * @version 1.0
 * @author pengqi
 */
public class LoginConsoleCommand implements ConsoleCommand {
    /**
     * 控制台命令执行器接口方法，执行控制台特定命令
     *
     * @param ch  命令施加的channel对象
     */
    @Override
    public void exec(Channel ch) {
        Scanner cin = new Scanner(System.in);
        //创建登录对象
        LogInRequestPacket lrp = new LogInRequestPacket();
        System.out.print("输入用户名登录：");
        String userName = cin.nextLine();
        lrp.setUserName(userName);
        lrp.setPwd("201335");

        //将数据冲刷出站
        ch.writeAndFlush(lrp);

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
