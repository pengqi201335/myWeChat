package com.pq.protocol.logout;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.LOGOUT_REQ;

/**
 * 注销登录请求对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class LogoutRequestPacket extends Packet {
    /**
     * 占位符
     */
    private String PLACEHOLDER;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return LOGOUT_REQ;
    }
}
