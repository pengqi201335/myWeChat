package com.pq.client.consoleCommand;

import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.Channel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 控制台命令管理器
 * 外观模式
 * @version 1.0
 * @author pengqi
 */
public class ConsoleCommandManager implements ConsoleCommand {
    /**
     * 存放特定命令与相应的命令执行器的映射表
     */
    private Map<String,ConsoleCommand> commandMap;

    public ConsoleCommandManager(){
        commandMap = new HashMap<>();
        commandMap.put("sendToUser",new SendToUserConsoleCommand());
        commandMap.put("logout",new LogoutConsoleCommand());
        commandMap.put("createGroup",new CreateGroupConsoleCommand());
        commandMap.put("joinGroup",new JoinGroupConsoleCommand());
        commandMap.put("exitGroup",new ExitGroupConsoleCommand());
        commandMap.put("listGroupMembers",new ListGroupMembersConsoleCommand());
        commandMap.put("sendToGroup",new SendToGroupConsoleCommand());
    }


    /**
     * 控制台命令执行器接口方法，执行控制台特定命令
     *
     * @param ch  命令施加的channel对象
     */
    @Override
    public void exec(Channel ch) {
        Scanner cin = new Scanner(System.in);
        //控制台键入的命令
        String command = cin.nextLine();

        //控制台输入"help"命令，打印命令列表
        if(command.equals("command-list")){
            System.out.println("+------------");
            Collection<String> keys = commandMap.keySet();
            for(String key:keys){
                System.out.println("|"+key);
                System.out.println("+------------");
            }
            return;
        }

        if(!SessionUtil.isLogin(ch)){
            return;
        }

        ConsoleCommand consoleCommand = commandMap.get(command);
        if(consoleCommand!=null){
            //执行命令
            consoleCommand.exec(ch);
        }else{
            System.err.println("未知的命令："+command+"请重新输入正确的命令");
        }
    }
}
