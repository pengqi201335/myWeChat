package com.pq.protocol.login;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.LOGIN_REQ;

/**
 * 用于传输用户登录信息的数据包对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class LogInRequestPacket extends Packet {
    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return LOGIN_REQ;
    }

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String pwd;

}
