package com.pq.protocol.login;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.LOGIN_RES;

@Data
public class LogInResponsePacket extends Packet {
    /**
     * 是否登录成功
     */
    private boolean success;

    /**
     * 登录失败原因
     */
    private String reason;

    /**
     * 服务端为登录用户生成的随机ID
     */
    private String userID;

    /**
     * 当前登录用户名
     */
    private String userName;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return LOGIN_RES;
    }
}
