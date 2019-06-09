package com.pq.client.consoleCommand;

import io.netty.channel.Channel;

/**
 * 控制台命令执行器抽象接口，定义执行控制台特定命令的方法
 * @version 1.0
 * @author pengqi
 */
public interface ConsoleCommand {
    /**
     * 控制台命令执行器接口方法，执行控制台特定命令
     * @param ch 命令施加的channel对象
     */
    void exec(Channel ch);
}
